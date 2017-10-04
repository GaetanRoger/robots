package fr.polytech.robots;

import com.sun.istack.internal.NotNull;
import fr.polytech.robots.interfaces.ResourceHoldable;

/**
 * Classe représentant une case de la grille de l'environnement.
 * <p>
 * Une case a une position, et peut-être vide ou contenir :
 * <ul>
 * <li> Un robot ;</li>
 * <li> Une ressource.</li>
 * </ul>
 * Une case ne peut pas contenir plusieurs robots ou plusieurs ressource, ni même un robot et une ressource.
 */
public class Cell implements ResourceHoldable {

    /**
     * Position de la case.
     */
    private Tuple position;

    /**
     * Robot de la case (null si non présent).
     */
    private Robot robot;

    /**
     * Ressource de la case (null si non présente).
     */
    private Resource resource;

    /**
     * Constructeur.
     *
     * @param position Position de la cellule.
     */
    public Cell(Tuple position) {
        this.position = position;
    }

    /**
     * @return La position de la case.
     */
    public Tuple getPosition() {
        return this.position;
    }

    /**
     * @return le robot sur la case, ou null s'il n'y en a pas.
     */
    public Robot getRobot() {
        return robot;
    }

    /**
     * @return la ressource sur la case, ou null s'il n'y en a pas.
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * Ajoute un robot à la case.
     *
     * @param r Robot à ajouter.
     */
    public void addRobot(@NotNull Robot r) {
        if (r == null)
            throw new NullPointerException("r ne peut pas être null.");
        if (this.robot != null)
            throw new UnsupportedOperationException("Un robot est déjà sur cette cellule (utiliser removeRobot() pour l'enlever).");
        if (this.resource != null)
            throw new UnsupportedOperationException("Une ressource est déjà sur cette cellule (utiliser removeResource() pour l'enlever).");

        this.robot = r;
    }

    /**
     * Essaie d'ajouter un robot.
     * <p>
     * Cette méthode ne lève pas d'exception en cas d'échec mais se
     * contente de renvoyer un booléen valant faux.
     *
     * @param r Robot à ajouter.
     * @return vrai si l'ajout est réussi, faux sinon.
     */
    public boolean tryAddRobot(@NotNull Robot r) {
        try {
            addRobot(r);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Supprime le robot de la case.
     * <p>
     * Si aucun robot n'est sur la case, ne fait rien.
     */
    public void removeRobot() {
        this.robot = null;
    }

    /**
     * Ajoute une ressource à la case.
     *
     * @param r Ressource à ajouter à la case.
     */
    public void addResource(@NotNull Resource r) {
        if (r == null)
            throw new NullPointerException("r ne peut pas être null.");
        if (this.resource != null)
            throw new UnsupportedOperationException("Une ressource est déjà sur cette cellule (utiliser removeResource() pour l'enlever).");
        if (this.robot != null)
            throw new UnsupportedOperationException("Un robot est déjà sur cette cellule (utiliser removeRobot()).");

        this.resource = r;
    }

    /**
     * Essaie d'ajouter une ressource.
     * <p>
     * Cette méthode ne lève pas d'exception en cas d'échec mais se
     * contente de renvoyer un booléen valant faux.
     *
     * @param r Ressource à ajouter.
     * @return vrai si l'ajout est réussi, faux sinon.
     */
    public boolean tryAddResource(@NotNull Resource r) {
        try {
            addResource(r);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Supprime la ressource de la case.
     * <p>
     * S'il n'y a pas de ressource sur la case, ne fait rien.
     */
    public void removeResource() {
        this.resource = null;
    }
}