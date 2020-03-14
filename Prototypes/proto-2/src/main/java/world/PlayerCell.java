package world;


import global.Coord;

class PlayerCell extends Cell {

    /**
     * true if the cell has a pawn, false otherwise.
     */
    private Boolean pawnState;

    public PlayerCell(Coord coord, Boolean pawnState) {
        super(coord);
        this.pawnState = pawnState;
    }

    public PlayerCell(Coord coord) {
        super(coord);
        this.pawnState = false;
    }

    public Boolean getPawnState() {
        return pawnState;
    }

    public void setPawnState(Boolean pawnState) {
        this.pawnState = pawnState;
    }
}