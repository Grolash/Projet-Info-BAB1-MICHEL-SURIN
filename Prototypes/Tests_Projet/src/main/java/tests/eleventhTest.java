package tests;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.*;


public class eleventhTest extends Application{

    Stage window;
    Button button;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Title");

        window.setOnCloseRequest(e -> {
            e.consume(); //Say to Java "Hey, we're handling this ourselves."
            closeProgram();
        });

        //CheckBox
        CheckBox box1 = new CheckBox("Bacon");
        GridPane.setConstraints(box1, 0, 0);
        box1.setSelected(true);
        CheckBox box2 = new CheckBox("Egg");
        GridPane.setConstraints(box2, 0, 1);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        button = new Button("Order now:");
        button.setOnAction(e -> handleOptions(box1, box2));

        GridPane.setConstraints(button, 1, 2);

        gridPane.getChildren().addAll(button, box1, box2);


        Scene scene = new Scene(gridPane, 400, 300);
        window.setScene(scene);
        window.show();

    }

    private void handleOptions(CheckBox box1, CheckBox box2){
        String message = "Making ";
        if(box1.isSelected()){
            message += "bacon";
        }
        if (box1.isSelected() && box2.isSelected()){
            message += " and ";
        }
        if(box2.isSelected()){
            message += "egg";
        }
        message += " sandwich";

        System.out.println(message);
    }

    private void closeProgram(){
        boolean answer = ConfirmBox.Display("I'm gonna stop you right there!",
                "Are your really sure you want to quit? You might want to save first.");
        if (answer == true) {
            window.close();
        }
    }
}