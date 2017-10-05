package fr.polytech.robots;

import com.sun.istack.internal.NotNull;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Classe représentant un environnement.
 * <p>
 * L'environnement est le point d'entrée de la simulation et en dirige le déroulement.
 */
public class Environment {

    /**
     * Cases et leur position.
     */
    private Map<Tuple, Cell> cells = new HashMap<>();

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

    /**
     * Nombre de cycles de la simulation.
     */
    private int cycles = 10;

    /**
     * Cycle courant.
     */
    private int currentCycle = 0;

    /**
     * Constructeur.
     *
     * @param n              Largeur de la grille.
     * @param m              Hauteur de la grille.
     * @param robotCount     Nombre de robots à placer sur la grille.
     * @param resourcesCount Nombre de ressource de chaque type à placer sur la grille.
     * @param cycles         Nombre de cycles de la simulation.
     */
    public Environment(int n, int m, int robotCount, int resourcesCount, int cycles) {
        this(n, m, robotCount, resourcesCount);
        this.cycles = cycles;
    }

    /**
     * Constructeur.
     *
     * @param n              Largeur de la grille.
     * @param m              Hauteur de la grille.
     * @param robotCount     Nombre de robots à placer sur la grille.
     * @param resourcesCount Nombre de ressource de chaque type à placer sur la grille.
     */
    public Environment(int n, int m, int robotCount, int resourcesCount) {
        this.n = n;
        this.m = m;

        _createCells(n, m);
        _createResources(resourcesCount);
        _createRobots(robotCount);
    }

    /**
     * @return les cellules de la grille sous la forme d'une association `postion -> cellule`.
     */
    public Map<Tuple, Cell> getCells() {
        return cells;
    }

    /**
     * @return tous les robots présents sur la grille.
     */
    public ArrayList<Robot> getRobots() {
        return robots;
    }

    /**
     * @return la largeur de la grille.
     */
    public int getN() {
        return n;
    }

    /**
     * @return la hauteur de la grille.
     */
    public int getM() {
        return m;
    }

    /**
     * Crée les cellules de la grille et leur position.
     *
     * @param n Largeur de la grille.
     * @param m Hauteur de la grille.
     */
    private void _createCells(int n, int m) {
        for (int x = 0; x < n; ++x) {
            for (int y = 0; y < m; ++y) {
                Tuple position = new Tuple(x, y);
                Cell cell = new Cell(position);

                cells.put(position, cell);
            }
        }
    }

    /**
     * Crée les ressources et les place sur la grille.
     *
     * @param resourcesCount Nombre de ressource de chaque type à placer.
     */
    private void _createResources(int resourcesCount) {
        for (int i = 0; i < resourcesCount; ++i) {
            for (ResourceType resourceType : ResourceType.values()) {
                _createOneResource(resourceType);
            }
        }
    }

    /**
     * Create a resource.
     *
     * @param resourceType Type of the resource to be created.
     */
    private void _createOneResource(ResourceType resourceType) {
        Resource resource = new Resource(resourceType);
        boolean resourceWasAdded = false;

        while (!resourceWasAdded) {
            Tuple randPosition = _generateRandomPosition();
            Cell cell = cells.get(randPosition);

            resourceWasAdded = cell.tryAddResource(resource);
        }
    }

    /**
     * Crée les robots et les place sur la grille.
     *
     * @param robotCount Nombre de robots à placer sur la grille.
     */
    private void _createRobots(int robotCount) {
        for (int i = 0; i < robotCount; ++i) {
            _createOneRobot(i);
        }
    }

    /**
     * Crée un robot.
     *
     * @param i ID du robot à créer.
     */
    private void _createOneRobot(int i) {
        Cell cell = null;
        Robot robot = new Robot(i);
        robot.setEnvironment(this);
        boolean robotWasAdded = false;

        while (!robotWasAdded) {
            Tuple randPosition = _generateRandomPosition();
            cell = cells.get(randPosition);

            robotWasAdded = cell.tryAddRobot(robot);
        }

        robot.setCell(cell);
        robots.add(robot);
    }

    /**
     * Lance la simulation jusqu'à sa fin, c'est-à-dire pendant `cycles` cycles.
     */
    public void runSimulation() {
        Log.log("Lancement de la simulation sur " + cycles + " cycles.");

        while (currentCycle < cycles)
            runNextCycle();
    }

    /**
     * Lance la simulation du prochaine cycle.
     */
    public void runNextCycle() {
        if (currentCycle == cycles)
            throw new UnsupportedOperationException("Le nombre maximal de cycle est atteint.");

        currentCycle++;

        Log.log("Lancement du cycle n°" + currentCycle);

        for (Robot robot : robots) {
            robot.move();
        }
    }

