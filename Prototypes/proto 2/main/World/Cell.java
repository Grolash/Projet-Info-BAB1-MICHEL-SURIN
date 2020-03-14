
class Cell {

    private Coord coord;
    /**
     * the item contained on the cell. only 1 item by cell is allowed
     */
    private MOAI item;

    public Cell(Coord coord) {
        this.coord = coord;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public MOAI getItem() {
        return item;
    }

    public void setItem(MOAI item) {
        this.item = item;
    }

}