package be.ac.umons.michelsurin.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MouseHandler implements EventHandler<MouseEvent> {

    public MouseHandler() {
    }

    @Override
    public  void handle(MouseEvent event) {
        int y = (int) event.getSceneY();
        int x = (int) event.getSceneX();
    }
}
