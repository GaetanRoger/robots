package fr.polytech.robots;

public class Robot {

	private Cell cell;
	private Environment environment;
	private Resource resource;
	public float K_PLUS;
	public float K_MINUS;

	public bool pickUp() {
		// TODO - implement Robot.pickUp
		throw new UnsupportedOperationException();
	}

	public void putDown() {
		// TODO - implement Robot.putDown
		throw new UnsupportedOperationException();
	}

	public void move() {
		// TODO - implement Robot.move
		throw new UnsupportedOperationException();
	}

	public Cell getCell() {
		return this.cell;
	}

	/**
	 * 
	 * @param c
	 */
	public void setCell(Cell c) {
		this.cell = c;
	}

	private float calculateProba() {
		// TODO - implement Robot.calculateProba
		throw new UnsupportedOperationException();
	}

}