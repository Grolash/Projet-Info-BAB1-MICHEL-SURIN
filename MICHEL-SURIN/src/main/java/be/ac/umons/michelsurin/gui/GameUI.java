package be.ac.umons.michelsurin.gui;

import be.ac.umons.michelsurin.engine.Game;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import be.ac.umons.michelsurin.tools.Coord;
import be.ac.umons.michelsurin.world.Board;

import java.awt.*;


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

        CellUI[][] cellArray = new CellUI[game.getBoard().getSize()][game.getBoard().getSize()];
        for (int i=0; i<game.getBoard().getSize(); i++) {
            for (int j=0; j< game.getBoard().getSize(); j++) {
                if (game.getBoard().getCell(getCoordFromSprite(i%34, j%34)).hasPawn()) {
                    gc.drawImage(new Image("pawn.png"), i*34, j*34);
                } else {
                    cellArray[i][j] = new CellUI(new Image("tile.png"), new Coord(i,j));
                    gc.drawImage(cellArray[i][j].getImg(), j * 34, i * 34);
                }
            }
        }

        PawnUI pawnUI = new PawnUI(new Image("pawn.png"), game.getBoard().getPawnCoord()[0]);
        gc.drawImage(pawnUI.getImg(), pawnUI.getCoord().getX()*34, pawnUI.getCoord().getY()*34);

        game.getPlayerArray()[0].move(Game.directions.get("DOWN"));
        update(gc, game);
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                gc.clearRect(event.getX() -2, event.getY() -2, 5,5);
            }
        });

        root.getChildren().add(canvas);
        primaryStage.show();
    }

    public void update(GraphicsContext gc, Game game) {
        //take the game state
        PawnUI pawnUI = new PawnUI(new Image("pawn.png"), game.getBoard().getPawnCoord()[0]);
        gc.drawImage(pawnUI.getImg(), pawnUI.getCoord().getX()*34, pawnUI.getCoord().getY()*34 );
    }


    public Coord getCoordFromSprite(int y, int x) {
        int new_y = y / 34;
        int new_x = x / 34;
        return new Coord(new_y, new_x);
    }
}
