package fr.polytech.test.robots;

import fr.polytech.robots.Cell;
import fr.polytech.robots.Environment;
import fr.polytech.robots.ResourceType;
import fr.polytech.robots.Robot;
import org.junit.Assert;
import org.junit.Test;

public class EnvironmentTest {
    private void _testEnv(Environment environment, int n, int m, int robotCount, int resourcesCount) {
        Assert.assertEquals(n, environment.getN());
        Assert.assertEquals(m, environment.getM());
        Assert.assertEquals(robotCount, environment.getRobots().size());
        Assert.assertEquals(n * m, environment.getCells().size());

        int actualRobotCount = 0;
        int actualResourceCount = 0;
        for (Cell cell : environment.getCells().values()) {
            if (cell.getRobot() != null)
                actualRobotCount++;
            else if (cell.getResource() != null)
                actualResourceCount++;
        }
        for (Robot robot :
                environment.getRobots()) {
            Assert.assertNotNull(robot.getCell());
            if (robot.getResource() != null)
                actualResourceCount++;
        }

        Assert.assertEquals(robotCount, actualRobotCount);
        Assert.assertEquals(resourcesCount * ResourceType.values().length, actualResourceCount);
    }


    @Test
    public void testSimpleConstructor() {
        Environment environment = new Environment(2, 3, 1, 1);
        _testEnv(environment, 2, 3, 1, 1);
    }

    @Test
    public void testConstructor() {
        Environment environment = new Environment(10, 10, 10, 10);

        _testEnv(environment, 10, 10, 10, 10);
    }

    @Test
    public void testRunSimulation() {
        Environment environment = new Environment(5, 5, 2, 2);
        environment.runSimulation();

        _testEnv(environment, 5, 5, 2, 2);


    }
}
