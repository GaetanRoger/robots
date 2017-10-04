package fr.polytech.robots;

import com.sun.istack.internal.NotNull;


public class Cell {

	private Tuple position;
	private Robot robot = null;
	private Resource resource;

    /**
     * Ajoute un robot à la case.
     *
     * @param r Robot à ajouter.
     */
	public void addRobot(@NotNull Robot r) throws UnsupportedOperationException {
	    if (r == null)
	        throw new NullPointerException("r ne peut pas être null.");
	    if (this.robot != null)
	        throw new UnsupportedOperationException("Un robot est déjà sur cette cellule (utiliser removeRobot() pour l'enlever).");

		this.robot = r;
	}

    /**
     * Supprime le robot de la case.
     *
     * Si aucun robot n'est sur la case, ne fait rien.
     */
	public void removeRobot() {
		this.robot = null;
	}

    /**
     * @return La position de la case.
     */
	public Tuple getPosition() {
		return this.position;
	}

    /**
     * Supprime la ressource de la case.
     *
     * S'il n'y a pas de ressource sur la case, ne fait rien.
     */
	public void removeResource() {
		this.resource = null;
	}

    /**
     * Ajoute une ressource à la case.
     * @param r Ressource à ajouter à la case.
     */
	public void addResource(@NotNull Resource r) throws UnsupportedOperationException {
	    if (r == null)
	        throw new NullPointerException("r ne peut pas être null.");
        if (this.resource != null)
            throw new UnsupportedOperationException("Une ressource est déjà sur cette cellule (utiliser removeResource() pour l'enlever).");

	    this.resource = r;
	}

    public Robot getRobot() {
        return robot;
    }

    public Resource getResource() {
        return resource;
    }

    public void setPosition(Tuple position) {
        this.position = position;
    }
}