package fr.polytech.robots;

import fr.polytech.terminal.Terminal;

public class Main {

    public static void main(String[] args) {

        Environment env = new Environment(5,2,3,2,2);
        Terminal terminal = new Terminal();
        terminal.displayEnvironment(env);
    }
}
