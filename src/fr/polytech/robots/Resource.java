package fr.polytech.robots;

import com.sun.istack.internal.NotNull;

/**
 * Classe représentant une ressource.
 */
public class Resource {

    /**
     * Type de la ressource.
     */
    private ResourceType type;

    /**
     * Constructeur.
     *
     * @param type Type de la ressource.
     */
    public Resource(@NotNull ResourceType type) {
        if (type == null)
            throw new NullPointerException("type ne peut pas être null.");

        this.type = type;
    }


    /**
     * @return le type de la ressource.
     */
    public ResourceType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "Resource n°" + hashCode() + " (" + type + ")";
    }
}