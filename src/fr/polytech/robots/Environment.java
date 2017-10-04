package fr.polytech.robots;

import com.sun.istack.internal.NotNull;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Environment {

    /**
     * Cases et leur position.
     */
    private Map<Tuple, Cell> cells = new HashMap<Tuple, Cell>();

    /**
     * Tous les robots créés.
     */
    private ArrayList<Robot> robots = new ArrayList<>();

    /**
     * Largeur de la grille.
     */
    private int n;

    /**
     * Hauteur de la grille.
     */
    private int m;

    private int cycles = 10;

    private int currentCycle = 0;

    public Environment(int n, int m, int robotCount, int resourcesCount, int cycles) {
        this(n, m, robotCount, resourcesCount);
        this.cycles = cycles;
    }

    public Environment(int n, int m, int robotCount, int resourcesCount) {
        this.n = n;
        this.m = m;

        createCells(n, m);
        createResources(n, m, resourcesCount);
        createRobots(robotCount);
    }

    public Map<Tuple, Cell> getCells() {
        return cells;
    }

    public ArrayList<Robot> getRobots() {
        return robots;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    private void createRobots(int robotCount) {
        for (int i = 0; i < robotCount; ++i) {
            Cell cell;
            Robot robot = new Robot(i);
            boolean robotWasAdded = false;

            do {
                Tuple randPosition = randomPosition();
                cell = cells.get(randPosition);

                try {
                    cell.addRobot(robot);
                    robots.add(robot);
                    robot.setCell(cell);
                    robotWasAdded = true;
                } catch (UnsupportedOperationException ignored) {
                }
            } while (!robotWasAdded);
        }
    }

    private void createResources(int n, int m, int resourcesCount) {
        for (int i = 0; i < resourcesCount; ++i) {
            for (ResourceType resourceType :
                    ResourceType.values()) {
                Resource resource = new Resource(resourceType);
                Cell cell;
                boolean resourceWasAdded = false;

                do {
                    Tuple randPosition = randomPosition();
                    cell = cells.get(randPosition);

                    try {
                        cell.addResource(resource);
                        resourceWasAdded = true;
                    } catch (UnsupportedOperationException ignored) {
                    }
                } while (!resourceWasAdded);
            }
        }
    }

    private Tuple randomPosition() {
        int randX = randomIntBetween(0, n);
        int randY = randomIntBetween(0, m);
        return new Tuple(randX, randY);
    }

    private void createCells(int n, int m) {
        for (int x = 0; x < n; ++x) {
            for (int y = 0; y < m; ++y) {
                Cell cell = new Cell();
                Tuple tuple = new Tuple(x, y);

                cell.setPosition(tuple);
                cells.put(tuple, cell);
            }
        }
    }

    public void runSimulation() {
        while (currentCycle < cycles)
            runNextCycle();
    }

    public void runNextCycle() {
        currentCycle++;

        for (Robot robot :
                robots) {
            robot.pickUp();
            robot.move();
        }
    }

    public void measureQuality() {
        // TODO - implement Environment.measureQuality
        throw new UnsupportedOperationException();
    }

    /**
     * @param r
     */
    public Cell chooseNextCell(Robot r) {
        ArrayList<Tuple> positions = _get4PositionsAround(r.getCell().getPosition());

        int randInt = randomIntBetween(0, positions.size());
        Tuple randPosition = positions.get(randInt);

        return cells.get(randPosition);
    }

    /**
     * @param t
     */
    private Cell chooseRandomCellAround(@NotNull Tuple t) {
        ArrayList<Tuple> availableChoices = _get4PositionsAround(t);

        int randomInt = randomIntBetween(0, availableChoices.size());
        Tuple selectedTuple = availableChoices.get(randomInt);

        return cells.get(selectedTuple);
    }

    private int randomIntBetween(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    /**
     * Retourne les huit cases entourant le robot.
     *
     * @param r Robot au centre des cases.
     */
    public ArrayList<Cell> getCellsAround(@NotNull Robot r) {
        Tuple robotPosition = r.getCell().getPosition();
        ArrayList<Tuple> positionsAround = _get8PositionsAround(robotPosition);
        ArrayList<Cell> cellsAround = new ArrayList<>(positionsAround.size());

        for (Tuple position :
                positionsAround) {
            cellsAround.add(cells.get(position));
        }

        return cellsAround;
    }

    /**
     * @param t Position entourée.
     * @return Les quatre positions entourant une position (gauche, droit, top, bottom).
     */
    private ArrayList<Tuple> _get4PositionsAround(Tuple t) {
        ArrayList<Tuple> availableChoices = new ArrayList<>();

        if (_isNextXBeforeRightBorder(t)) {
            availableChoices.add(new Tuple(t.getX() + 1, t.getY()));
        }
        if (_isNextYBelowTopBorder(t)) {
            availableChoices.add(new Tuple(t.getX(), t.getY() + 1));
        }
        if (_isPreviousXAfterLeftBorder(t)) {
            availableChoices.add(new Tuple(t.getX() - 1, t.getY()));
        }
        if (_isPreviousYTopOfBottomBorder(t)) {
            availableChoices.add(new Tuple(t.getX(), t.getY() - 1));
        }
        return availableChoices;
    }

    /**
     * Vérifie que t.y - 1 n'est pas inférieur à zéro.
     *
     * @param t
     * @return
     */
    private boolean _isPreviousYTopOfBottomBorder(Tuple t) {
        return t.getY() - 1 >= 0;
    }

    /**
     * Vérifie que t.x - 1 n'est pas inférieur à zéro.
     *
     * @param t
     * @return
     */
    private boolean _isPreviousXAfterLeftBorder(Tuple t) {
        return t.getX() - 1 >= 0;
    }

    /**
     * Vérifie que t.y + 1 n'est pas supérieur ou égal à m.
     *
     * @param t
     * @return
     */
    private boolean _isNextYBelowTopBorder(Tuple t) {
        return t.getY() + 1 < m;
    }

    /**
     * Vérifie que t.x + 1 n'est pas supérieur ou égal à n.
     *
     * @param t
     * @return
     */
    private boolean _isNextXBeforeRightBorder(Tuple t) {
        return t.getX() + 1 < n;
    }

    /**
     * @param t Position entourée.
     * @return Les huit positions entourant une position.
     */
    private ArrayList<Tuple> _get8PositionsAround(Tuple t) {
        ArrayList<Tuple> positions = _get4PositionsAround(t);

        // Top right
        if (_isNextXBeforeRightBorder(t) && _isNextYBelowTopBorder(t)) {
            positions.add(new Tuple(t.getX() + 1, t.getY() + 1));
        }
        // Bottom right
        if (_isNextXBeforeRightBorder(t) && _isPreviousYTopOfBottomBorder(t)) {
            positions.add(new Tuple(t.getX() + 1, t.getY() - 1));
        }
        // Bottom left
        if (_isPreviousXAfterLeftBorder(t) && _isPreviousYTopOfBottomBorder(t)) {
            positions.add(new Tuple(t.getX() - 1, t.getY() - 1));
        }
        // Top left
        if (_isPreviousXAfterLeftBorder(t) && _isNextYBelowTopBorder(t)) {
            positions.add(new Tuple(t.getX() - 1, t.getY() + 1));
        }

        return positions;
    }
}