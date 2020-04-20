package be.ac.umons.michelsurin.gui;

import be.ac.umons.michelsurin.tools.Coord;
import javafx.scene.image.Image;

public class CellUI {

    private Image img;
    private Coord coord;

    public CellUI(Image img, Coord coord) {
        this.img = img;
        this.coord = coord;
    }

    public Coord getCoord() {
        return coord;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }
}
