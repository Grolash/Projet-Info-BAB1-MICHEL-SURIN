package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Menu extends Application {

    private Scene scene;
    private Button launchButton;
    private Button closeButton;
    private Stage window;

    private ChoiceBox<String> playerNumber;
    private ChoiceBox<String> firstAIDifficultyMenu;
    private ChoiceBox<String> secondAIDifficultyMenu;
    private ChoiceBox<String> thirdAIDifficultyMenu;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        window = new Stage();

        window.setOnCloseRequest(e -> {
            e.consume(); //Say to Java "Hey, we're handling this ourselves."
            closeProgram();
        });

        closeButton = new Button("Close the game");
        closeButton.setOnAction(e -> closeProgram());

        window.setTitle("Main Menu");
        window.setMinWidth(600);
        window.setMinHeight(600);

        launchButton = new Button("Launch game!");
        launchButton.setOnAction(e -> launchGame());

        Label settings = new Label("Settings:");

        //Following lines will set difficulty menus for AI. Listeners will follow in real time selection changes.
        // TODO link listeners values to game values replace sout in lambda function.
        firstAIDifficultyMenu = new ChoiceBox<>();
        firstAIDifficultyMenu.getItems().addAll("Random AI", "Easy", "Harder");
        firstAIDifficultyMenu.setValue("Random AI"); //set a default value.
        firstAIDifficultyMenu.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> System.out.println(newValue));

        secondAIDifficultyMenu = new ChoiceBox<>();
        secondAIDifficultyMenu.getItems().addAll("Random AI", "Easy", "Harder");
        secondAIDifficultyMenu.setValue("Random AI"); //set a default value.
        secondAIDifficultyMenu.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> System.out.println(newValue));

        thirdAIDifficultyMenu = new ChoiceBox<>();
        thirdAIDifficultyMenu.getItems().addAll("Random AI", "Easy", "Harder");
        thirdAIDifficultyMenu.setValue("Random AI"); //set a default value.
        thirdAIDifficultyMenu.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> System.out.println(newValue));

        //Player number menus, will define which difficulty options are enabled.
        playerNumber = new ChoiceBox<>();
        playerNumber.getItems().addAll( "2 Players", "2 Players (1 AI)",
                "4 Players", "4 Players (1 AI)", "4 Players (2 AI)", "4 Players (3 AI)");
        playerNumber.setValue("2 Players (1 AI)"); //set a default value.
        playerNumber.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            if (newValue.equals("2 Players (1 AI)")){
                Label difficulty = new Label("AI difficulty:");
                // TODO set firstAIDifficultyMenu to layout
            }
            else if(newValue.equals("4 Players (1 AI)")){
                Label difficulty = new Label("AI difficulty:");
                // TODO set firstAIDifficultyMenu to layout
            }
            else if (newValue.equals("4 Players (2 AI)")){
                Label firstDifficulty = new Label("First AI difficulty:");
                Label secondDifficulty = new Label("Second AI difficulty:");
                // TODO set both difficulty menus to layout
            }
            else if (newValue.equals("4 Players (3 AI)")){
                Label firstDifficulty = new Label("First AI difficulty:");
                Label secondDifficulty = new Label("Second AI difficulty:");
                Label thirdDifficulty = new Label("Third AI difficulty:");
                // TODO set all difficulty menus to layout
            }
            // TODO set human players
        });




        window.show();


    }

    private void closeProgram(){
        boolean answer = ConfirmBox.Display("Quit confirmation",
                "Are your really sure you want to quit? You might want to save first.");
        if (answer) {
            window.close();
        }
    }

    private void launchGame(){
        boolean answer = ConfirmBox.Display("Launch confirmation",
                "Are you sure you want to launch the game? Be sure you selected the right settings.");
        if (answer){
            // TODO implement game launch!
        }
    }
}
