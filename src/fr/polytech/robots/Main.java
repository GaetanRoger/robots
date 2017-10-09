package fr.polytech.robots;

import com.sun.prism.paint.Color;
import fr.polytech.terminal.Terminal;

public class Main {

    public static void main(String[] args) {

        Environment env = new Environment(4,4,3,2,2);
        Terminal terminal = new Terminal();

        terminal.displayEnvironment(env);
    }
}