    /**
     * Mesure la qualité du résulat de la simulation.
     */
    public void measureQuality() {
        // TODO - implement Environment.measureQuality
        throw new UnsupportedOperationException();
    }

    /**
     * Détermine la prochaine cellule sur laquelle le robot devrait aller.
     *
     * @param r Robot à déplacer.
     * @return la cellule où le robot devrait aller.
     * @// FIXME: 04/10/2017 vérifier qu'il n'y a pas un autre robot sur la cellule de destination
     */
    public Cell chooseNextCell(Robot r) {
        return chooseRandomCellAround(r.getCell().getPosition(), true);
    }

    /**
     * @param t           Une position.
     * @param avoidRobots Vrai pour ne pas retourner les cellules où un robot est présent.
     * @return Une cellule aléatoire autour de cette position, ou null si aucune cellule ne peut être choisie.
     */
    private Cell chooseRandomCellAround(@NotNull Tuple t, boolean avoidRobots) {
        ArrayList<Tuple> availableChoices = _get4PositionsAround(t);
        int randomInt;

        if (avoidRobots) {
            availableChoices = _filterOutCellsWithRobots(availableChoices);
        }

        try {
             randomInt = randomIntBetween(0, availableChoices.size());
        } catch (IllegalArgumentException e) {
            return null;
        }

        Tuple selectedTuple = availableChoices.get(randomInt);
        return cells.get(selectedTuple);
    }

    /**
     * Supprime les positions correspondants à des cellules contenant des robots.
     * @param availableChoices List de positions.
     * @return les positions correspondants à des cellules ne contenant pas des robots.
     */
    private ArrayList<Tuple> _filterOutCellsWithRobots(ArrayList<Tuple> availableChoices) {
        ArrayList<Tuple> availableChoicesWithoutRobots = new ArrayList<>();

        for (Tuple position : availableChoices) {
            if (cells.get(position).getRobot() == null)
                availableChoicesWithoutRobots.add(position);
        }

        availableChoices = availableChoicesWithoutRobots;
        return availableChoices;
    }

    /**
     * Génère un entier aléatoire dans un intervalle donné.
     *
     * @param min Borne inférieure, inclue.
     * @param max Borne supérieure, exclue.
     * @return l'entier aléatoire généré.
     */
    private int randomIntBetween(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    /**
     * @param r Robot au centre des cases.
     * @return les huit cases entourant le robot.
     */
    public ArrayList<Cell> getCellsAround(@NotNull Robot r) {
        return getCellsAround(r.getCell());
    }

    public ArrayList<Cell> getCellsAround(@NotNull Cell cell) {
        return getCellsAround(cell.getPosition());
    }

    public ArrayList<Cell> getCellsAround(@NotNull Tuple position) {
        ArrayList<Tuple> positionsAround = _get8PositionsAround(position);
        ArrayList<Cell> cellsAround = new ArrayList<>(positionsAround.size());

        for (Tuple t :
                positionsAround) {
            cellsAround.add(cells.get(t));
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

    /**
     * Vérifie que t.y - 1 n'est pas inférieur à zéro.
     *
     * @param t Position à tester.
     * @return vrai si la condition est vérifiée.
     */
    private boolean _isPreviousYTopOfBottomBorder(Tuple t) {
        return t.getY() - 1 >= 0;
    }

    /**
     * Vérifie que t.x - 1 n'est pas inférieur à zéro.
     *
     * @param t Position à tester.
     * @return vrai si la condition est vérifiée.
     */
    private boolean _isPreviousXAfterLeftBorder(Tuple t) {
        return t.getX() - 1 >= 0;
    }

    /**
     * Vérifie que t.y + 1 n'est pas supérieur ou égal à m.
     *
     * @param t Position à tester.
     * @return vrai si la condition est vérifiée.
     */
    private boolean _isNextYBelowTopBorder(Tuple t) {
        return t.getY() + 1 < m;
    }

    /**
     * Vérifie que t.x + 1 n'est pas supérieur ou égal à n.
     *
     * @param t Position à tester.
     * @return vrai si la condition est vérifiée.
     */
    private boolean _isNextXBeforeRightBorder(Tuple t) {
        return t.getX() + 1 < n;
    }

    /**
     * Génère une position aléatoire sur la grille.
     *
     * @return la position aléatoire générée.
     */
    private Tuple _generateRandomPosition() {
        int randX = randomIntBetween(0, n);
        int randY = randomIntBetween(0, m);
        return new Tuple(randX, randY);
    }

    public Cell getCellByPosition(Tuple position) {
        Cell c1 = this.getCells().get(position);
        return c1;

    }
}