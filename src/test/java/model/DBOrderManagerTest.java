package model;

import model.dao.DBOrderConnector;
import model.dao.DBOrderItem;
import model.dao.DBOrderManager;
import org.junit.*;
import static org.junit.Assert.*;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DBOrderManagerTest {
    private static final int TEST_USER_ID = 42; 
    private DBOrderConnector cf;
    private Connection conn;
    private DBOrderManager mgr;

    @Before
    public void setUp() throws Exception {
        // open a connection to the test database
        cf = new DBOrderConnector();
        conn = cf.openConnection();
        mgr = new DBOrderManager(conn);
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void testGetOrdersByUserEmpty() throws Exception {
        // If a user has no orders, getOrdersByUser should return an empty list.
        List<Order> emptyList = mgr.getOrdersByUser(-99999);
        assertTrue("Expected no orders for a nonexistent user", emptyList.isEmpty());
    }

    @Test
    public void testInsertOrderAndReturnKey() throws Exception {
        // 1) Insert a new order with status SAVED
        Order toInsert = new Order(
            0,                            
            TEST_USER_ID,                
            new Date(),                 
            123.45,                      
            Order.SAVED                  
        );
        int newId = mgr.insertOrderAndReturnKey(toInsert);
        assertTrue("Generated order ID must be > 0", newId > 0);

        // 2) Fetch back all orders for that user and verify the new one is in the list
        List<Order> orders = mgr.getOrdersByUser(TEST_USER_ID);
        boolean found = orders.stream().anyMatch(o -> o.getOrderID() == newId);
        assertTrue("Inserted order should be retrievable via getOrdersByUser", found);
    }

    @Test
    public void testCancelOrder() throws Exception {
        // Insert an order
        Order toInsert = new Order(0, TEST_USER_ID, new Date(), 77.77, Order.SAVED);
        int id = mgr.insertOrderAndReturnKey(toInsert);

        // Cancel order
        mgr.cancelOrder(id);

        // fetch it back and verify status is CANCELLED
        List<Order> list = mgr.searchOrders(TEST_USER_ID, id, null);
        assertEquals("searchOrders should still return one after cancel", 1, list.size());
        assertEquals("Order status should now be CANCELLED", 
                     Order.CANCELLED, list.get(0).getOrderStatus());
    }

    @Test
    public void testAddItemInsertsLineItem() throws Exception {
        // 1) create an order
        Order o      = new Order(0, TEST_USER_ID, new java.util.Date(), 0.0, Order.SAVED);
        int orderId  = mgr.insertOrderAndReturnKey(o);

        // 2) use manager.addItem(...)
        mgr.addItem(orderId, /*deviceId=*/123, /*qty=*/2, /*price=*/9.99);

        // 3) fetch via DBOrderItem
        DBOrderItem itemDao = new DBOrderItem(conn);
        List<CartItem> items = itemDao.getItemsForOrder(orderId);

        assertEquals(1, items.size());
        CartItem ci = items.get(0);
        assertEquals(123, ci.getDeviceId());
        assertEquals(2, ci.getQuantity());
        assertEquals(9.99, ci.getUnitPrice(), 0.001);
    }

    @Test
    public void testSearchOrdersByDate() throws Exception {
        // pick a fixed date (today, truncated to SQL date)
        java.util.Date now = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(now.getTime());

        // insert an order with that date
        Order o     = new Order(0, TEST_USER_ID, now, 77.77, Order.SAVED);
        int id      = mgr.insertOrderAndReturnKey(o);

        // search by date only
        List<Order> found = mgr.searchOrders(TEST_USER_ID, null, sqlDate);
        assertTrue("searchOrders(date) should return at least our test order",
                   found.stream().anyMatch(x -> x.getOrderID() == id));
    }

    @Test
    public void testUpdateOrderStatus() throws Exception {
        Order o    = new Order(0, TEST_USER_ID, new java.util.Date(), 10.00, Order.SAVED);
        int id     = mgr.insertOrderAndReturnKey(o);

        // flip to SUBMITTED
        mgr.updateOrderStatus(id, Order.SUBMITTED);

        Order fetched = mgr.findById(id);
        assertEquals("updateOrderStatus should change the status",
                     Order.SUBMITTED, fetched.getOrderStatus());
    }

    @Test
    public void testUpdateOrder() throws Exception {
        java.util.Date origDate = new java.util.Date();
        Order o     = new Order(0, TEST_USER_ID, origDate, 20.00, Order.SAVED);
        int id      = mgr.insertOrderAndReturnKey(o);

        // prepare new values
        Calendar cal = Calendar.getInstance();
        cal.setTime(origDate);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        java.util.Date newDate = cal.getTime();
        Order updated = new Order(id, TEST_USER_ID, newDate, 99.99, Order.CANCELLED);

        mgr.updateOrder(updated);
        Order fetched = mgr.findById(id);

        assertEquals("Date should be updated",
                     new java.sql.Date(newDate.getTime()), 
                     new java.sql.Date(fetched.getOrderDate().getTime()));
        assertEquals(99.99, fetched.getTotalPrice(), 0.001);
        assertEquals(Order.CANCELLED, fetched.getOrderStatus());
    }

    @Test
    public void testFindById() throws Exception {
        Order o = new Order(0, TEST_USER_ID, new java.util.Date(), 5.55, Order.SAVED);
        int id  = mgr.insertOrderAndReturnKey(o);

        Order fetched = mgr.findById(id);
        assertNotNull(fetched);
        assertEquals(id, fetched.getOrderID());
        assertEquals(TEST_USER_ID, fetched.getUserID());
    }

    @Test
    public void testRecalcTotal() throws Exception {
        // 1) insert order with zero total
        Order o    = new Order(0, TEST_USER_ID, new java.util.Date(), 0.0, Order.SAVED);
        int id     = mgr.insertOrderAndReturnKey(o);

        // 2) add two items
        mgr.addItem(id, /*dev=*/1, /*qty=*/2, /*unit=*/10.0); // line=20
        mgr.addItem(id, /*dev=*/2, /*qty=*/3, /*unit=*/5.0);  // line=15

        // 3) recalc and fetch
        mgr.recalcTotal(id);
        Order fetched = mgr.findById(id);

        assertEquals("Total should be sum of line-items (20+15=35)", 
                     35.0, fetched.getTotalPrice(), 0.001);
    }

}