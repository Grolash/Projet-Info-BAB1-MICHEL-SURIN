package be.ac.umons.michelsurin.gui;

import be.ac.umons.michelsurin.engine.Game;
import be.ac.umons.michelsurin.engine.SaverLoader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Menu extends Application {


    private Scene scene;
    private Button launchButton;
    private Button closeButton;
    private Stage window;
    private VBox layout;

    /**
     * Load a game.
     */
    private Button loadButton;

    /**
     * Save and load games.
     */
    private SaverLoader saverLoader;

    private ChoiceBox<String> playerNumber;
    private ChoiceBox<String> firstAIDifficultyMenu;
    private ChoiceBox<String> secondAIDifficultyMenu;
    private ChoiceBox<String> thirdAIDifficultyMenu;
    private ChoiceBox<String> fourthAIDifficultyMenu;
    private ChoiceBox<String> wallOptions;

    private int playerNumberInt;
    private String firstPlayerType;
    private String secondPlayerType;
    private String thirdPlayerType;
    private String fourthPlayerType;
    private int wallNumber;

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
        window = primaryStage;
        window.setOnCloseRequest(e -> {
            e.consume(); //Say to Java : "Hey, we're handling this ourselves."
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
        layout.setBackground(Background.EMPTY);


        launchButton = new Button("Launch game!");
        launchButton.setOnAction(e -> launchGame(window));
        layout.getChildren().add(launchButton);

        loadButton = new Button("Load Game");
        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Game game = SaverLoader.load();
                    GameUI gameUI = new GameUI(window, scene, game);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        layout.getChildren().add(loadButton);

        layout.getChildren().add(closeButton);

        Separator separator = new Separator();
        layout.getChildren().add(separator);


        Label settings = new Label("Settings:");
        layout.getChildren().add(settings);

        //Following lines will set difficulty menus for AI. Listeners will follow in real time selection changes.
        firstAIDifficultyMenu = new ChoiceBox<>();
        firstAIDifficultyMenu.getItems().addAll("Human", "Random AI", "Easy", "Harder");
        firstAIDifficultyMenu.setValue("Human");
        firstPlayerType = getPlayerType(firstAIDifficultyMenu.getValue());
        firstAIDifficultyMenu.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            firstPlayerType = getPlayerType(newValue);
        });

        secondAIDifficultyMenu = new ChoiceBox<>();
        secondAIDifficultyMenu.getItems().addAll("Human", "Random AI", "Easy", "Harder");
        secondAIDifficultyMenu.setValue("Easy");
        secondPlayerType = getPlayerType(secondAIDifficultyMenu.getValue());
        secondAIDifficultyMenu.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            secondPlayerType = getPlayerType(newValue);
        });

        thirdAIDifficultyMenu = new ChoiceBox<>();
        thirdAIDifficultyMenu.getItems().addAll("Human", "Random AI", "Easy", "Harder");
        thirdAIDifficultyMenu.setValue("Easy");
        thirdPlayerType = getPlayerType(thirdAIDifficultyMenu.getValue());
        thirdAIDifficultyMenu.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            thirdPlayerType = getPlayerType(newValue);
        });

        fourthAIDifficultyMenu = new ChoiceBox<>();
        fourthAIDifficultyMenu.getItems().addAll("Human", "Random AI", "Easy", "Harder");
        fourthAIDifficultyMenu.setValue("Easy");
        fourthPlayerType = getPlayerType(fourthAIDifficultyMenu.getValue());
        fourthAIDifficultyMenu.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            fourthPlayerType  = getPlayerType(newValue);
        });

        Label walls = new Label("Walls:");
        layout.getChildren().add(walls);
        //Number of walls menu:
        wallOptions = new ChoiceBox<>();
        wallOptions.getItems().addAll("5 walls", "10 walls");
        wallOptions.setValue("10 walls");
        wallNumber = 10;
        wallOptions.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            switch (newValue){
                case "5 walls":
                    wallNumber = 5;
                    break;
                case "10 walls":
                    wallNumber =10;
                    break;
                default:
                    throw new IllegalArgumentException("Uh oh... something went wrong with wall numbers in the menu...");
            }
        });
        layout.getChildren().add(wallOptions);


        Label players = new Label("Players:");
        layout.getChildren().add(players);
        //Player number menus, will define which difficulty options are enabled.
        playerNumber = new ChoiceBox<>();
        playerNumber.getItems().addAll( "2 Players",
                "4 Players");
        playerNumber.setValue("2 Players");
        playerNumberInt = 2;
        layout.getChildren().add(playerNumber);
        Label difficulty = new Label("AI difficulty:");
        layout.getChildren().add(difficulty);
        //default state
        layout.getChildren().remove(firstAIDifficultyMenu);
        layout.getChildren().remove(secondAIDifficultyMenu);
        layout.getChildren().remove(thirdAIDifficultyMenu);
        layout.getChildren().remove(fourthAIDifficultyMenu);
        layout.getChildren().addAll(firstAIDifficultyMenu, secondAIDifficultyMenu);
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
        layout.setMinSize(600, 600);
        scene = new Scene(layout);
        scene.getStylesheets().add("Viper.css");
        window.setScene(scene);
        window.show();


    }

    private void closeProgram(){
        boolean answer = ConfirmBox.Display("Quit confirmation",
                "  Are you sure you want to quit? \nDon't forget to save your games!");
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

    public int getWallNumber() {
        return wallNumber;
    }

    private void launchGame(Stage appStage){
        boolean answer = ConfirmBox.Display("Launch confirmation",
                "Are you sure you want to launch the game? Be sure you selected the right settings.");
        if (answer){
            // TODO implement game launch!
            if (getPlayerNumberInt() == 2) {
                String[] types = {getFirstPlayerType(), getSecondPlayerType()};
                Game game = new Game(9, types, getWallNumber());
                GameUI gameUI = new GameUI(appStage, scene, game);
            } else if (getPlayerNumberInt() == 4) {
                String[] types = {getFirstPlayerType(), getSecondPlayerType(),
                        getThirdPlayerType(), getFourthPlayerType()};
                Game game = new Game(9, types, getWallNumber());
                GameUI gameUI = new GameUI(appStage, scene, game);
            } else {
                throw new IllegalArgumentException("expected 2 or 4 player, got " + getPlayerNumberInt());
            }
        }
    }
}
