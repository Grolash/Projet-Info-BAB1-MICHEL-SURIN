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
        String[] type = {"Human", "Human"};
        Game game = new Game(9, type, 10);

        primaryStage.setTitle("Quoridor - by Virgil Surin & Simon Michel");
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(1000,500);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image[][] imagesArray = new Image[game.getBoard().getSize()][game.getBoard().getSize()];
        for (int i=0; i<game.getBoard().getSize(); i++) {
            for (int j=0; j< game.getBoard().getSize(); j++) {
                if (game.getBoard().getCell(getCoordFromSprite(i%38, j%38)).hasPawn()) {
                    gc.drawImage(new Image("pawn.png"), i*38, j*38);
                } else {
                    imagesArray[i][j] = new Image("midTile-shut.png");
                    gc.drawImage(imagesArray[i][j], i * 38, j * 38);
                }
            }
        }
        root.getChildren().add(canvas);
        primaryStage.show();

    }


    public Coord getCoordFromSprite(int y, int x) {
        int new_y = y / 36;
        int new_x = x / 36;
        return new Coord(new_y, new_x);
    }
}
