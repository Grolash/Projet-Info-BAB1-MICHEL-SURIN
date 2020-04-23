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

        layout.getChildren().add(closeButton);

        Separator separator = new Separator();
        layout.getChildren().add(separator);

        Label settings = new Label("Settings:");
        layout.getChildren().add(settings);

        //Following lines will set difficulty menus for AI. Listeners will follow in real time selection changes.
        firstAIDifficultyMenu = new ChoiceBox<>();
        firstAIDifficultyMenu.getItems().addAll("Human", "Random AI", "Easy", "Harder");
        firstAIDifficultyMenu.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            firstPlayerType = getPlayerType(newValue);
        });

        secondAIDifficultyMenu = new ChoiceBox<>();
        secondAIDifficultyMenu.getItems().addAll("Human", "Random AI", "Easy", "Harder");
        secondAIDifficultyMenu.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            secondPlayerType = getPlayerType(newValue);
        });

        thirdAIDifficultyMenu = new ChoiceBox<>();
        thirdAIDifficultyMenu.getItems().addAll("Human", "Random AI", "Easy", "Harder");
        thirdAIDifficultyMenu.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            thirdPlayerType = getPlayerType(newValue);
        });

        fourthAIDifficultyMenu = new ChoiceBox<>();
        fourthAIDifficultyMenu.getItems().addAll("Human", "Random AI", "Easy", "Harder");
        fourthAIDifficultyMenu.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            fourthPlayerType  = getPlayerType(newValue);
        });

        Label players = new Label("Players:");
        layout.getChildren().add(players);
        //Player number menus, will define which difficulty options are enabled.
        playerNumber = new ChoiceBox<>();
        playerNumber.getItems().addAll( "2 Players",
                "4 Players");
        layout.getChildren().add(playerNumber);
        Label difficulty = new Label("AI difficulty:");
        layout.getChildren().add(difficulty);
        playerNumber.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            if (newValue.equals("2 Players")){
                playerNumberInt = 2;
                layout.getChildren().remove(firstAIDifficultyMenu);
                layout.getChildren().remove(secondAIDifficultyMenu);
                layout.getChildren().remove(thirdAIDifficultyMenu);
                layout.getChildren().remove(fourthAIDifficultyMenu);
                layout.getChildren().addAll(firstAIDifficultyMenu, secondAIDifficultyMenu);
            }
            else if (newValue.equals("4 Players")){
                playerNumberInt = 4;
                layout.getChildren().remove(firstAIDifficultyMenu);
                layout.getChildren().remove(secondAIDifficultyMenu);
                layout.getChildren().addAll(firstAIDifficultyMenu, secondAIDifficultyMenu, thirdAIDifficultyMenu, fourthAIDifficultyMenu);
            }
        });

        scene = new Scene(layout);
        window.setScene(scene);
        window.show();


    }

    private void closeProgram(){
        boolean answer = ConfirmBox.Display("Quit confirmation",
                "Are your really sure you want to quit? You might want to save first.");
        if (answer) {
            window.close();
        }
    }

    /**
     * Convert string to valid player type.
     * @param string
     * @return
     * @throws IllegalArgumentException
     */
    private String getPlayerType(String string) throws IllegalArgumentException{
        switch (string) {
            case "Human":
                return string;
            case "Random AI":
                return "Debilus";
            case "Easy":
                return "Smarted";
            case "Harder":
                return "Smart";
            default:
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
