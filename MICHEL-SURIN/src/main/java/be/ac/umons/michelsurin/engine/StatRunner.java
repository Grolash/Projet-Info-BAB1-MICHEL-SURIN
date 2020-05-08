package be.ac.umons.michelsurin.engine;

import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * class designed to run a given number of game between 2 AI and output their winning ratio.
 *
 */
public class StatRunner {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Hello, welcome to the Quoridor's statistic mode");
        int numbGame = readInt("Please choose how many games you want to run : ");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please choose the first AI");
        String firstAI = scanner.next();
        System.out.println("Please choose the second AI");
        String secondAI = scanner.next();

        Hashtable<String, Integer> winTable = new Hashtable<String, Integer>();
        winTable.put(firstAI,0);
        winTable.put(secondAI,0);

        Hashtable<String, Long> timeTable = new Hashtable<>();
        timeTable.put(firstAI, (long) 0);
        timeTable.put(secondAI, (long) 0);
        System.out.println("Start running games");
        for (int i=0; i<numbGame; i++) {
            Game game = new Game(firstAI, secondAI);
            game.statLoop(winTable, timeTable);
            System.out.println("--game played--");
        }
        double ratioAI1 = winTable.get(firstAI).floatValue() / numbGame;
        double ratioAI2 = winTable.get(secondAI).floatValue() / numbGame;

        long timeRatioAI1 = timeTable.get(firstAI).longValue() / numbGame;
        long timeRatioAI2 = timeTable.get(secondAI).longValue() / numbGame;
        System.out.println(firstAI + " ratio : " + ratioAI1);
        System.out.println("Average turn time (in ms) : " + timeRatioAI1);
        System.out.println("---------------------------");
        System.out.println(secondAI + " ratio : " + ratioAI2);
        System.out.println("Average turn time (in ms) : " + timeRatioAI2);

    }

    public static int readInt(String prompt) {
        System.out.println(prompt);
        Scanner scanner = new Scanner(System.in);
        try {
            return scanner.nextInt();

        } catch (InputMismatchException invalidInt) {
            return readInt("Invalid input, please enter a correct integer : ");
        }
    }
}
