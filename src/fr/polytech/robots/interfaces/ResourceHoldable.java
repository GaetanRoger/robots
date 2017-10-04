package fr.polytech.robots.interfaces;

import fr.polytech.robots.Resource;

public interface ResourceHoldable {
    void addResource(Resource r);
    void removeResource();
}
