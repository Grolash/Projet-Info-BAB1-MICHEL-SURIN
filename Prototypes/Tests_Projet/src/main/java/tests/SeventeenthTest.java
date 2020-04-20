package tests;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.Observable;


public class SeventeenthTest extends Application {

    Stage window;
    Button button;
    BorderPane layout;


    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Menus");

        window.setOnCloseRequest(e -> {
            e.consume(); //Say to Java "Hey, we're handling this ourselves."
            closeProgram();
        });

        //File menu
        Menu fileMenu = new Menu("File");

        //Menu items
        MenuItem newFile = new MenuItem("New...");
        newFile.setOnAction(e -> System.out.println("Blah blah blah..."));
        fileMenu.getItems().add(newFile);
        fileMenu.getItems().add(new MenuItem("New Module..."));
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(new MenuItem("Settings..."));
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(new MenuItem("Exit..."));

        //Edit Menu
        Menu editMenu = new Menu("_Edit");
        editMenu.getItems().add(new MenuItem("Cut"));
        editMenu.getItems().add(new MenuItem("Copy"));
        MenuItem paste = new MenuItem("Paste");
        paste.setDisable(true);
        editMenu.getItems().add(paste);

        //Help Menu
        Menu helpMenu = new Menu("Help");
        CheckMenuItem showLines = new CheckMenuItem("Show Lines Numbers");
        showLines.setOnAction(e -> {
            if(showLines.isSelected())
                System.out.println("Program will now display line numbers.");
            else
                System.out.println("Hiding line numbers.");
        });
        CheckMenuItem autoSave = new CheckMenuItem("Enable Auto Save");
        autoSave.setSelected(true);
        helpMenu.getItems().addAll(showLines, autoSave);


        //Radio Menu (1 selected at a time)
        Menu difficultyMenu = new Menu("Difficulty");

        ToggleGroup difficultyToggle = new ToggleGroup();

        RadioMenuItem easy = new RadioMenuItem("Easy");
        RadioMenuItem medium = new RadioMenuItem("Medium");
        RadioMenuItem hard = new RadioMenuItem("Hard");

        easy.setToggleGroup(difficultyToggle);
        medium.setToggleGroup(difficultyToggle);
        hard.setToggleGroup(difficultyToggle);

        difficultyMenu.getItems().addAll(easy, medium, hard);



        //Main menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu, difficultyMenu);


        layout = new BorderPane();
        layout.setTop(menuBar);
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