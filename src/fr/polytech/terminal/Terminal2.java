package fr.polytech.terminal;

import fr.polytech.robots.*;

import java.util.Map;

public class Terminal2 {
    private final static String[] COLORS = new String[] {
            "\u001B[41m", //red
            "\u001B[42m", //green
            "\u001B[43m", //yellow
            "\u001B[44m", // blue
            "\u001B[45m", //purple
            "\u001B[46m", //cyan
    };
    private final static String RESET_COLOR = "\u001B[0m";
    private Environment environment;
    private boolean drawRobots = true;
    private boolean drawHoldedResources = true;

    public Terminal2(Environment environment) {
        this.environment = environment;
    }

    public Terminal2(Environment environment, boolean drawRobots, boolean drawHoldedResources) {
        this(environment);
        this.drawRobots = drawRobots;
        this.drawHoldedResources = drawHoldedResources;
    }

    public void runAndPrint(int delay, int steps) {
        Map<Tuple, Cell> cells = environment.getCells();

        try {
            while (true) {
                _printGrid(cells);

                for (int i = 0; i < steps; i++) {
                    environment.runNextCycle();
                }

                if (delay > 0)
                    Thread.sleep(delay);
            }
        } catch (InterruptedException|UnsupportedOperationException ignored) {}


    }

    private void _printGrid(Map<Tuple, Cell> cells) {
        StringBuilder drawing = new StringBuilder("\033[H\033[2J");

        drawing = _appendLine(drawing);

        for (int y = 0; y < environment.getM(); y++) {
            drawing.append('|');
            for (int x = 0; x < environment.getN(); x++) {
                drawing = _appendCellDrawing(cells.get(new Tuple(x, y)), drawing);
            }
            drawing.append("|\n");
        }

        drawing = _appendLine(drawing);

        System.out.println(drawing);
    }

    public void run() {
        _printGrid(environment.getCells());
        System.out.println("^^^ Starting situation ^^^");
        System.out.println("Simulating...");
        environment.runSimulation();
        _printGrid(environment.getCells());
        System.out.println("^^^ Result ^^^");
    }

    private StringBuilder _appendLine(StringBuilder stringBuilder) {
        stringBuilder.append(' ');

        for (int i = 0; i < environment.getN(); i++) {
            stringBuilder.append('-');
        }

        stringBuilder.append("\n");

        return stringBuilder;
    }

    private StringBuilder _appendCellDrawing(Cell cell, StringBuilder stringBuilder) {
        if (cell.getResource() == null && cell.getRobot() == null) {
            stringBuilder.append(' ');
            return stringBuilder;
        }

        String toBePrinted = " ";

        if (drawRobots && cell.getRobot() != null)
            toBePrinted = "R";

        if (cell.getResource() != null) {
            toBePrinted = _getResourceColor(cell.getResource()) + toBePrinted + RESET_COLOR;
        } else if (drawHoldedResources && cell.getRobot().getResource() != null) {
            toBePrinted = _getResourceColor(cell.getRobot().getResource()) + toBePrinted + RESET_COLOR;
        }

        stringBuilder.append(toBePrinted);
        return stringBuilder;
    }

    private String _getResourceColor(Resource resource) {
        for (int i = 0; i < ResourceType.values().length; ++i)
            if (resource.getType().equals(ResourceType.values()[i]))
                return COLORS[i];

        return "";
    }
}
