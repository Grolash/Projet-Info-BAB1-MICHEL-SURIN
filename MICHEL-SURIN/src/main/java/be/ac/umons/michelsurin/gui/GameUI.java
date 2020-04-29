package be.ac.umons.michelsurin.gui;

import be.ac.umons.michelsurin.controller.Action;
import be.ac.umons.michelsurin.controller.PawnController;
import be.ac.umons.michelsurin.engine.Game;
import be.ac.umons.michelsurin.engine.Rules;
import be.ac.umons.michelsurin.tools.Coord;
import be.ac.umons.michelsurin.world.Board;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;


public class GameUI extends Application {

    private Image cellImg = new Image("tile.png");
    private Image humanPawnImg = new Image("neo.png");
    private Image AIPawnImg = new Image("agent.png");
    private Image wallHImg = new Image("wallH.png");
    private Image wallVImg = new Image("wallV.png");
    public static final int Hspace = 50;
    public static final int Vspace = 50;
    private static ColorAdjust colorCell = new ColorAdjust(0.1, 0, 0.5, 0.5);
    private static Shadow wallShadow = new Shadow(10, Color.RED);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        String[] type = {"Human", "Human"};
        int numbOfWall = 10;
        Game game = new Game(9, type, numbOfWall);
        int playerTotal = game.getPlayerArray().length;
        int boardSize = game.getBoard().getSize();
        PawnController[] playerArray = game.getPlayerArray();
        Board board = game.getBoard();

        //game scene ------------------------------------------------------------------
        primaryStage.setTitle("Quoridor - by Virgil Surin & Simon Michel");
        primaryStage.setFullScreen(true);
        Group root = new Group();
        Scene scene = new Scene(root);

