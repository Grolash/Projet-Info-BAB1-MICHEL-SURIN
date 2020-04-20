package be.ac.umons.michelsurin.gui;

import be.ac.umons.michelsurin.tools.Coord;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class PawnUI {

    private Image img;
    private Coord coord;

    public PawnUI(Image img, Coord coord) {
        this.img = img;
        this.coord = coord;
    }

    public Image getImg() {
        return img;
    }

    public Coord getCoord() {
        return coord;
    }



}
