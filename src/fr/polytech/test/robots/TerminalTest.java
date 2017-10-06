package fr.polytech.test.robots;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import fr.polytech.robots.*;
import fr.polytech.robots.Robot;
import fr.polytech.terminal.Terminal;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class TerminalTest {

    @Test
    public void testPrintCellEmpty(){

        Cell c1 = new Cell(new Tuple(0,0));
        Terminal t = new Terminal();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        t.printCell(c1);
        Assert.assertEquals("o",outContent.toString());


    }
    @Test
    public void testPrintCellR(){

        Cell c1 = new Cell(new Tuple(0,1));
        Robot r1 = new Robot(1);
        c1.addRobot(r1);
        Terminal t = new Terminal();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        t.printCell(c1);
        Assert.assertEquals(Color.green +"r",outContent.toString());

    }
    @Test
    public void testPrintCellBlueRessource(){
        Cell c1 = new Cell(new Tuple(0,1));
        Resource resource = new Resource(ResourceType.BLEUE);
        c1.addResource(resource);
        Terminal t = new Terminal();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        t.printCell(c1);
        Assert.assertEquals(Color.blue +"x",outContent.toString());


    }
    @Test
    public void testPrintCellRedRessource(){
        Cell c2 = new Cell(new Tuple(1,1));
        Resource resource1 = new Resource(ResourceType.ROUGE);
        c2.addResource(resource1);
        Terminal t = new Terminal();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        t.printCell(c2);
        Assert.assertEquals(Color.RED+"x",outContent.toString());

    }
    @Test
    public void testDisplayEnvironment(){
        Cell c1 = new Cell(new Tuple(0,0));
        Cell c2 = new Cell(new Tuple(1,0));
        Cell c3 = new Cell(new Tuple (2,0));
        Cell c4 = new Cell(new Tuple(3,0));
        Cell c5 = new Cell(new Tuple(0,1));
        Cell c6 = new Cell(new Tuple(1,1));
        Cell c7 = new Cell(new Tuple(2,1));
        Cell c8 = new Cell(new Tuple(3,1));
        Cell c9 = new Cell(new Tuple(0,2));

    }
}
