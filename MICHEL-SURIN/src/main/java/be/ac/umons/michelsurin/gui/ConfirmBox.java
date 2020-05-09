package be.ac.umons.michelsurin.gui;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

/**
 * A small window to demand confirmation.
 */
public class ConfirmBox {

    /**
     * Boolean value of the answer.
     */
    static boolean answer;

    /**
     * A small window to demand confirmation.
     * @param title The title of the window.
     * @param question The text (usually a question asked to the user) displayed by the ConfirmBox.
     * @return boolean value corresponding to the answer given to the question.
     */
    public static boolean Display(String title, String question){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL); //Prevent interactions events with the called until taken care of.
        window.setTitle(title);
        window.setMinWidth(400);
        window.setMinHeight(250);

        Label label = new Label();
        label.setText(question);

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });

        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        window.setOnCloseRequest(e -> {
            e.consume(); //Say to Java : "Hey, we're handling this ourselves."
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("Viper.css");
        window.setScene(scene);
        window.showAndWait(); //Assures the window stay displayed until taken care of.

        return answer;
    }
}