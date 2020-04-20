package tests;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class heighteenthTest extends Application{

    Button button;

    public static void main(String[] args){

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Title of the Window");


        Person Bucky = new Person();
        Bucky.firstNameProperty().addListener( (v, oldValue, newValue) -> {
            System.out.println("Name change to:" + newValue);
            System.out.println("firstNameProperty():" + Bucky.firstNameProperty());
            System.out.println("First name:" + Bucky.getFirstName());
        });


        button = new Button();
        button.setText("Click me");
        button.setOnAction(e -> Bucky.setFirstName("Porkypute"));

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
