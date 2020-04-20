package tests;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.*;


public class twelvthTest extends Application{

    Stage window;
    Button button;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("ChoiceBox test");

        window.setOnCloseRequest(e -> {
            e.consume(); //Say to Java "Hey, we're handling this ourselves."
            closeProgram();
        });


        ChoiceBox<String> choiceBox = new ChoiceBox<>(); //Drop-down lists
        //getItems returns the Observablelist object which you can add objects to
        choiceBox.getItems().addAll("Apples", "Bananas");
        choiceBox.setValue("Apples"); //set a default value.

        //Listen for selection changes
        choiceBox.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> System.out.println(newValue));


        button = new Button("Click me");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(choiceBox, button);


        Scene scene = new Scene(layout, 400, 300);
        window.setScene(scene);
        window.show();

    }

    private void closeProgram(){
        boolean answer = ConfirmBox.Display("I'm gonna stop you right there!",
                "Are your really sure you want to quit? You might want to save first.");
        if (answer == true) {
            window.close();
        }
    }
}