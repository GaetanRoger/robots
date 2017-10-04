package fr.polytech.test.robots;

import fr.polytech.robots.Environment;
import org.junit.Assert;
import org.junit.Test;

public class EnvironmentTest {
    @Test
    public void testConstructor() {
        Environment environment = new Environment(2, 3, 1, 1);

        Assert.assertEquals(2, environment.getN());
        Assert.assertEquals(3, environment.getM());
        Assert.assertEquals(1, environment.getRobots().size());
    }
}
