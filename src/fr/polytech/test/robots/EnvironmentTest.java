package fr.polytech.test.robots;

import fr.polytech.robots.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

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

    @Test
    public void testGetCellsAroundFromCenter() {
        Environment environment = new Environment(3, 3, 1, 0);

        Robot robot = environment.getRobots().get(0);
        _moveRobotToCell(environment, robot, new Tuple(1, 1));

        ArrayList<Cell> cellsAround = environment.getCellsAround(robot);

        Assert.assertEquals(8, cellsAround.size());

        Collection<Tuple> tuples = new ArrayList<>(cellsAround.size());
        for (Cell cell : cellsAround) {
            tuples.add(cell.getPosition());
        }

        Assert.assertTrue(tuples.contains(new Tuple(0, 0)));
        Assert.assertTrue(tuples.contains(new Tuple(1, 0)));
        Assert.assertTrue(tuples.contains(new Tuple(2, 0)));
        Assert.assertTrue(tuples.contains(new Tuple(0, 1)));
        Assert.assertTrue(tuples.contains(new Tuple(2, 1)));
        Assert.assertTrue(tuples.contains(new Tuple(0, 2)));
        Assert.assertTrue(tuples.contains(new Tuple(1, 2)));
        Assert.assertTrue(tuples.contains(new Tuple(2, 2)));
    }

    @Test
    public void testGetCellsAroundFromTopLeft() {
        Environment environment = new Environment(3, 3, 1, 0);

        Robot robot = environment.getRobots().get(0);
        _moveRobotToCell(environment, robot, new Tuple(0, 0));

        ArrayList<Cell> cellsAround = environment.getCellsAround(robot);

        Assert.assertEquals(3, cellsAround.size());

        Collection<Tuple> tuples = new ArrayList<>(cellsAround.size());
        for (Cell cell : cellsAround) {
            tuples.add(cell.getPosition());
        }

        Assert.assertTrue(tuples.contains(new Tuple(1, 0)));
        Assert.assertTrue(tuples.contains(new Tuple(0, 1)));
        Assert.assertTrue(tuples.contains(new Tuple(1, 1)));
    }

    @Test
    public void testGetCellsAroundFromBottomRight() {
        Environment environment = new Environment(3, 3, 1, 0);

        Robot robot = environment.getRobots().get(0);
        _moveRobotToCell(environment, robot, new Tuple(2, 2));

        ArrayList<Cell> cellsAround = environment.getCellsAround(robot);

        Assert.assertEquals(3, cellsAround.size());

        Collection<Tuple> tuples = new ArrayList<>(cellsAround.size());
        for (Cell cell : cellsAround) {
            tuples.add(cell.getPosition());
        }

        Assert.assertTrue(tuples.contains(new Tuple(1, 2)));
        Assert.assertTrue(tuples.contains(new Tuple(2, 1)));
        Assert.assertTrue(tuples.contains(new Tuple(1, 1)));
    }

    @Test
    public void testChooseNextCell() {
        Environment environment = new Environment(3, 3, 1, 0);

        Robot robot = environment.getRobots().get(0);
        _moveRobotToCell(environment, robot, new Tuple(1, 1));

        Cell cell = environment.chooseNextCell(robot);
        Tuple[] availablePositions = new Tuple[] {
                new Tuple(0, 1),
                new Tuple(1, 0),
                new Tuple(1, 2),
                new Tuple(2, 1),
        };

        Assert.assertTrue(
                cell.getPosition().equals(availablePositions[0]) ||
                cell.getPosition().equals(availablePositions[1]) ||
                cell.getPosition().equals(availablePositions[2]) ||
                cell.getPosition().equals(availablePositions[3])
        );
    }

    private void _moveRobotToCell(Environment environment, Robot robot, Tuple target) {
        Cell originCell = robot.getCell();
        Tuple robotPosition = originCell.getPosition();

        if (!robotPosition.equals(target)) {
            Cell targetCell = environment.getCells().get(target);
            originCell.removeRobot();
            if (targetCell.getRobot() != null) {
                originCell.addRobot(targetCell.getRobot());
                targetCell.getRobot().setCell(originCell);
                targetCell.removeRobot();
            } else if (targetCell.getResource() != null) {
                originCell.addResource(targetCell.getResource());
                targetCell.removeResource();
            }

            targetCell.addRobot(robot);
            robot.setCell(targetCell);
        }
    }
}
