package be.ac.umons.michelsurin.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Menu extends Application {

    private Scene scene;
    private Button launchButton;
    private Button closeButton;
    private Stage window;
    private VBox layout;

    private ChoiceBox<String> playerNumber;
    private ChoiceBox<String> firstAIDifficultyMenu;
    private ChoiceBox<String> secondAIDifficultyMenu;
    private ChoiceBox<String> thirdAIDifficultyMenu;
    private ChoiceBox<String> fourthAIDifficultyMenu;

    private int playerNumberInt;
    private String firstPlayerType;
    private String secondPlayerType;
    private String thirdPlayerType;
    private String fourthPlayerType;

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

        layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(20);


        launchButton = new Button("Launch game!");
        launchButton.setOnAction(e -> launchGame());
        layout.getChildren().add(launchButton);

        Separator separator = new Separator();
        layout.getChildren().add(separator);

        Label settings = new Label("Settings:");
        layout.getChildren().add(settings);

        //Following lines will set difficulty menus for AI. Listeners will follow in real time selection changes.
        // TODO link listeners values to game values replace sout in lambda function.
        firstAIDifficultyMenu = new ChoiceBox<>();
        firstAIDifficultyMenu.getItems().addAll("Human", "Random AI", "Easy", "Harder");
        firstAIDifficultyMenu.setValue("Human"); //set a default value.
        firstAIDifficultyMenu.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            firstPlayerType = getPlayerType(newValue);
        });

        secondAIDifficultyMenu = new ChoiceBox<>();
        secondAIDifficultyMenu.getItems().addAll("Human", "Random AI", "Easy", "Harder");
        secondAIDifficultyMenu.setValue("Random AI"); //set a default value.
        secondAIDifficultyMenu.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            secondPlayerType = getPlayerType(newValue);
        });

        thirdAIDifficultyMenu = new ChoiceBox<>();
        thirdAIDifficultyMenu.getItems().addAll("Human", "Random AI", "Easy", "Harder");
        thirdAIDifficultyMenu.setValue("Human"); //set a default value.
        thirdAIDifficultyMenu.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            thirdPlayerType = getPlayerType(newValue);
        });

        fourthAIDifficultyMenu = new ChoiceBox<>();
        fourthAIDifficultyMenu.getItems().addAll("Human", "Random AI", "Easy", "Harder");
        fourthAIDifficultyMenu.setValue("Random AI"); //set a default value.
        fourthAIDifficultyMenu.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            fourthPlayerType  = getPlayerType(newValue);
        });

        //Player number menus, will define which difficulty options are enabled.
        playerNumber = new ChoiceBox<>();
        playerNumber.getItems().addAll( "2 Players",
                "4 Players");
        playerNumber.setValue("2 Players"); //set a default value.
        layout.getChildren().add(playerNumber);
        playerNumber.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            if (newValue.equals("2 Players")){
                playerNumberInt = 2;
                Label difficulty = new Label("AI difficulty:");
                layout.getChildren().addAll(firstAIDifficultyMenu, secondAIDifficultyMenu);
            }
            if (newValue.equals("4 Players")){
                playerNumberInt = 4;
                Label difficulty = new Label("AI difficulty:");
                layout.getChildren().addAll(firstAIDifficultyMenu, secondAIDifficultyMenu, thirdAIDifficultyMenu, fourthAIDifficultyMenu);
            }
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

    private String getPlayerType(String string) throws IllegalArgumentException{
        if (string.equals("Human")){
            return string;
        }
        else if(string.equals("Random AI")){
            return "Debilus";
        }
        else if(string.equals("Easy")){
            return "Smarted";
        }
        else if(string.equals("Harder")){
            return "Smart";
        }
        else {
            throw new IllegalArgumentException("Wrong player type!");
        }
    }

    public int getPlayerNumberInt() {
        return playerNumberInt;
    }

    public String getFirstPlayerType() {
        return firstPlayerType;
    }

    public String getSecondPlayerType() {
        return secondPlayerType;
    }

    public String getThirdPlayerType() {
        return thirdPlayerType;
    }

    public String getFourthPlayerType() {
        return fourthPlayerType;
    }

    private void launchGame(){
        boolean answer = ConfirmBox.Display("Launch confirmation",
                "Are you sure you want to launch the game? Be sure you selected the right settings.");
        if (answer){
            // TODO implement game launch!
        }
    }
}
