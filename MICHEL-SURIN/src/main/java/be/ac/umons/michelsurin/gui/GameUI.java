package be.ac.umons.michelsurin.gui;

import be.ac.umons.michelsurin.controller.Action;
import be.ac.umons.michelsurin.controller.PawnController;
import be.ac.umons.michelsurin.engine.Game;
import be.ac.umons.michelsurin.engine.Rules;
import be.ac.umons.michelsurin.tools.Coord;
import be.ac.umons.michelsurin.world.Board;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;


public class GameUI extends Application {

    private Image cellImg = new Image("tile.png");
    private Image humanPawnImg = new Image("neo.png");
    private Image AIPawnImg = new Image("agent.png");
    private Image wallHImg = new Image("wallH.png");
    private Image wallVImg = new Image("wallV.png");
    public static final int Hspace = 50;
    public static final int Vspace = 50;
    private static Glow glowingLevel = new Glow(0.9);
    private static Shadow wallShadow = new Shadow(10, Color.RED);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        String[] type = {"Human", "Smarted"};
        int numbOfWall = 10;
        Game game = new Game(9, type, numbOfWall);

        int playerTotal = game.getPlayerArray().length;
        int boardSize = game.getBoard().getSize();
        PawnController[] playerArray = game.getPlayerArray();
        Board board = game.getBoard();

        primaryStage.setTitle("Quoridor - by Virgil Surin & Simon Michel");
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.setFill(Color.BLACK);


        ImageView[][] cellView = new ImageView[boardSize][boardSize];

        //TODO canvas
        Canvas wallCanvas = new Canvas(500, 500);
        GraphicsContext wallGC = wallCanvas.getGraphicsContext2D();


        //board drawing
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                cellView[i][j] = new ImageView();
                cellView[i][j].setImage(cellImg);
                cellView[i][j].setY(i * Vspace);
                cellView[i][j].setX(j * Hspace);
                cellView[i][j].setEffect(new Glow(0));

                root.getChildren().add(cellView[i][j]);

