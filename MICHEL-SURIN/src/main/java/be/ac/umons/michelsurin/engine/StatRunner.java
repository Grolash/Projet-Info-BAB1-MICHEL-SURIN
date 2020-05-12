package be.ac.umons.michelsurin.engine;

import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarStyle;

import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * class designed to run a given number of game between 2 AI and output their winning ratio and their execution time.
 *
 */
public class StatRunner {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Hello, welcome to the Quoridor's statistic mode");
        int numbGame = readInt("Please choose how many games you want to run : ");
        System.out.println("-----------------------------------------------");
        System.out.println("Please choose the first AI");
        String firstAI = readType("the differents types are : Random, Easy, Hard");
        System.out.println("-----------------------------------------------");
        System.out.println("Please choose the second AI");
        String secondAI = readType("the differents types are : Random, Easy, Hard");

        String firstAI2 = firstAI + "1"; //used to prevent key value stack
        String secondAI2 = secondAI + "2"; //used to prevent key value stack
        Hashtable<String, Integer> winTable = new Hashtable<String, Integer>();
        winTable.put(firstAI2,0);
        winTable.put(secondAI2,0);

        Hashtable<String, Long> timeTable = new Hashtable<>();
        timeTable.put(firstAI2, (long) 0);
        timeTable.put(secondAI2, (long) 0);
        System.out.println("Start running games");
        //TODO author of the progress bar code : https://github.com/ctongfei/progressbar
        try (ProgressBar progressBar = new ProgressBar("Game played", numbGame, ProgressBarStyle.ASCII)) {
            for (int i=0; i<numbGame; i++) {
                Game game = new Game(firstAI, secondAI);
                game.statLoop(winTable, timeTable);
                progressBar.step();
            }
        }

        double ratioAI1 = winTable.get(firstAI2).floatValue() / numbGame;
        double ratioAI2 = winTable.get(secondAI2).floatValue() / numbGame;

        long timeRatioAI1 = timeTable.get(firstAI2) / numbGame;
        long timeRatioAI2 = timeTable.get(secondAI2) / numbGame;
        System.out.println("1 : " + firstAI + " ratio : " + ratioAI1);
        System.out.println("Average turn time (in ms) : " + timeRatioAI1);
        System.out.println("---------------------------");
        System.out.println("2 : " + secondAI + " ratio : " + ratioAI2);
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
                case "Easy":
                case "Hard":
                    return type;
                default:
                    throw new InputMismatchException("Invalid type");
            }
        } catch (InputMismatchException invalidType) {
            return readType("Invalid type chosen.");
        }
    }
}
