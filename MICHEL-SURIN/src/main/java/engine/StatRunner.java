package engine;

import engine.Game;

import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StatRunner {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello, welcome to the Quoridor's statistic mode");
        int numbGame = readInt("Please choose how many games you want to run : ", scanner);
        System.out.println("Please choose the first AI");
        String firstAI = scanner.next();
        System.out.println("Please choose the second AI");
        String secondAI = scanner.next();
        Hashtable<String, Integer> winTable = new Hashtable<String, Integer>();
        winTable.put(firstAI,0);
        winTable.put(secondAI,0);
        System.out.println("Start running games");
        for (int i=0; i<numbGame; i++) {
            Game game = new Game(firstAI, secondAI);
            game.statLoop(winTable);
            System.out.println("--game played--");
        }
        double ratioAI1 = winTable.get(firstAI).floatValue() / numbGame;
        double ratioAI2 = winTable.get(secondAI).floatValue() / numbGame;
        System.out.println(firstAI + " ratio : " + ratioAI1);
        System.out.println(secondAI + " ratio : " + ratioAI2);

    }

    public static int readInt(String prompt, Scanner scanner) {
        System.out.println(prompt);
        try {
            return scanner.nextInt();
        } catch (InputMismatchException invalidInt) {
            return readInt("Invalid entry, please enter a correct integer : ", scanner);
        }
    }
}
