package model;

import model.dao.DBOrderConnector;
import model.dao.DBOrderManager;
import org.junit.*;
import static org.junit.Assert.*;
import java.sql.Connection;
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
}