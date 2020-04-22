package be.ac.umons.michelsurin.gui;

import be.ac.umons.michelsurin.controller.Action;
import be.ac.umons.michelsurin.controller.PawnController;
import be.ac.umons.michelsurin.engine.Game;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import be.ac.umons.michelsurin.tools.Coord;
import be.ac.umons.michelsurin.world.Board;
import javafx.util.Duration;
import java.lang.Math;
import java.security.cert.CertificateParsingException;


public class GameUI extends Application {

    private Image cellImg = new Image("tile.png");
    private Image playerPawnImg = new Image("neo.png");
    private Image AIPawnImg = new Image("agent.png");
    private Image wallHImg = new Image("wallH.png");
    private Image wallVImg = new Image("wallV.png");
    public static final int Hspace = 50;
    public static final int Vspace = 50;
    private static Glow glowingLevel = new Glow(0.9);


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        String[] type = {"Debilus", "Debilus"};
        Game game = new Game(9, type, 10);

        //wall set-up for testing
        /*
        Coord[] wall1 = new Coord[2];
        wall1[0] = new Coord(2,2);
        wall1[1] = new Coord(2,3);
        game.getBoard().addToWallList(wall1);
         */
        int currentPlayer = 0;
        int playerTotal = game.getPlayerArray().length;

        primaryStage.setTitle("Quoridor - by Virgil Surin & Simon Michel");
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.setFill(Color.BLACK);


        ImageView[][] cellView = new ImageView[game.getBoard().getSize()][game.getBoard().getSize()];

        //TODO canvas
        Canvas pawnCanvas = new Canvas(500,500);
        GraphicsContext pawnGC = pawnCanvas.getGraphicsContext2D();

        Canvas wallCanvas = new Canvas(500, 500);
        GraphicsContext wallGC = wallCanvas.getGraphicsContext2D();

        //board drawing
        for (int i=0; i<game.getBoard().getSize(); i++) {
            for (int j=0; j<game.getBoard().getSize(); j++){
                //cellGC.drawImage(cellImg, j*HSpace, i*Vspace);
                cellView[i][j] = new ImageView();
                cellView[i][j].setImage(new Image("tile.png"));
                cellView[i][j].setY(i*Vspace);
                cellView[i][j].setX(j*Hspace);
                cellView[i][j].setEffect(new Glow(0));

                /*
                ImageView imageView = new ImageView();
                imageView.setImage(cellImg);
                imageView.setY(i*Vspace);
                imageView.setX(j*Hspace);
                 */
                root.getChildren().add(cellView[i][j]);
            }
        }
        updatePawn(pawnCanvas, pawnGC, game);
        updateWall(wallCanvas, wallGC, game);



        //CLICK HANDLING ---------------------------------------------------------

        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                PawnController ctrl = game.getPlayerArray()[0];
                int boardSize = game.getBoard().getSize();

                Coord playerCoord = game.getPlayerArray()[0].getDependency().getCoord();
                Coord[] possibleCell = game.whereCanIGo(0);
                Coord clickedCell = getCoordFromPos(event.getX(), event.getY());

