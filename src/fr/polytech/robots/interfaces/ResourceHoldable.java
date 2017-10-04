package fr.polytech.robots.interfaces;

import fr.polytech.robots.Resource;

/**
 * Interface représentant un objet pouvant contenir une ressource.
 */
public interface ResourceHoldable {

    /**
     * Ajoute une ressource.
     *
     * @param r Ressource à ajouter.
     */
    void addResource(Resource r);

    /**
     * Supprime la ressource.
     */
    void removeResource();
}
