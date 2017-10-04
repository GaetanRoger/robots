package fr.polytech.test.robots;

import fr.polytech.robots.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class CellTest {

    @Test
    public void testAddRobot() {
        Cell cell = new Cell(new Tuple(0, 0));
        Robot robot = new Robot(0);

        cell.addRobot(robot);
        Robot cellRobot = cell.getRobot();

        Assert.assertTrue(robot == cellRobot);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddRobotAlreadyExists() {
        Robot robot1 = new Robot(0);
        Robot robot2 = new Robot(0);

        Cell cell = new Cell(new Tuple(0, 0));
        cell.addRobot(robot1);
        cell.addRobot(robot2);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddRobotWithResourcePresent() {
        Robot robot = new Robot(0);
        Resource resource = new Resource(ResourceType.BLEUE);

        Cell cell = new Cell(new Tuple(0, 0));
        cell.addResource(resource);
        cell.addRobot(robot);
    }

    @Test(expected = NullPointerException.class)
    public void testAddRobotNull() {
        Cell cell = new Cell(new Tuple(0, 0));
        cell.addRobot(null);
    }

    @Test
    public void testRemoveRobot() {
        Robot robot = new Robot(0);
        Cell cell = new Cell(new Tuple(0, 0));

        cell.addRobot(robot);
        cell.removeRobot();

        Assert.assertNull(cell.getRobot());
    }

    @Test
    public void testAddResource() {
        Cell cell = new Cell(new Tuple(0, 0));
        Resource resource = new Resource(ResourceType.BLEUE);

        cell.addResource(resource);
        Resource cellResource = cell.getResource();

        Assert.assertTrue(resource == cellResource);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddResourceAlreadyExists() {
        Resource resource1 = new Resource(ResourceType.BLEUE);
        Resource resource2 = new Resource(ResourceType.BLEUE);

        Cell cell = new Cell(new Tuple(0, 0));
        cell.addResource(resource1);
        cell.addResource(resource2);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddResourceWithRobotPresent() {
        Resource resource = new Resource(ResourceType.BLEUE);
        Robot robot = new Robot(0);

        Cell cell = new Cell(new Tuple(0, 0));
        cell.addRobot(robot);
        cell.addResource(resource);
    }

    @Test(expected = NullPointerException.class)
    public void testAddResourceNull() {
        Cell cell = new Cell(new Tuple(0, 0));
        cell.addResource(null);
    }

    @Test
    public void testRemoveResource() {
        Resource resource = new Resource(ResourceType.BLEUE);
        Cell cell = new Cell(new Tuple(0, 0));

        cell.addResource(resource);
        cell.removeResource();

        Assert.assertNull(cell.getResource());
    }
}
