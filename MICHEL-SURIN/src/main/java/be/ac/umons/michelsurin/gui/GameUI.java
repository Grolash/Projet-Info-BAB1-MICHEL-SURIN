package be.ac.umons.michelsurin.gui;

import be.ac.umons.michelsurin.engine.Game;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import be.ac.umons.michelsurin.tools.Coord;
import be.ac.umons.michelsurin.world.Board;


public class GameUI extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Game game = new Game(9,2);

        primaryStage.setTitle("Quoridor - by Virgil Surin & Simon Michel");
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(1000,1000);
        Image cellImg = new Image("midTile-shut-Corridor.png");
        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (int i=0; i<game.getBoard().getSize(); i++) {
            for (int j=0; j< game.getBoard().getSize(); j++) {
                gc.drawImage(cellImg, i*50, j*50);
            }
        }


        root.getChildren().add(canvas);
        primaryStage.show();

    }
}
