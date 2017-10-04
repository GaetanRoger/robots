package fr.polytech.robots;

import com.sun.istack.internal.NotNull;

public class Resource {

    private ResourceType type;

    public Resource(@NotNull ResourceType type) {
        if (type == null)
            throw new NullPointerException("type ne peut pas être null.");

        this.type = type;
    }

    public ResourceType getType() {
        return this.type;
    }

}