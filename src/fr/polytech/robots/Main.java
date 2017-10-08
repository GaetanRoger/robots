package fr.polytech.robots;

import fr.polytech.terminal.ArgumentsManager;
import fr.polytech.terminal.Terminal2;

public class Main {
    private final static String VERSION = "1.0";


    public static void main(String[] args) {
        Log.enabled = false;
        ArgumentsManager.version = VERSION;
        ArgumentsManager.extractArgs(args);

        Environment env = new Environment(
                ArgumentsManager.n,
                ArgumentsManager.m,
                ArgumentsManager.robotsCount,
                ArgumentsManager.resourceCount,
                ArgumentsManager.cycles
        );
        Terminal2 terminal2 = new Terminal2(
                env,
                ArgumentsManager.drawRobots,
                ArgumentsManager.drawHeldResources
        );
        
        if (ArgumentsManager.print)
            terminal2.runAndPrint(
                    ArgumentsManager.delay,
                    ArgumentsManager.steps
            );
        else
            terminal2.run();
    }

}