                if (event.getButton().compareTo(MouseButton.PRIMARY) == 0 && clickedCell.getY() < boardSize && clickedCell.getX() < boardSize) {
                    ImageView clickedCellImage = (ImageView) root.getChildren().get(clickedCell.getX()+ boardSize*clickedCell.getY());
                    if (playerCoord.compareTo(clickedCell) == 0) {
                        //click on pawn --> we make the reachable cell glowing
                        for (Coord coord : possibleCell) {
                            root.getChildren().get(coord.getX()+ (9*coord.getY())).setEffect(glowingLevel);
                        }
                    } else if ( clickedCell.isIn(possibleCell)
                                && clickedCellImage.getEffect().equals(glowingLevel)) {
                        //if click on a glowing cell (a cell where the player can go), we mote the player to it
                        int deltaY = clickedCell.getY() - playerCoord.getY();
                        int deltaX = clickedCell.getX() - playerCoord.getX();
                        Coord dir = new Coord(deltaY, deltaX);
                        ctrl.move(dir);
                        updatePawn(pawnCanvas, pawnCanvas.getGraphicsContext2D(), game);
                        resetGlowing(root, game);
                    } else {
                        resetGlowing(root, game);
                    }
                }
            }
        });


        //root.getChildren().add(cellView[0][1]);
        root.getChildren().add(wallCanvas);
        root.getChildren().add(pawnCanvas);
        primaryStage.show();
        action(currentPlayer, playerTotal, game, wallCanvas, pawnCanvas, primaryStage, root);
    }
    /*
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                }
            }));
            timeline.play();
     */

    private void resetGlowing(Group root, Game game) {
        for (int i=0; i< (int) Math.pow(game.getBoard().getSize(), 2); i++) {
            root.getChildren().get(i).setEffect(new Glow(0));
        }
    }
    public void updatePawn(Canvas canvas, GraphicsContext gc, Game game) {
        //take the game state
        Board board = game.getBoard();
        gc.clearRect(0,0, canvas.getHeight(), canvas.getWidth());
        for (int i=0; i<game.getPlayerArray().length; i++) {
            Coord coord = game.getPlayerArray()[i].getDependency().getCoord();
            if (game.getPlayerArray()[i].getType() == "Human") {
                gc.drawImage(playerPawnImg, coord.getX()* Hspace, coord.getY()*Vspace);
            } else {
                gc.drawImage(AIPawnImg, coord.getX()* Hspace, coord.getY()*Vspace);
            }
        }
    }
    public void updateWall(Canvas canvas, GraphicsContext gc, Game game) {
        Board board = game.getBoard();
        gc.clearRect(0,0, canvas.getHeight(), canvas.getWidth());
        for (Coord[] wall : board.getWallList()) {
            gc.drawImage(wallHImg, wall[0].getX()* Hspace, wall[0].getY()*Vspace);
            gc.drawImage(wallHImg, wall[1].getX()* Hspace, wall[1].getY()*Vspace);
        }
    }
    /**
     * Given a position in the canvas, will return the coordinates of the sprite the position is pointing to.
     * It's used to make a transition from the GUI to the game logic system.
     * @param x
     * @param y
     * @return a coordinates instance for a Game object to use.
     */
    public static Coord getCoordFromPos(double x, double y) {
        int game_y = (int) y / Vspace;
        int game_x = (int) x / Hspace;
        return new Coord(game_y,game_x);
    }
    /**
     * given an array of coordinates and a mouse click event. It will check if one of the coordinates corresponds
     * to the click position and if one is found, it returns it.
     *
     * @param mouseClick the click event we are looking the position from.
     * @param cellArray the coordinates we are comparing.
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

    private void action(int currentPlayer, int playerTotal, Game game, Canvas wallCanvas, Canvas pawnCanvas, Stage stage, Group root) {
        PawnController[] playerArray = game.getPlayerArray();
        if (playerArray[currentPlayer].getType() == "Human") {
            //Human action handling
            root.getOnMouseClicked();
            stage.getScene().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    PawnController ctrl = game.getPlayerArray()[0];
                    int boardSize = game.getBoard().getSize();

                    Coord playerCoord = game.getPlayerArray()[0].getDependency().getCoord();
                    Coord[] possibleCell = game.whereCanIGo(0);
                    Coord clickedCell = getCoordFromPos(event.getX(), event.getY());

                    if (event.getButton().compareTo(MouseButton.PRIMARY) == 0 && clickedCell.getY() < boardSize && clickedCell.getX() < boardSize) {
                        ImageView clickedCellImage = (ImageView) root.getChildren().get(clickedCell.getX()+ boardSize*clickedCell.getY());
                        if (playerCoord.compareTo(clickedCell) == 0) {
                            //click on pawn --> we make the reachable cell glowing
                            for (Coord coord : possibleCell) {
                                root.getChildren().get(coord.getX()+ (9*coord.getY())).setEffect(glowingLevel);
                            }
                        } else if ( clickedCell.isIn(possibleCell)
                                && clickedCellImage.getEffect().equals(glowingLevel)) {
                            //if click on a glowing cell (a cell where the player can go), we mote the player to it
                            int deltaY = clickedCell.getY() - playerCoord.getY();
                            int deltaX = clickedCell.getX() - playerCoord.getX();
                            Coord dir = new Coord(deltaY, deltaX);
                            ctrl.move(dir);
                            updatePawn(pawnCanvas, pawnCanvas.getGraphicsContext2D(), game);
                            resetGlowing(root, game);
                        } else {
                            resetGlowing(root, game);
                        }
                    }
                }
            });
        } else {
            //AI action handling
            int finalCurrentPlayer = currentPlayer;
            Action.getAction(playerArray, playerArray[finalCurrentPlayer]);
        }
        //no matter what we update the pawns and walls.
        updatePawn(pawnCanvas, pawnCanvas.getGraphicsContext2D(), game);
        updateWall(wallCanvas, wallCanvas.getGraphicsContext2D(), game);
        if (playerArray[currentPlayer].hasWon()) {
            //code to end the game
            System.out.println("YEAH ! Player " + currentPlayer + " has won !");
            System.out.println(game.getBoard().getWallList().size());
            for (Coord[] wall: game.getBoard().getWallList()) {
                System.out.println(wall[0] + " | " + wall[1]);
            }
            //stage.close();
        } else {
            //we ask for an other turn
            currentPlayer = (currentPlayer + 1)% playerTotal; //keeps the current player into bounds
            action(currentPlayer, playerTotal, game, wallCanvas, pawnCanvas, stage, root);
        }
    }



}
