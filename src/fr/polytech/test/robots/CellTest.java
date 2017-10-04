package fr.polytech.test.robots;

import fr.polytech.robots.Cell;
import fr.polytech.robots.Resource;
import fr.polytech.robots.Robot;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.management.InstanceAlreadyExistsException;

public class CellTest {

    @Test
    public void testAddRobot() throws InstanceAlreadyExistsException {
        Cell cell = new Cell();
        Robot robot = new Robot();

        cell.addRobot(robot);
        Robot cellRobot = cell.getRobot();

        Assert.assertTrue(robot == cellRobot);
    }

    @Test(expected = InstanceAlreadyExistsException.class)
    public void testAddRobotAlreadyExists() throws InstanceAlreadyExistsException {
        Robot robot1 = new Robot();
        Robot robot2 = new Robot();

        Cell cell = new Cell();
        cell.addRobot(robot1);
        cell.addRobot(robot2);
    }

    @Test(expected = NullPointerException.class)
    public void testAddRobotNull() throws InstanceAlreadyExistsException {
        Cell cell = new Cell();
        cell.addRobot(null);
    }

    @Test
    public void testRemoveRobot() throws Exception {
        Robot robot = new Robot();
        Cell cell = new Cell();

        cell.addRobot(robot);
        cell.removeRobot();

        Assert.assertNull(cell.getRobot());
    }

    @Test
    public void testAddResource() throws InstanceAlreadyExistsException {
        Cell cell = new Cell();
        Resource resource = new Resource();

        cell.addResource(resource);
        Resource cellResource = cell.getResource();

        Assert.assertTrue(resource == cellResource);
    }

    @Test(expected = InstanceAlreadyExistsException.class)
    public void testAddResourceAlreadyExists() throws InstanceAlreadyExistsException {
        Resource resource1 = new Resource();
        Resource resource2 = new Resource();

        Cell cell = new Cell();
        cell.addResource(resource1);
        cell.addResource(resource2);
    }

    @Test(expected = NullPointerException.class)
    public void testAddResourceNull() throws InstanceAlreadyExistsException {
        Cell cell = new Cell();
        cell.addResource(null);
    }

    @Test
    public void testRemoveResource() throws Exception {
        Resource resource = new Resource();
        Cell cell = new Cell();

        cell.addResource(resource);
        cell.removeResource();

        Assert.assertNull(cell.getResource());
    }
}
