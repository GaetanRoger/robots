package fr.polytech.test.robots;

import fr.polytech.robots.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class RobotTest {

    @Test
    public void testRobot(){
        Cell c1 = new Cell(new Tuple(0,0));
        Resource ress = new Resource(ResourceType.ROUGE);
        Robot r1 = new Robot(1,c1,ress);
        int id = r1.getId();
        Assert.assertEquals(1,r1.getId());
        Assert.assertEquals(c1, r1.getCell());
        Assert.assertEquals(ress,r1.getResource());
    }
    @Test
    public  void testnbRessourceOccurance()
    {
        Cell c1 = new Cell(new Tuple(0,0));
        Cell c2 = new Cell(new Tuple(1,0));
        Cell c3 = new Cell(new Tuple (2,0));
        Cell c4 = new Cell(new Tuple(3,0));
        Cell c5 = new Cell(new Tuple(0,1));
        Cell c6 = new Cell(new Tuple(1,1));
        Cell c7 = new Cell(new Tuple(2,1));
        Cell c8 = new Cell(new Tuple(3,1));
        Cell c9 = new Cell(new Tuple(0,2));
        Cell c10 = new Cell(new Tuple(1,2));
        Cell c11 = new Cell(new Tuple(2,2));
        Cell c12 = new Cell(new Tuple(3,2));
        Cell c13 = new Cell(new Tuple(0,3));
        Cell c14 = new Cell(new Tuple(1,3));
        Cell c15 = new Cell(new Tuple(2,3));
        Cell c16 = new Cell(new Tuple(3,3));

        ArrayList<Cell> cells = new ArrayList<>();
        cells.add(c1);
        cells.add(c2);
        cells.add(c3);
        cells.add(c4);
        cells.add(c5);
        cells.add(c6);
        cells.add(c7);
        cells.add(c8);
        cells.add(c9);
        cells.add(c10);
        cells.add(c11);
        cells.add(c12);
        cells.add(c13);
        cells.add(c14);
        cells.add(c15);
        cells.add(c16);
        c1.addResource(new Resource(ResourceType.BLEUE));
        c3.addResource(new Resource(ResourceType.BLEUE));

        Robot r1 = new Robot(1);
        double nb_found = r1.nbRessourceOccurance(cells, ResourceType.BLEUE);

        Assert.assertEquals(0.125,nb_found,0.0001);
    }

    @Test
    public void testCalculateProbaPick(){
        double f = 0.125 ;
        Robot r = new Robot(1);
        r.calculateProbaPick(f);
        r.setK_PLUS(0.5);
        double res = r.calculateProbaPick(f);
        // Expected : 0.64
//        Assert.assertEquals(0.5,r.getK_PLUS(),0.0001);
        Assert.assertEquals(0.64,res,0.0001);
    }
    @Test
    public void testCalculateProbaPut(){

      double f = 0.1;
      Robot r = new Robot(1);
      r.setK_MINUS(0.1);
      double res = r.calculateProbaPut(f);
      Assert.assertEquals(0.25,res,0.0001);
    }

}
