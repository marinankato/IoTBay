package model;
import model.dao.IoTDeviceDAO;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class IoTDeviceDAOTest {

    @Test
    public void testAddDevice() throws Exception {
        IoTDeviceDAO dao = new IoTDeviceDAO();
        IoTDevice device = new IoTDevice(0, "JUnit Device", "Test Type", 99.9, 20);
        dao.addDevice(device);

        List<IoTDevice> list = dao.getAllDevices();
        boolean exists = false;
        for (IoTDevice d : list) {
            if ("JUnit Device".equals(d.getName())) {
                exists = true;
                break;
            }
        }
        assertTrue("Device should exist after adding", exists);
    }

    @Test
    public void testGetAllDevices() throws Exception {
        IoTDeviceDAO dao = new IoTDeviceDAO();
        List<IoTDevice> devices = dao.getAllDevices();
        assertNotNull("Device list should not be null", devices);
    }

    @Test
    public void testSearchDevices() throws Exception {
        IoTDeviceDAO dao = new IoTDeviceDAO();
        List<IoTDevice> results = dao.searchDevices("Sensor", "");
        assertNotNull("Search result should not be null", results);
    }

    @Test
    public void testUpdateDevice() throws Exception {
        IoTDeviceDAO dao = new IoTDeviceDAO();
        List<IoTDevice> list = dao.getAllDevices();
        if (!list.isEmpty()) {
            IoTDevice device = list.get(0);
            int newQty = device.getQuantity() + 1;
            device.setQuantity(newQty);
            dao.updateDevice(device);

            IoTDevice updated = dao.getDeviceById(device.getId());
            assertEquals("Quantity should be updated", newQty, updated.getQuantity());
        }
    }
}
