package tests;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.*;


public class thirteenthTest extends Application{

    Stage window;
    Button button;
    ComboBox<String> comboBox;

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
        button = new Button("Click me");


        comboBox = new ComboBox<>();
        comboBox.getItems().addAll(
                "Alien VS Predator", "Predator", "Alien"
        );
        comboBox.setPromptText("Select your favorite movie");
        button.setOnAction(e -> printMovie());

        comboBox.setOnAction(e -> System.out.println("User selected " + comboBox.getValue()));

        comboBox.setEditable(true);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(comboBox, button);

        Scene scene = new Scene(layout, 400, 300);
        window.setScene(scene);
        window.show();

    }

    private void printMovie(){
        System.out.println(comboBox.getValue());
    }

    private void closeProgram(){
        boolean answer = ConfirmBox.Display("I'm gonna stop you right there!",
                "Are your really sure you want to quit? You might want to save first.");
        if (answer == true) {
            window.close();
        }
    }
}