                /*
                ImageView imageView = new ImageView();
                imageView.setImage(cellImg);
                imageView.setY(i*Vspace);
                imageView.setX(j*Hspace);
                root.getChildren().add(cellView[i][j]);
                 */
            }
        }
        //pawn initialization
        for (int i=0; i<playerTotal; i++) {
            root.getChildren().add(new ImageView());
        }
        //display the current state
        updatePawn(boardSize, playerTotal, playerArray, root);
        updateWall(board.getWallList(), root);

        //TURN SYSTEM
        //(valeur du joueur + 1)%playerTotal
        final int[] currentPlayer = {0}; //start with player 0

        //CLICK HANDLING ---------------------------------------------------------
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
                    /*
                    ImageView lastChild = (ImageView) root.getChildren().get(root.getChildren().size()-1);
                    if (rightClickCount > 0 && lastChild.getEffect() == wallShadow) {
                        root.getChildren().remove(root.getChildren().size());
                        Coord[] wall = new Coord[2];
                        wall[0] = clickedCell;
                        if(lastChild.getImage() == wallHImg) {
                            wall[1] = Coord.add(clickedCell, Game.directions.get("RIGHT"));
                        } else {
                            wall[1] = Coord.add(clickedCell, Game.directions.get("UP"));
                        }
                        game.getBoard().addToWallList(wall);
                        updateWall(wallCanvas, wallGC, game);
                    } */

                    ImageView clickedCellImage = (ImageView) root.getChildren().get(clickedCell.getX() + boardSize * clickedCell.getY());
                    if (playerCoord.compareTo(clickedCell) == 0) {
                        //click on pawn --> we make the reachable cell glowing
                        for (Coord coord : possibleCell) {
                            root.getChildren().get(coord.getX() + (9 * coord.getY())).setEffect(glowingLevel);
                        }
                    } else if (clickedCell.isIn(possibleCell)
                            && clickedCellImage.getEffect().equals(glowingLevel)) {
                        //if click on a glowing cell (a cell where the player can go), we mote the player to it
                        int deltaY = clickedCell.getY() - playerCoord.getY();
                        int deltaX = clickedCell.getX() - playerCoord.getX();
                        Coord dir = new Coord(deltaY, deltaX);
                        ctrl.move(dir);
                        updatePawn(boardSize, playerTotal, playerArray, root);
                        resetGlowing(root, game);
                        currentPlayer[0] = (currentPlayer[0] + 1) % playerTotal;
                    } else {
                        resetGlowing(root, game);
                    }
                //wall placing system
                    //first righclick on a cell --> highlight H wall (V wall if H wall not possible)
                } else if (event.getButton().compareTo(MouseButton.SECONDARY) == 0 && clickedCell.getY() < boardSize
                        && clickedCell.getY() != 0 && clickedCell.getX() < boardSize - 1
                        && (root.getChildren().get(root.getChildren().size()-1).getEffect() != wallShadow) ) {
                    //wall highlight
                    if (Rules.canPlaceWall(playerArray, ctrl, clickedCell, Game.directions.get("RIGHT"))) {
                        //H wall
                        ImageView wall = new ImageView(wallHImg);
                        wall.setX(clickedCell.getX() * Hspace - 9);
                        wall.setY(clickedCell.getY() * Vspace - 18);
                        wall.setEffect(wallShadow);
                        root.getChildren().add(wall);
                        rightClickCount += 1;
                    } else if (Rules.canPlaceWall(playerArray, ctrl, clickedCell, Game.directions.get("UP"))) {
                        //V wall
                        ImageView wall = new ImageView(wallHImg);
                        wall.setX(clickedCell.getX() * Hspace - 9);
                        wall.setY(clickedCell.getY() * Vspace - 18);
                        wall.setEffect(wallShadow);
                        root.getChildren().add(wall);
                        rightClickCount += 1;
                    }
                    //next right click on a cell WHERE A WALL IS ALREADY HIGHLIGHT --> switch direction (H -> V / V -> H)
                    //if possible
                } else if (event.getButton().compareTo(MouseButton.SECONDARY) == 0 && clickedCell.getY() < boardSize
                        && clickedCell.getY() != 0 && clickedCell.getX() < boardSize - 1) {
                    ImageView lastChild = (ImageView) root.getChildren().get(root.getChildren().size()-1);
                    Coord tempCoord = Coord.add(getCoordFromPos(lastChild.getX(), lastChild.getY()), new Coord(1,1));
                    //System.out.println(tempCoord);
                    if ( tempCoord.compareTo(clickedCell) == 0){
                        //we click on the highlighted wall cell
                        if (lastChild.getImage() == wallHImg) {
                            lastChild.setImage(wallVImg);
                            lastChild.setX(clickedCell.getX() * Hspace +32);
                            lastChild.setY(clickedCell.getY() * Vspace - 18);
                        } else if (lastChild.getImage() == wallVImg) {
                            lastChild.setImage(wallHImg);
                            lastChild.setX(clickedCell.getX() * Hspace - 9);
                            lastChild.setY(clickedCell.getY() * Vspace - 18);
                            System.out.println(tempCoord);
                        }
                        rightClickCount += 1;
                    } else {
                        //we clicked somewhere else --> delete the highlight
                        root.getChildren().remove(root.getChildren().size()-1);
                    }
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
                    for (Coord[] wall: game.getBoard().getWallList()) {
                        //System.out.println(wall[0]+" | "+wall[1]);
                    }
                    currentPlayer[0] = (currentPlayer[0] + 1) % playerTotal;
                }
            }
        }.start();
        /*
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (playerArray[currentPlayer[0]].getType() != "Human") {
                    Action.getAction(playerArray, playerArray[currentPlayer[0]]);
                    updatePawn(pawnCanvas, pawnGC, game);
                    updateWall(wallCanvas, wallGC, game);

                    currentPlayer[0] = (currentPlayer[0] +1) % playerTotal;
                }
            }
        }));
        timeline.play();
         */


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
