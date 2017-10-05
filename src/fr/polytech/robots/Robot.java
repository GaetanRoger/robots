package fr.polytech.robots;

import com.sun.istack.internal.NotNull;
import com.sun.javaws.exceptions.ExitException;
import fr.polytech.robots.interfaces.ResourceHoldable;

import javax.management.InstanceAlreadyExistsException;
import java.util.ArrayList;
import java.util.Random;

public class Robot implements ResourceHoldable {

    private int id;
    private Cell cell;
    private Environment environment;
    private Resource resource;
    public float K_PLUS = 0.1f;
    public float K_MINUS = 0.1f;


    public Robot(int id, Cell cell, Resource resource) {
        this.id = id;
        this.cell = cell;
        this.resource = resource;
    }

    public Robot(int id) {
        this.id = id;
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


    public float getK_PLUS() {
        return K_PLUS;
    }

    public void setK_PLUS(float k_PLUS) {
        K_PLUS = k_PLUS;
    }

    public float getK_MINUS() {
        return K_MINUS;
    }

    public void setK_MINUS(float k_MINUS) {
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
     * @param c
     */
    public void setCell(Cell c) {
        this.cell = c;
    }

    private double calculateProbaPick(float f) {
        return Math.pow((K_PLUS / K_PLUS + f), 2);
    }

    private double calculateProbaPut(float f) {
        return Math.pow((f / (K_MINUS + f)), 2);
    }

    private float nbRessourceOccurance(ArrayList<Cell> cells, ResourceType rType) {
        float nbOcc = 0;

        for (Cell cell : cells) {
            if (cell.getResource() != null && cell.getResource().getType() == rType) {
                nbOcc++;
            }
        }

        return nbOcc;
    }

    public boolean pickUp() {

        Resource cellResource = cell.getResource();

        // Si la cellule comporte bien une ressource
        if (cellResource != null) {

            // Récupération des cellules entourant la ressource
            ArrayList<Cell> cellsAround = this.getEnvironment().getCellsAround(cell);

            // Calcul de la fréquence d'apparition de la ressource
            float f = nbRessourceOccurance(cellsAround, cellResource.getType());

            // Calcul de la probabilité de ramassage de la ressource.
            double proba = calculateProbaPick(f);

            // Génération d'un double aléatoire
            Random rand = new Random();
            double randomDouble = rand.nextDouble();

            // Si le double est inférieur, on ramasse la ressource
            if (randomDouble < proba) {
                Log.log(this.toString() + ": ramasse de " + cell.getResource() + " depuis " + cell.getPosition());

                cell.removeResource();
                this.setResource(cellResource);
                return true;
            }
        }
        return false;
    }

    public boolean putDown() {

        Resource cellResource = cell.getResource();

        // S'il n'y a pas de ressource sur la case et qu'on possède une ressource
        if (cellResource == null && this.resource != null) {

            // Récupération des cellules entourant celle-ci.
            ArrayList<Cell> cellsAround = this.getEnvironment().getCellsAround(cell);

            // Pour chacun des types de ressources
            for (ResourceType resourceType : ResourceType.values()) {

                // Calcul de la fréquence d'apparition de la ressource.
                float f = nbRessourceOccurance(cellsAround, resourceType);

                // Calcul de la probabilité de dépose.
                double proba = calculateProbaPut(f);

                // Génération d'un double aléatoire.
                Random rand = new Random();
                double randomDouble = rand.nextDouble();

                // Si le double est inférieur, on dépose la ressource.
                if (randomDouble < proba) {
                    Log.log(this.toString() + ": dépose de " + this.getResource() + " sur " + cell.getPosition());

                    cell.addResource(this.getResource());
                    this.removeResource();
                    return true;
                }
            }

        } // Sinon case chargée, dépot impossible

        return false;
    }

    public void move() {
        Environment env = this.getEnvironment();
        Cell nextCell = env.chooseNextCell(this);


        // Si une prochaine cellule est disponible
        if (nextCell != null) {
            Log.log(this.toString() + ": déplacement vers " + nextCell.getPosition());

            Cell oldCell = this.getCell();

            // Dépose de la ressource.
            putDown();

            // Suppression de l'ancienne case
            oldCell.removeRobot();
            setCell(null);

            // Ajout sur la nouvelle cellule
            nextCell.addRobot(this);
            setCell(nextCell);

            // Ramassage de la ressource
            pickUp();

        } // Sinon on reste sur place pour ce tour.
    }

    @Override
    public String toString() {
        String string =  "Robot n°" + id;
        if (cell != null && cell.getPosition() != null) {
            string += " " + cell.getPosition();
        }
        return string;
    }
}