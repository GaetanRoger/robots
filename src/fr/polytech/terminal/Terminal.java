package fr.polytech.terminal;

import fr.polytech.robots.*;

import java.awt.*;
import java.util.Map;

public class Terminal {

    public Terminal() {
    }

    public void displayMain(){

        System.out.println("***********************************************************************");
        System.out.println("************** Welcome to the Robot's World ***************************");
        System.out.println("***********************************************************************");
        System.out.println("Choose one of those options : ");
        System.out.println("1-  Play");
        System.out.println("2-  Exit");
    }


    public void printCell(Cell cell) {

        if (cell.isEmpty()){
            System.out.print("o");
        } else if (cell.getResource() !=null){
            Resource r1 = cell.getResource();
            if(r1.getType() == ResourceType.BLEUE){
                System.out.print(Color.blue + "x");
            } else if(r1.getType() == ResourceType.ROUGE){
                System.out.print(Color.RED + "x");
            }
        } else if( cell.getRobot() != null){
            System.out.print(Color.green + "r");
        }
    }
    public void displayEnvironment(Environment env){
        Map<Tuple, Cell> cells = env.getCells();
        for ( Cell cell: cells.values()
             ) {
                for(int i =0; i<env.getN(); i++){
                    for(int j=0; i<env.getM(); j++){
                        Cell c = env.getCellByPosition(new Tuple(i,j));
                        if(i== env.getN()){
                            printCell(c);
                            System.out.print("\n");
                        } else {
                            printCell(c);
                        }
                    }
                }
        }
    }
}
