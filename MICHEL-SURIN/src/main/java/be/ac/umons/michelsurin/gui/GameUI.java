package be.ac.umons.michelsurin.gui;

import be.ac.umons.michelsurin.controller.Action;
import be.ac.umons.michelsurin.controller.PawnController;
import be.ac.umons.michelsurin.engine.Game;
import be.ac.umons.michelsurin.tools.Coord;
import be.ac.umons.michelsurin.world.Board;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
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

import java.util.ArrayList;


public class GameUI {

    private final Image cellImg = new Image("tile.png");
    private final Image humanPawnImg = new Image("neo.png");
    private final Image AIPawnImg = new Image("agent.png");
    private final Image wallHImg = new Image("wallH.png");
    private final Image wallVImg = new Image("wallV.png");
    public final int Hspace = 50;
    public final int Vspace = 50;
    private final ColorAdjust colorCell = new ColorAdjust(0.1, 0, 0.5, 0.5);
    private final Shadow wallShadow = new Shadow(10, Color.RED);

    private Game game;
    private int playerTotal;
    private PawnController[] playerArray;
    private Board board;
    private int boardSize;
    private Group root;
    private Group victoryGroup;
    private Scene gameScene;
    private Scene victoryScene;
    private Scene menuScene;
    private Stage appStage;
    private final int numbOfWall;

    public GameUI(Stage appStage, Scene menuScene, int playerNumber, String[] types){
        this.appStage = appStage;

        this.numbOfWall = 10;
        this.game = new Game(9, types, numbOfWall);
        this.playerArray = game.getPlayerArray();
        this.playerTotal = playerNumber;
        this.board = game.getBoard();
        this.boardSize = board.getSize();

        this.root = new Group();
        this.victoryGroup = new Group();

        this.gameScene = new Scene(root);
        this.victoryScene = new Scene(victoryGroup);
        this.menuScene = menuScene;

        this.appStage.setScene(gameScene);

        //victoryScene --------------------------------------
        Button backToMenu = new Button("BACK TO THE MENUUUUUUU");
        backToMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                appStage.setScene(menuScene);
            }
        });
        victoryGroup.getChildren().add(backToMenu);

        //gameScene --------------------------------------
        gameScene.setFill(Color.BLACK);
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
        updateWall();
        updatePawn();

        //TURN SYSTEM ------------------------------------------------------------------
        final int[] currentPlayer = {0}; //start with player 0
        //CLICK HANDLING
        gameScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //System.out.println(currentPlayer[0]);
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
                        updatePawn();
                        resetGlowing();
                        //Check win
                        if (ctrl.hasWon()) {
                            appStage.setScene(victoryScene);
                        } else {
                            currentPlayer[0] = (currentPlayer[0] + 1) % playerTotal;
                        }
                    } else {
                        resetGlowing();
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
                    updatePawn();
                    updateWall();
                    if (playerArray[currentPlayer[0]].hasWon()) {
                        //System.out.println("player "+ currentPlayer[0] + " has won !");
                        this.stop();
                        appStage.setScene(victoryScene);
                    } else {
                        currentPlayer[0] = (currentPlayer[0] + 1) % playerTotal;
                    }
                }
            }
        }.start();
        appStage.show();

    }

    private void resetGlowing() {
        for (int i = 0; i < (int) Math.pow(game.getBoard().getSize(), 2); i++) {
            root.getChildren().get(i).setEffect(new Glow(0));
        }
    }

    public void updatePawn() {
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

    public void updateWall() {
        ArrayList<Coord[]> wallList = board.getWallList();
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
    public Coord getCoordFromPos(double x, double y) {
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
    public Coord cellClickIsInArray(MouseEvent mouseClick, Coord[] cellArray) {
        for (Coord coord : cellArray) {
            if (getCoordFromPos(mouseClick.getX(), mouseClick.getY()).compareTo(coord) == 0) {
                return coord;
            }
        }
        return null;
    }

}
