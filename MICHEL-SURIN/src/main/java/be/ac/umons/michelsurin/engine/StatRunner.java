package be.ac.umons.michelsurin.engine;

import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarStyle;

import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * class designed to run a given number of game between 2 AI and output their winning ratio.
 *
 */
public class StatRunner {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Hello, welcome to the Quoridor's statistic mode");
        int numbGame = readInt("Please choose how many games you want to run : ");
        System.out.println("-----------------------------------------------");
        System.out.println("Please choose the first AI");
        System.out.println("the differents types are : Random, Easy, Hard");
        System.out.println("if you want an AI to play against itself, use a number like this :");
        String firstAI = readType("Random1 or Hard1 ");
        System.out.println("-----------------------------------------------");
        System.out.println("Please choose the second AI");
        System.out.println("the differents types are : Random, Easy, Hard");
        System.out.println("if you want an AI to play against itself, use a number like this :");
        String secondAI = readType("Random2 or Hard2");

        Hashtable<String, Integer> winTable = new Hashtable<String, Integer>();
        winTable.put(firstAI,0);
        winTable.put(secondAI,0);

        Hashtable<String, Long> timeTable = new Hashtable<>();
        timeTable.put(firstAI, (long) 0);
        timeTable.put(secondAI, (long) 0);
        System.out.println("Start running games");
        try (ProgressBar progressBar = new ProgressBar("Game played", numbGame, ProgressBarStyle.ASCII)) {
            for (int i=0; i<numbGame; i++) {
                Game game = new Game(firstAI, secondAI);
                game.statLoop(winTable, timeTable);
                progressBar.step();
            }
        }

        double ratioAI1 = winTable.get(firstAI).floatValue() / numbGame;
        double ratioAI2 = winTable.get(secondAI).floatValue() / numbGame;

        long timeRatioAI1 = timeTable.get(firstAI) / numbGame;
        long timeRatioAI2 = timeTable.get(secondAI) / numbGame;
        System.out.println(firstAI + " ratio : " + ratioAI1);
        System.out.println("Average turn time (in ms) : " + timeRatioAI1);
        System.out.println("---------------------------");
        System.out.println(secondAI + " ratio : " + ratioAI2);
        System.out.println("Average turn time (in ms) : " + timeRatioAI2);

    }

    /**
     *
     * @param prompt the message displayed by the method
     * @return an valid int given by user
     */
    public static int readInt(String prompt) {
        System.out.println(prompt);
        Scanner scanner = new Scanner(System.in);
        try {
            return scanner.nextInt();

        } catch (InputMismatchException invalidInt) {
            return readInt("Invalid input, please enter a correct integer : ");
        }
    }

    /**
     *
     * @param prompt the message displayed by the method
     * @return an valid String type given by user
     */
    public static String readType(String prompt) {
        System.out.println(prompt);
        Scanner scanner = new Scanner(System.in);
        try {
            String type = scanner.next();
            switch (type) {
                case "Random":
                case "Random1":
                case "Random2":
                case "Easy":
                case "Easy1":
                case "Easy2":
                case "Hard":
                case "Hard1":
                case "Hard2":
                    return type;
                default:
                    throw new InputMismatchException("Invalid type");
            }
        } catch (InputMismatchException invalidType) {
            return readType("Invalid type chosen.");
        }
    }
}
