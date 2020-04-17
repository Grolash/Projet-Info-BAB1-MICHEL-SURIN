package gui;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.LoadListener;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import tools.Coord;
import world.Board;
import world.Cell;


public class GameUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Quoridor - by Virgil Surin & Simon Michel");
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        Coord[] pawnCoord = {new Coord(0,4), new Coord(8,4)};
        Board board = new Board(9,pawnCoord);
        Canvas canvas = new Canvas(500,500);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image cellImg = new Image("midTile-shut-Corridor.png");

        gc.drawImage(cellImg, 40,0);
        gc.drawImage(cellImg, 0,0);




        root.getChildren().add(canvas);
        primaryStage.show();

    }
}
