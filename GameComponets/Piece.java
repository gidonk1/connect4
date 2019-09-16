package GameComponets;

public class Piece {

    private Colour colour;

    public Piece() {
        this.colour = Colour.EMPTY;
    }

    public Piece(Colour colour) {
        this.colour = colour;
    }

    public boolean isEmpty() {
        return getColour().equals(Colour.EMPTY);
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public void printPiece() {
        switch (colour) {
            case RED:
                System.out.print("R");
                break;
            case BLUE:
                System.out.print("B");
                break;
            case EMPTY:
                System.out.print("-");
                break;
        }
    }

    // Two pieces are equal iff they have the same colour.
    @Override
    public boolean equals(Object o) {

        if (o instanceof Piece) {
            if (((Piece) o).colour.equals(colour)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        return colour.hashCode();
    }
}