        //victory scene ------------------------------------------------------------------
        Group victory = new Group();
        Scene victoryScene = new Scene(victory);
        Button backToMenu = new Button("BACK TO THE MENUUUUUUU");
        backToMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("back to the menu !");
                System.out.println("*dolorean noise*");
                primaryStage.close();
            }
        });
        victory.getChildren().add(backToMenu);

        //game scene ------------------------------------------------------------------
        primaryStage.setScene(scene);
        scene.setFill(Color.BLACK);
        //board drawing
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                ImageView cell = new ImageView();
                cell.setImage(cellImg);
                cell.setY(i * Vspace);
                cell.setX(j * Hspace);
                cell.setEffect(new Glow(0));
                root.getChildren().add(cell);
            }
        }
        //pawn initialization
        for (int i=0; i<playerTotal; i++) {
            root.getChildren().add(new ImageView());
        }
        //display the current state
        updatePawn(boardSize, playerTotal, playerArray, root);
        updateWall(board.getWallList(), root);

        //TURN SYSTEM ------------------------------------------------------------------
        final int[] currentPlayer = {0}; //start with player 0
        //CLICK HANDLING
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //System.out.println(currentPlayer[0]);

                int boardSize = game.getBoard().getSize();
                Coord[] possibleCell = game.whereCanIGo(currentPlayer[0]);
                Coord clickedCell = getCoordFromPos(event.getX(), event.getY());
                PawnController ctrl = playerArray[currentPlayer[0]];
                Coord playerCoord = ctrl.getDependency().getCoord();
                int rightClickCount = 0;

                if (event.getButton().compareTo(MouseButton.PRIMARY) == 0 && clickedCell.getY() < boardSize && clickedCell.getX() < boardSize
                        && ctrl.getType() == "Human") {

                    ImageView clickedCellImage = (ImageView) root.getChildren().get(clickedCell.getX() + boardSize * clickedCell.getY());
                    if (playerCoord.compareTo(clickedCell) == 0) {
                        //click on pawn --> we make the reachable cell glowing
                        for (Coord coord : possibleCell) {
                            root.getChildren().get( coord.getX()+(9*coord.getY()) ).setEffect(colorCell);
                        }
                    } else if (clickedCell.isIn(possibleCell)
                            && clickedCellImage.getEffect().equals(colorCell)) {
                        //if click on a glowing cell (a cell where the player can go), we mote the player to it
                        int deltaY = clickedCell.getY() - playerCoord.getY();
                        int deltaX = clickedCell.getX() - playerCoord.getX();
                        Coord dir = new Coord(deltaY, deltaX);
                        ctrl.move(dir);
                        updatePawn(boardSize, playerTotal, playerArray, root);
                        resetGlowing(root, game);
                        //Check win
                        if (ctrl.hasWon()) {
                            primaryStage.setScene(victoryScene);
                        } else {
                            currentPlayer[0] = (currentPlayer[0] + 1) % playerTotal;
                        }
                    } else {
                        resetGlowing(root, game);
                    }

                //wall placing system
                }
            }
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (playerArray[currentPlayer[0]].getType() != "Human") {
                    Action.getAction(playerArray, playerArray[currentPlayer[0]]);
                    updatePawn(boardSize, playerTotal, playerArray, root);
                    updateWall(board.getWallList(), root);
                    if (playerArray[currentPlayer[0]].hasWon()) {
                        System.out.println("player "+ currentPlayer[0] + " has won !");
                        this.stop();
                        primaryStage.setScene(victoryScene);
                    } else {
                        currentPlayer[0] = (currentPlayer[0] + 1) % playerTotal;
                    }
                }
            }
        }.start();
        primaryStage.show();
    }

    private void resetGlowing(Group root, Game game) {
        for (int i = 0; i < (int) Math.pow(game.getBoard().getSize(), 2); i++) {
            root.getChildren().get(i).setEffect(new Glow(0));
        }
    }

    public void updatePawn(int boardSize, int playerTotal, PawnController[] playerArray, Group root) {
        for (int i=0; i<playerTotal; i++) {
            //We know that all the pawns are in this interval [boardSize², boardSize²+playerTotal[
            ImageView pawn = (ImageView) root.getChildren().get( (boardSize*boardSize)+i );
            Coord playerCoord = playerArray[i].getDependency().getCoord();
            if (pawn.getImage() == null) { //if there is already an image set, it's not necessary to set it again
                if (playerArray[i].getType() == "Human") {
                    pawn.setImage(humanPawnImg); //TODO random choice between Neo/Morpheus/Trinity
                } else {
                    pawn.setImage(AIPawnImg);
                }
            }
            pawn.setX(playerCoord.getX() * Hspace);
            pawn.setY(playerCoord.getY() * Vspace);
        }
    }

    public void updateWall(ArrayList<Coord[]> wallList, Group root) {
        ImageView wall = new ImageView();
        for (Coord[] wallCoord : wallList) {
            if (wallCoord[0].getY() == wallCoord[1].getY()) {
                //horizontal wall
                wall.setImage(wallHImg);
                wall.setX(wallCoord[0].getX() * Hspace - 9);
                wall.setY(wallCoord[0].getY() * Vspace - 18);
            } else {
                //vertical wall
                wall.setImage(wallVImg);
                wall.setX(wallCoord[0].getX() * Hspace + 33);
                wall.setY(wallCoord[0].getY() * Vspace - 54);
            }
        }
        root.getChildren().add(wall);
    }

    /**
     * Given a position in the canvas, will return the coordinates of the sprite the position is pointing to.
     * It's used to make a transition from the GUI to the game logic system.
     *
     * @param x
     * @param y
     * @return a coordinates instance for a Game object to use.
     */
    public static Coord getCoordFromPos(double x, double y) {
        int game_y = (int) y / Vspace;
        int game_x = (int) x / Hspace;
        return new Coord(game_y, game_x);
    }

    /**
     * given an array of coordinates and a mouse click event. It will check if one of the coordinates corresponds
     * to the click position and if one is found, it returns it.
     *
     * @param mouseClick the click event we are looking the position from.
     * @param cellArray  the coordinates we are comparing.
     * @return the corresponding coordinates. Null if iit has not been found.
     */
    public static Coord cellClickIsInArray(MouseEvent mouseClick, Coord[] cellArray) {
        for (Coord coord : cellArray) {
            if (getCoordFromPos(mouseClick.getX(), mouseClick.getY()).compareTo(coord) == 0) {
                return coord;
            }
        }
        return null;
    }

}
