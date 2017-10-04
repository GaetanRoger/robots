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
    private Collection<Robot> robots;

    /**
     * Largeur de la grille.
     */
    private int n;

    /**
     * Hauteur de la grille.
     */
    private int m;

    public Environment(int n, int m, int robotCount) {
        this.n = n;
        this.m = m;
        
    }

    public void runSimulation() {
        // TODO - implement Environment.runSimulation
        throw new UnsupportedOperationException();
    }

    public void measureQuality() {
        // TODO - implement Environment.measureQuality
        throw new UnsupportedOperationException();
    }

    /**
     * @param r
     */
    public Cell chooseNextCell(Robot r) {
        // TODO - implement Environment.chooseNextCell
        throw new UnsupportedOperationException();
    }

    /**
     * @param t
     */
    private Cell chooseRandomCellAround(@NotNull Tuple t) {
        ArrayList<Tuple> availableChoices = get4PositionsAround(t);

        int randomInt = ThreadLocalRandom.current().nextInt(0, availableChoices.size());
        Tuple selectedTuple = availableChoices.get(randomInt);

        return cells.get(selectedTuple);
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
            cellsAround.add(cells.get(cells.get(position)));
        }

        return cellsAround;
    }

    /**
     * @param t Position entourée.
     * @return Les quatre positions entourant une position (gauche, droit, top, bottom).
     */
    private ArrayList<Tuple> get4PositionsAround(Tuple t) {
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
     * @param t
     * @return
     */
    private boolean _isPreviousYTopOfBottomBorder(Tuple t) {
        return t.getY() - 1 >= 0;
    }

    /**
     * Vérifie que t.x - 1 n'est pas inférieur à zéro.
     * @param t
     * @return
     */
    private boolean _isPreviousXAfterLeftBorder(Tuple t) {
        return t.getX() - 1 >= 0;
    }

    /**
     * Vérifie que t.y + 1 n'est pas supérieur ou égal à m.
     * @param t
     * @return
     */
    private boolean _isNextYBelowTopBorder(Tuple t) {
        return t.getY() + 1 < m;
    }

    /**
     * Vérifie que t.x + 1 n'est pas supérieur ou égal à n.
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
        ArrayList<Tuple> positions = get4PositionsAround(t);

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