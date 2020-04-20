package tests;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class ninthTest extends Application{

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

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        Label nameLabel = new Label("Username:");
        GridPane.setConstraints(nameLabel, 0, 0);
        nameLabel.setId("bold-label");
        TextField nameInput = new TextField("John Smith");
        GridPane.setConstraints(nameInput, 1, 0);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);
        TextField passwordInput = new TextField();
        passwordInput.setPromptText("********");
        GridPane.setConstraints(passwordInput, 1, 1);

        Button loginButton = new Button("Log in");
        loginButton.setOnAction(e -> {
            setUserAgentStylesheet(STYLESHEET_CASPIAN);
        });

        GridPane.setConstraints(loginButton, 1, 2);

        //sign up
        Button signupButton = new Button("Sign up");
        gridPane.setConstraints(signupButton, 1, 3);
        signupButton.getStyleClass().add("button-blue");

        gridPane.getChildren().addAll(nameLabel, nameInput, passwordLabel, passwordInput, loginButton, signupButton);


        Scene scene = new Scene(gridPane, 400, 300);
        scene.getStylesheets().add("Viper.css");
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