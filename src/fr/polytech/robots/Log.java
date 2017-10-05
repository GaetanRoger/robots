package fr.polytech.robots;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;

public class Log {
    public static boolean enabled = true;

    private static final String _FILE = "actions.log";
    private static FileWriter fileWriter;
    private static BufferedWriter bufferedWriter;
    private static PrintWriter printWriter;

    public static void log(String message) {
        if (!enabled) return;

        if (fileWriter == null || bufferedWriter == null || printWriter == null) {
            try {
                _instanciate();
            } catch (IOException e) {
                System.out.println("Could not open file to write: " + _FILE);
                return;
            }
        }


        Date date = Calendar.getInstance().getTime();
        printWriter = new PrintWriter(bufferedWriter);

        printWriter.print("[" + date.toString() + "] ");
        printWriter.print("\"" + message + "\"");

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length >= 3) {
            StackTraceElement stackTraceElement = stackTrace[2];
            String trace = "\t in " +
                    stackTraceElement.getClassName() + " " +
                    stackTraceElement.getMethodName() + "() [" +
                    stackTraceElement.getFileName() + ":" +
                    stackTraceElement.getLineNumber() + "]";

            printWriter.print(trace);
        }

        printWriter.println();

        printWriter.close();
        try {
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        printWriter = null;
        bufferedWriter = null;
        fileWriter = null;
    }

    private static void _instanciate() throws IOException {
        fileWriter = new FileWriter(_FILE, true);
        bufferedWriter = new BufferedWriter(fileWriter);
    }
}
