package fr.polytech.test.robots;

import fr.polytech.robots.Tuple;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class TupleTest {
    @Test
    public void testConstructor() {
        Tuple tuple = new Tuple(1, 2);

        Assert.assertEquals(1, tuple.getX());
        Assert.assertEquals(2, tuple.getY());
    }

    @Test
    public void testToString() {
        Tuple tuple = new Tuple(1, 2);
        String string = tuple.toString();

        Assert.assertEquals("(1;2)", string);
    }
    
    @Test
    public void testEquals() {
        Tuple tuple1 = new Tuple(1, 2);
        Tuple tuple2 = new Tuple(1, 2);
        Tuple tuple3 = new Tuple(2, 1);

        Assert.assertTrue(tuple1.equals(tuple2));
        Assert.assertFalse(tuple1.equals(tuple3));
        Assert.assertFalse(tuple2.equals(tuple3));

        Assert.assertEquals(tuple1, tuple2);
        Assert.assertNotEquals(tuple1, tuple3);
        Assert.assertNotEquals(tuple2, tuple3);
    }

    @Test
    public void testHashCode() {
        Tuple tuple1 = new Tuple(1, 1);
        Tuple tuple2 = new Tuple(1, 1);
        Tuple tuple3 = new Tuple(11, 0);

        Assert.assertEquals(tuple1.hashCode(), tuple2.hashCode());
        Assert.assertNotEquals(tuple1.hashCode(), tuple3.hashCode());
        Assert.assertNotEquals(tuple2.hashCode(), tuple3.hashCode());
    }

    @Test
    public void testTuplesInHashMap() {
        HashMap<Tuple, Integer> hashMap = new HashMap<>();

        Tuple tuple1 = new Tuple(1, 1);
        Tuple tuple2 = new Tuple(1, 2);
        Tuple tuple3 = new Tuple(15, 1);

        hashMap.put(tuple1, 1);
        hashMap.put(tuple2, 2);
        hashMap.put(tuple3, 3);

        Assert.assertEquals(new Integer(1), hashMap.get(tuple1));
        Assert.assertEquals(new Integer(2), hashMap.get(tuple2));
        Assert.assertEquals(new Integer(3), hashMap.get(tuple3));

        Tuple tuple1_2 = new Tuple(1, 1);
        Tuple tuple2_2 = new Tuple(1, 2);
        Tuple tuple3_2 = new Tuple(15, 1);

        Assert.assertEquals(new Integer(1), hashMap.get(tuple1_2));
        Assert.assertEquals(new Integer(2), hashMap.get(tuple2_2));
        Assert.assertEquals(new Integer(3), hashMap.get(tuple3_2));
    }

    @Test
    public void testAddingEqualTuplesInHashMap() {
        HashMap<Tuple, Integer> hashMap = new HashMap<>();

        Tuple tuple1 = new Tuple(1, 2);
        Tuple tuple2 = new Tuple(1, 2);

        hashMap.put(tuple1, 1);
        hashMap.put(tuple2, 2);

        Assert.assertEquals(1, hashMap.size());
        Assert.assertEquals(new Integer(2), hashMap.get(tuple1));
        Assert.assertEquals(new Integer(2), hashMap.get(tuple2));
    }
}
