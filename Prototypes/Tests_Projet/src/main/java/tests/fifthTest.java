package tests;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class fifthTest extends Application{

    Stage window;
    Button button;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Title");

        button = new Button("You want to know more?");
        button.setOnAction(e -> AlertBox.Display("Test alert", "Click on Close."));
        StackPane layout = new StackPane();
        layout.getChildren().add(button);
        Scene scene = new Scene(layout, 300, 300);
        window.setScene(scene);
        window.show();

    }
}