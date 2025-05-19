package model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.sql.*;
import java.util.List;

import model.dao.DBOrderItem;

public class DBOrderItemTest {
    private Connection conn;
    private DBOrderItem dao;

    @Before
    public void setUp() throws Exception {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite::memory:");
        Statement st = conn.createStatement();

        // create the device table (for the JOIN) and the OrderItems table:
        st.execute("CREATE TABLE iot_device (" +
                   "device_id INTEGER PRIMARY KEY," +
                   "device_name TEXT," +
                   "device_type TEXT," +
                   "unit_price NUMERIC," +
                   "quantity INTEGER)");
        st.execute("CREATE TABLE OrderItems (" +
                   "orderID INTEGER," +
                   "deviceID INTEGER," +
                   "quantity INTEGER," +
                   "unitPrice NUMERIC)");

        st.execute("INSERT INTO iot_device(device_id, device_name, device_type, unit_price, quantity) " +
                   "VALUES(1, 'Dev1', 'TypeA', 10.0, 100)");

        dao = new DBOrderItem(conn);
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void testGetItemsForOrderEmpty() throws SQLException {
        List<CartItem> items = dao.getItemsForOrder(999);
        assertNotNull("should return a (non-null) list even if empty", items);
        assertTrue("no items for an order that doesn’t exist", items.isEmpty());
    }

    @Test
    public void testAddAndGetItemsForOrder() throws SQLException {
        // add one line item
        dao.addItem(1, 1, 5, 9.99);

        List<CartItem> items = dao.getItemsForOrder(1);
        assertEquals("exactly one item in order 1", 1, items.size());

        CartItem ci = items.get(0);
        assertEquals(1,    ci.getDeviceId());
        assertEquals("Dev1", ci.getName());
        assertEquals(5,    ci.getQuantity());
        assertEquals(9.99, ci.getUnitPrice(), 0.001);
    }

    @Test
    public void testUpdateItem() throws SQLException {
        // insert, then update
        dao.addItem(1, 1, 5, 9.99);
        dao.updateItem(1, 1, 3, 8.88);

        List<CartItem> items = dao.getItemsForOrder(1);
        assertEquals("still one item after update", 1, items.size());

        CartItem ci = items.get(0);
        assertEquals(3,    ci.getQuantity());
        assertEquals(8.88, ci.getUnitPrice(), 0.001);
    }

    @Test
    public void testDeleteItem() throws SQLException {
        // insert, then delete
        dao.addItem(1, 1, 5, 9.99);
        dao.deleteItem(1, 1);

        List<CartItem> items = dao.getItemsForOrder(1);
        assertTrue("no items after deletion", items.isEmpty());
    }
}