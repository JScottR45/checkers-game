package checkers;

enum PieceColor {

    RED {
        @Override
        PieceColor opposite() {
            return BLACK;
        }
    },

    BLACK {
        @Override
        PieceColor opposite() {
            return RED;
        }
    };

    PieceColor opposite() {
        throw new UnsupportedOperationException();
    }

}
