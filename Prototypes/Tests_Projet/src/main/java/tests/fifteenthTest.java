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


public class fifteenthTest extends Application{

    Stage window;
    Button button;
    TreeView<String> tree;


    public static void main(String[] args){

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Tree");

        window.setOnCloseRequest(e -> {
            e.consume(); //Say to Java "Hey, we're handling this ourselves."
            closeProgram();
        });
        button = new Button("Click me");


        TreeItem<String> root, bucky, megan;

        //Root
        root = new TreeItem<>();
        root.setExpanded(true);

        //Bucky branch
        bucky = makeBranch("Bucky", root);
        makeBranch("thenewboston", bucky);
        makeBranch("Youtube", bucky);
        makeBranch("Chicken", bucky);
        //Megan branch
        megan = makeBranch("Megan", root);
        makeBranch("Glitter", megan);
        makeBranch("Makeup", megan);

        //Create tree
        tree = new TreeView<>(root);
        tree.setShowRoot(false);
        tree.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) -> {
                    if (newValue != null)
                        System.out.println(newValue.getValue());

                });

        StackPane layout = new StackPane();
        layout.getChildren().add(tree);
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

    //creates branches
    public TreeItem<String> makeBranch(String title, TreeItem<String> parent){
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }
}