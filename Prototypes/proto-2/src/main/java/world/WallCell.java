package world;

import global.Coord;


public class WallCell extends Cell {

    /**
     * true if the cell has a wall, false otherwise.
     */
    private Boolean wallState;

    public WallCell(Coord coord) {
        super(coord);
        this.wallState = false;
    }

    public WallCell(Coord coord, Boolean wallState) {
        super(coord);
        this.wallState = wallState;
    }

    public Boolean getWallState() {
        return wallState;
    }

    public void setWallState(Boolean wallState) {
        this.wallState = wallState;
    }
}
