package tests;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

public class nineteenthTest extends Application {

    Button button;

    public static void main(String[] args){

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Binding");

        IntegerProperty x = new SimpleIntegerProperty(3);
        IntegerProperty y = new SimpleIntegerProperty();

        y.bind(x.multiply(10));

        button = new Button("Placeholder");
        //setMaxWidth() and textProperty() seem to not exist... ( ._.)
        /*
        //Inputs and labels
        TextField userInput = new TextField();
        userInput.setMaxWidth(200);
        Label firstLabel = new Label("Welcome, ");
        Label secondLabel = new Label();

        HBox bottomText = new HBox(firstLabel, secondLabel);
        bottomText.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(10, userInput, bottomText);
        vBox.setAlignment(Pos.CENTER);

        secondLabel.textProperty().bind(userInput.textProperty());

        Scene scene = new Scene(vBox, 300, 250);
        primaryStage.setScene(scene);
        */
        primaryStage.show();
    }
}
