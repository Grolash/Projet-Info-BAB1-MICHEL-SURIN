package be.ac.umons.michelsurin.gui;

import be.ac.umons.michelsurin.engine.Game;
import be.ac.umons.michelsurin.engine.SaverLoader;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.print.attribute.standard.Media;
import javax.print.attribute.standard.MediaName;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.spi.AudioFileReader;
import java.applet.AudioClip;
import java.io.IOException;


/**
 * Application. Here is the main menu and the settings.
 */
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
     * Number of players. ChoiceBox.
     */
    private ChoiceBox<String> playerNumber;
    /**
     * Difficulty menu for the first player if it's and AI. ChoiceBox.
     */
    private ChoiceBox<String> firstAIDifficultyMenu;
    /**
     * Difficulty menu for the second player if it's and AI. ChoiceBox.
     */
    private ChoiceBox<String> secondAIDifficultyMenu;
    /**
     * Difficulty menu for the third player if it's and AI. ChoiceBox.
     */
    private ChoiceBox<String> thirdAIDifficultyMenu;
    /**
     * Difficulty menu for the fourth player if it's and AI. ChoiceBox.
     */
    private ChoiceBox<String> fourthAIDifficultyMenu;
    /**
     * Wall options menu. ChoiceBox.
     */
    private ChoiceBox<String> wallOptions;
    /**
     * board size option menu. ChoiceBox.
     */
    private ChoiceBox<String> boardSizeOption;
    /**
     * Number of players in the game.
     */
    private int playerNumberInt;
    /**
     * Type of the first player.
     */
    private String firstPlayerType;
    /**
     * Type of the second player.
     */
    private String secondPlayerType;
    /**
     * Type of the third player.
     */
    private String thirdPlayerType;
    /**
     * Type of the fourth player.
     */
    private String fourthPlayerType;
    /**
     * Number of wall for each player in the game.
     */
    private int wallNumber;
    /**
     * Size of the board.
     */
    private int boardSize;
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Called at launch of application. Main menu.
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

        layout = new VBox(); //Sole layout. Gets children.
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(20);
        layout.setBackground(Background.EMPTY);
        Label title = new Label("QUORIDOR v0.9.2");
        layout.getChildren().addAll(title);

        launchButton = new Button("Launch game!");
        launchButton.setOnAction(e -> launchGame(window));
        layout.getChildren().add(launchButton);

        loadButton = new Button("Load Game");
        loadButton.setOnAction(e -> {
        //Loaded game launch lambda method.
                try {
                    Game game = SaverLoader.load();
                    GameUI gameUI = new GameUI(window, scene, game);
                } catch (IOException | ClassNotFoundException f) {
                    f.printStackTrace();
                }

        });
        //Adding to layout.
        layout.getChildren().add(loadButton);

        layout.getChildren().add(closeButton);

        Separator separator = new Separator(); //The line separating the main buttons from settings menus.
        layout.getChildren().add(separator);


        Label settings = new Label("Settings:");
        layout.getChildren().add(settings);

        HBox line = new HBox();
        VBox column1 = new VBox();
        VBox column2 = new VBox();
        column1.setSpacing(15);
        column2.setSpacing(15);
        line.getChildren().addAll(column1, column2);
        line.setAlignment(Pos.CENTER);
        line.setSpacing(50);
        layout.getChildren().add(line);

        //Following lines will set difficulty menus for AI. Listeners will follow in real time selection changes.
        firstAIDifficultyMenu = new ChoiceBox<>();
        firstAIDifficultyMenu.getItems().addAll("Human", "Random AI", "Easy", "Harder");
        firstAIDifficultyMenu.setValue("Human");
        firstPlayerType = getPlayerType(firstAIDifficultyMenu.getValue());
        firstAIDifficultyMenu.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            firstPlayerType = getPlayerType(newValue);
        });
        firstAIDifficultyMenu.setMinSize(95, 30);
        firstAIDifficultyMenu.setMaxSize(95, 30);

        secondAIDifficultyMenu = new ChoiceBox<>();
        secondAIDifficultyMenu.getItems().addAll("Human", "Random AI", "Easy", "Harder");
        secondAIDifficultyMenu.setValue("Easy");
        secondPlayerType = getPlayerType(secondAIDifficultyMenu.getValue());
        secondAIDifficultyMenu.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            secondPlayerType = getPlayerType(newValue);
        });
        secondAIDifficultyMenu.setMinSize(95, 30);
        secondAIDifficultyMenu.setMaxSize(95, 30);

        thirdAIDifficultyMenu = new ChoiceBox<>();
        thirdAIDifficultyMenu.getItems().addAll("Human", "Random AI", "Easy", "Harder");
        thirdAIDifficultyMenu.setValue("Easy");
        thirdPlayerType = getPlayerType(thirdAIDifficultyMenu.getValue());
        thirdAIDifficultyMenu.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            thirdPlayerType = getPlayerType(newValue);
        });
        thirdAIDifficultyMenu.setMinSize(95, 30);
        thirdAIDifficultyMenu.setMaxSize(95, 30);

        fourthAIDifficultyMenu = new ChoiceBox<>();
        fourthAIDifficultyMenu.getItems().addAll("Human", "Random AI", "Easy", "Harder");
        fourthAIDifficultyMenu.setValue("Easy");
        fourthPlayerType = getPlayerType(fourthAIDifficultyMenu.getValue());
        fourthAIDifficultyMenu.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            fourthPlayerType  = getPlayerType(newValue);
        });
        fourthAIDifficultyMenu.setMinSize(95, 30);
        fourthAIDifficultyMenu.setMaxSize(95, 30);

        Label walls = new Label("Walls:");
        column1.getChildren().add(walls);
        //Number of walls menu:
        wallOptions = new ChoiceBox<>();
        wallOptions.setMinSize(110, 30);
        wallOptions.setMaxSize(110, 30);
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
        column1.getChildren().add(wallOptions);

        Label boardSizeSetting = new Label("Board size :");
        column1.getChildren().add(boardSizeSetting);
        //board size menu :
        boardSizeOption = new ChoiceBox<>();
        boardSizeOption.getItems().addAll("Tiny", "Standard", "Huge");
        boardSizeOption.setValue("Standard");
        boardSize = getBoardSize(boardSizeOption.getValue());
        boardSizeOption.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            boardSize = getBoardSize(newValue);
        });
        boardSizeOption.setMinSize(95, 30);
        boardSizeOption.setMaxSize(95, 30);
        column1.getChildren().add(boardSizeOption);

        Label players = new Label("Players:");
        column2.getChildren().add(players);
        //Player number menus, will define which difficulty options are enabled.
        playerNumber = new ChoiceBox<>();
        playerNumber.setMinSize(110, 30);
        playerNumber.setMaxSize(110, 30);
        playerNumber.getItems().addAll( "2 Players",
                "4 Players");
        playerNumber.setValue("2 Players");
        playerNumberInt = 2;
        column2.getChildren().add(playerNumber);
        Label difficulty = new Label("AI difficulty:");
        column2.getChildren().add(difficulty);
        //default state
        column2.getChildren().remove(firstAIDifficultyMenu);
        column2.getChildren().remove(secondAIDifficultyMenu);
        column2.getChildren().remove(thirdAIDifficultyMenu);
        column2.getChildren().remove(fourthAIDifficultyMenu);
        column2.getChildren().addAll(firstAIDifficultyMenu, secondAIDifficultyMenu);
        playerNumber.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            if (newValue.equals("2 Players")){
                playerNumberInt = 2;
                column2.getChildren().remove(firstAIDifficultyMenu);
                column2.getChildren().remove(secondAIDifficultyMenu);
                column2.getChildren().remove(thirdAIDifficultyMenu);
                column2.getChildren().remove(fourthAIDifficultyMenu);
                column2.getChildren().addAll(firstAIDifficultyMenu, secondAIDifficultyMenu);
            }
            else if (newValue.equals("4 Players")){
                playerNumberInt = 4;
                column2.getChildren().remove(firstAIDifficultyMenu);
                column2.getChildren().remove(secondAIDifficultyMenu);
                column2.getChildren().addAll(firstAIDifficultyMenu, secondAIDifficultyMenu, thirdAIDifficultyMenu, fourthAIDifficultyMenu);
            }
        });
        layout.setMinSize(700, 700);
        scene = new Scene(layout);
        scene.getStylesheets().add("Viper.css");
        window.setScene(scene);
        window.show();


    }
    /**
     * The close method of the program. Calls a ConfirmBox prevent missed clicks.
     */
    private void closeProgram(){
        boolean answer = ConfirmBox.Display("Quit confirmation",
                "  Are you sure you want to quit? \nDon't forget to save your games!");
        if (answer) {
            window.close();
        }
    }

    /**
     * Convert string to a valid board size.
     * @param string
     * @return
     * @throws IllegalArgumentException
     */
    private int getBoardSize(String string) {
        switch (string) {
            case "Tiny" :
                return 5;
            case "Standard" :
                return 9;
            case "Huge" :
                return 15;
            default:
                throw new IllegalArgumentException("Wrong board size !");
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

    //Getters!

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

    /**
     * GameUI launch method. Creates a game with selected settings and starts GameUI.
     * @param appStage
     */
    private void launchGame(Stage appStage){
        boolean answer = ConfirmBox.Display("Launch confirmation",
                "Are you sure you want to launch the game? \n    Be sure you selected the right settings.");
        if (answer){
            if (getPlayerNumberInt() == 2) {
                String[] types = {getFirstPlayerType(), getSecondPlayerType()};
                Game game = new Game(9, types, getWallNumber(), 0);
                GameUI gameUI = new GameUI(appStage, scene, game);
            } else if (getPlayerNumberInt() == 4) {
                String[] types = {getFirstPlayerType(), getSecondPlayerType(),
                        getThirdPlayerType(), getFourthPlayerType()};
                Game game = new Game(9, types, getWallNumber(), 0);
                GameUI gameUI = new GameUI(appStage, scene, game);
            } else {
                throw new IllegalArgumentException("expected 2 or 4 player, got " + getPlayerNumberInt());
            }
        }
    }
}
