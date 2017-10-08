package fr.polytech.terminal;

public class ArgumentsManager {
    public static int n = 30;
    public static int m = 30;
    public static int robotsCount = 100;
    public static int resourceCount = 75;
    public static int cycles = 10000;
    public static boolean print = true;
    public static int delay = 0;
    public static int steps = 10;
    public static boolean drawRobots = true;
    public static boolean drawHeldResources = true;

    public static void extractArgs(String[] args) {
        if (args.length > 0) {
            try {
                _readArgs(args);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                _printUsage(args);
                System.out.flush();
                System.exit(1);
            }
        }
    }

    private static void _readArgs(String[] args) throws Exception {
        for (String arg : args) {
            String[] split = arg.split("=");
            String command;
            String value;

            try {
                command = split[0];
                value = split[1];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new Exception("Argument format invalid: " + arg, e);
            }

            try {
                _extractArg(command, value);
            } catch (NumberFormatException e) {
                throw new Exception("Invalid value given for " + command + ": " + value, e);
            }
        }
    }

    private static void _printUsage(String[] args) {
        System.out.println("\nUsage: java -jar robots.jar [arguments]");
        System.out.println("Arguments:");
        System.out.println(" * n=[1-9]+ Largeur de la grille");
        System.out.println(" * m=[1-9]+ Hauteur de la grille");
        System.out.println(" * robots=[1-9]+ Nombre de robots sur la grille");
        System.out.println(" * resources=[1-9]+ Nombre de ressources de chaque type sur la grille");
        System.out.println(" * cycles=[1-9]+ Nombre de cycles de simulation");
        System.out.println(" * print=true|false Dessin la grille a chaque etape de la simulation ou non");
        System.out.println(" * steps=[1-9]+ Nombre de tour de simulation avant chaque dessin");
        System.out.println(" * delay=[1-9]+ Nombre de millisecondes entre chaque dessin");
        System.out.println(" * drawrobots=true|false Dessinne ou non les robots");
        System.out.println(" * drawheldresources=true|false Dessinne ou non les ressources que portent les robots");
    }

    private static void _extractArg(String command, String value) throws Exception {
        switch (command) {
            case "n":
                n = Integer.parseInt(value);
                break;
            case "m":
                m = Integer.parseInt(value);
                break;
            case "robots":
                robotsCount = Integer.parseInt(value);
                break;
            case "resources":
                resourceCount = Integer.parseInt(value);
                break;
            case "cycles":
                cycles = Integer.parseInt(value);
                break;
            case "print":
                print = Boolean.parseBoolean(value);
                break;
            case "delay":
                delay = Integer.parseInt(value);
                break;
            case "steps":
                steps = Integer.parseInt(value);
                break;
            case "drawrobots":
                drawRobots = Boolean.parseBoolean(value);
                break;
            case "drawheldresources":
                drawHeldResources = Boolean.parseBoolean(value);
                break;
            default:
                throw new Exception("Unknown command: " + command);
        }
    }
}
