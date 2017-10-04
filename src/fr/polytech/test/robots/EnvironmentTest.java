package fr.polytech.test.robots;

import fr.polytech.robots.Cell;
import fr.polytech.robots.Environment;
import org.junit.Assert;
import org.junit.Test;

public class EnvironmentTest {
    @Test
    public void testSimpleConstructor() {
        Environment environment = new Environment(2, 3, 1, 1);

        Assert.assertEquals(2, environment.getN());
        Assert.assertEquals(3, environment.getM());
        Assert.assertEquals(1, environment.getRobots().size());
        Assert.assertEquals(2 * 3, environment.getCells().size());

        int robotCount = 0;
        for (Cell cell : environment.getCells().values()) {
            if (cell.getRobot() != null)
                robotCount++;
        }

        Assert.assertEquals(1, robotCount);
    }

    @Test
    public void testConstructor() {
        Environment environment = new Environment(10, 10, 10, 10);

        Assert.assertEquals(10, environment.getN());
        Assert.assertEquals(10, environment.getM());
        Assert.assertEquals(10, environment.getRobots().size());
        Assert.assertEquals(10 * 10, environment.getCells().size());

        int robotCount = 0;
        for (Cell cell : environment.getCells().values()) {
            if (cell.getRobot() != null)
                robotCount++;
        }

        Assert.assertEquals(10, robotCount);
    }
}
