package be.ac.umons.michelsurin.gui;

import be.ac.umons.michelsurin.engine.Game;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import be.ac.umons.michelsurin.tools.Coord;
import be.ac.umons.michelsurin.world.Board;
import javafx.util.Duration;

import java.awt.*;


public class GameUI extends Application {

    private Image cellImg = new Image("tile.png");
    private Image playerPawnImg = new Image("neo.png");
    private Image AIPawnImg = new Image("agent.png");
    private Image wallHImg = new Image("wallH.png");
    private Image wallVImg = new Image("wallV.png");
    public static final int HSpace = 34;
    public static final int Vspace = 50;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        String[] type = {"Human", "Debilus"};
        Game game = new Game(9, type, 10);

        //wall set-up for testing
        Coord[] wall1 = new Coord[2];
        wall1[0] = new Coord(2,2);
        wall1[1] = new Coord(2,3);
        game.getBoard().addToWallList(wall1);


        primaryStage.setTitle("Quoridor - by Virgil Surin & Simon Michel");
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.setFill(Color.DARKGREEN);


        ImageView[][] cellView = new ImageView[game.getBoard().getSize()][game.getBoard().getSize()];

        //TODO canvas
        //canvas + GC
        Canvas cellCanvas = new Canvas(500,500);
        cellCanvas.autosize();
        GraphicsContext cellGC = cellCanvas.getGraphicsContext2D();

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
            }
        }
        updatePawn(pawnCanvas, pawnGC, game);
        updateWall(wallCanvas, wallGC, game);
        adjacentCellHighlight(0, game, scene, cellCanvas);


        root.getChildren().add(cellView[0][1]);
        root.getChildren().add(wallCanvas);
        root.getChildren().add(pawnCanvas);
        primaryStage.show();
    }

    /**
     * This method make the GUI sleeping for a given duration.
     * Source : https://stackoverflow.com/questions/49881109/how-to-properly-execute-thread-sleep-in-javafx
     *
     * @param millis the sleeping time in millisecond.
     */
    public static void sleep(int millis, Scene scene) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(millis), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        }));
        timeline.play();
    }

    public void updatePawn(Canvas canvas, GraphicsContext gc, Game game) {
        //take the game state
        Board board = game.getBoard();
        gc.clearRect(0,0, canvas.getHeight(), canvas.getWidth());
        for (int i=0; i<game.getPlayerArray().length; i++) {
            Coord coord = game.getPlayerArray()[i].getDependency().getCoord();
            if (game.getPlayerArray()[i].getType() == "Human") {
                gc.drawImage(playerPawnImg, coord.getX()*HSpace, coord.getY()*Vspace);
            } else {
                gc.drawImage(AIPawnImg, coord.getX()*HSpace, coord.getY()*Vspace);
            }
        }
    }
    public void updateWall(Canvas canvas, GraphicsContext gc, Game game) {
        Board board = game.getBoard();
        gc.clearRect(0,0, canvas.getHeight(), canvas.getWidth());
        for (Coord[] wall : board.getWallList()) {
            gc.drawImage(wallHImg, wall[0].getX()*HSpace, wall[0].getY()*Vspace);
            gc.drawImage(wallHImg, wall[1].getX()*HSpace, wall[1].getY()*Vspace);
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
        int game_x = (int) x / HSpace;
        return new Coord(game_y,game_x);
    }

    public void adjacentCellHighlight(int player, Game game, Scene scene, Canvas cellCanvas) {
        //the player must be human
        Coord playerCoord = game.getPlayerArray()[player].getDependency().getCoord();
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().compareTo(MouseButton.PRIMARY) == 0 &&
                        playerCoord.compareTo(getCoordFromPos(event.getX(), event.getY())) == 0 ) {
                    Coord[] possibleCell = game.whereCanIGo(player);
                    for (Coord coord : possibleCell) {
                        cellCanvas.getGraphicsContext2D().setEffect(new Glow(0.9));
                        System.out.println(coord);
                        }
                    }
                }
        });

    }

}
