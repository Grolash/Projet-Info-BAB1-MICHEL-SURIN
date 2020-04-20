package tests;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.Observable;


public class fourteenthTest extends Application{

    Stage window;
    Button button;
    ListView<String>    listView;

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

        listView = new ListView<>();
        listView.getItems().addAll("Iron Man", "Thor", "Black Widow", "Captain America");
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        button.setOnAction(event -> buttonClicked());


        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(listView, button);

        Scene scene = new Scene(layout, 400, 300);
        window.setScene(scene);
        window.show();

    }

    private void buttonClicked(){
        String message = "";
        ObservableList<String> movies;
        movies = listView.getSelectionModel().getSelectedItems();
        for(String m: movies){
            message += m + "\n";
        }
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