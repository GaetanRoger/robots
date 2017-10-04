package fr.polytech.robots;

import com.sun.istack.internal.NotNull;

public class Cell {

	private Tuple position;
	private Robot robot;
	private Resource resource;

    /**
     * Ajoute un robot à la case.
     *
     * @param r Robot à ajouter.
     */
	public void addRobot(@NotNull Robot r) {
		this.robot = r;
	}

    /**
     * Supprime le robot de la case.
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
     */
	public void removeResource() {
		this.resource = null;
	}

    /**
     * Ajoute une ressource à la case.
     * @param r Ressource à ajouter à la case.
     */
	public void addResource(@NotNull Resource r) {
		this.resource = r;
	}

}