package fr.polytech.test.robots;

import fr.polytech.robots.Resource;
import fr.polytech.robots.ResourceType;
import org.junit.Assert;
import org.junit.Test;

public class ResourceTest {
    @Test
    public void testResourceConstructor() {
        Resource resource = new Resource(ResourceType.BLEUE);

        Assert.assertEquals(ResourceType.BLEUE, resource.getType());
    }

    @Test (expected = NullPointerException.class)
    public void testResourceNullConstructor() {
        Resource resource = new Resource(null);
    }

}
