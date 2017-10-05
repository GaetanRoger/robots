package fr.polytech.robots;

import com.sun.istack.internal.NotNull;
import com.sun.javaws.exceptions.ExitException;
import fr.polytech.robots.interfaces.ResourceHoldable;

import javax.management.InstanceAlreadyExistsException;
import java.util.ArrayList;
import java.util.Random;

public class Robot implements ResourceHoldable{

	private int id;
	private Cell cell;
	private Environment environment;
	private Resource resource;
	public double K_PLUS;
	public double K_MINUS;


	public Robot(int id, Cell cell, Resource resource) {
		this.id = id;
		this.cell = cell;
		this.resource = resource ;
	}
	public Robot(int id){
		this.id = id ;
	}


	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
	//TODO : May be an interfaces
	public void addResource(@NotNull Resource r) throws UnsupportedOperationException {
		if (r == null)
			throw new NullPointerException("r ne peut pas être null.");
		if (this.resource != null)
			throw new UnsupportedOperationException("Une ressource est déjà sur cette cellule (utiliser removeResource() pour l'enlever).");
		this.resource = r;
	}

	public void removeResource() {
		this.resource = null;
	}


	public double getK_PLUS() {
		return K_PLUS;
	}

	public void setK_PLUS(double k_PLUS) {
		K_PLUS = k_PLUS;
	}

	public double getK_MINUS() {
		return K_MINUS;
	}

	public void setK_MINUS(double k_MINUS) {
		K_MINUS = k_MINUS;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
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

	public double calculateProbaPick(double f) {
		//return (this.getK_PLUS()/(this.getK_PLUS() + f) ) ;
		return Math.pow((K_PLUS/(K_PLUS + f) ),2);
	}
	public double calculateProbaPut(double f) {
		return Math.pow((f/(K_MINUS+ f) ),2);
	}
	public double nbRessourceOccurance(ArrayList<Cell> cells, ResourceType rType){
		int nbOcc = 0 ;
		for(int i =0 ; i < cells.size();i++){
			Cell c = cells.get(i);
			Resource r = c.getResource();
			if(r != null){
				if(r.getType() == rType){
					nbOcc++;
				}
			}
		}
		return nbOcc/cells.size() ;
	}
	public boolean pickUp() {

		Cell c1 = this.getCell();
		Resource r1 = c1.getResource();
		if(r1 != null){
			ArrayList cellsAround = this.getEnvironment().getCellsAround(this);

			double f = nbRessourceOccurance(cellsAround,r1.getType()) ;
			double res = calculateProbaPick(f) ;

			Random rand = new Random();
			float f_rand =  rand.nextInt((1) + 1) ;

			if(f_rand<res){
				this.setResource(r1);
				c1.removeResource();
				return true ;
			}
		}
		return false;
	}

	public boolean putDown() {

		Cell c2 = this.getCell();
		Resource r2 = c2.getResource();
		if(r2!=null){
			System.out.println("Case chargée, dépot impossible");
		} else {
			ArrayList cellsAround = this.getEnvironment().getCellsAround(this);
			double f = nbRessourceOccurance(cellsAround,r2.getType()) ;
			double res = calculateProbaPut(f) ;
			Random rand = new Random();
			float f_rand =  rand.nextInt((1) + 1) ;

			if(f_rand<res) {
				c2.addResource(r2);
				this.removeResource();
				return true;
			}
		}

		return false;
	}

	public void move() {
		Cell current_cell = this.getCell();
		Environment env = this.getEnvironment() ;
		Cell next_cell = env.chooseNextCell(this);
		current_cell.removeRobot();
		next_cell.addRobot(this);
		this.setCell(next_cell);
	}




}