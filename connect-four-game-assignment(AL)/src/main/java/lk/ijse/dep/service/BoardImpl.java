package lk.ijse.dep.service;

public class BoardImpl  implements Board {
    private Piece[][] pieces;
    private BoardUI boardUI;

    public BoardImpl(BoardUI boardUI) {
        this.boardUI = boardUI;
        pieces=new Piece[NUM_OF_COLS][NUM_OF_ROWS];

        for (int x = 0; x < NUM_OF_COLS; x++) {
            for (int y = 0; y < NUM_OF_ROWS; y++)
                pieces[x][y] = Piece.EMPTY;
            }
    };

    @Override
    public BoardUI getBoardUI() {
        return boardUI;
    }

    @Override
    public Piece[][] getPiece(){
        return pieces;
    }

    @Override
    public void updateMove(int col, int row, Piece move) {
        pieces[col][row]=move;
    }

    @Override
    public int FindNextAvailableSpot(int col) {

        for (int i = 0; i < NUM_OF_ROWS; i++) {
            if (pieces[col][i] == Piece.EMPTY) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isLegalMove(int col) {
        if (FindNextAvailableSpot(col) != -1) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean existLegalMoves() {
        for (int i = 0; i < NUM_OF_COLS; i++) {
            for (int x = 0; x < NUM_OF_ROWS; x++) {

                if (pieces[i][x] == Piece.EMPTY) {
                    return true;

                }
            }
        }
        return false;
    }

    @Override
    public void updateMove(int col, Piece move) {
        int raw = FindNextAvailableSpot(col);
        pieces[col][raw] = move;

    }

    @Override
    public Winner findWinner() {
        for (int raw = 0; raw < NUM_OF_ROWS; raw++) {
            for (int col = 0; col < 3; col++) {
                if (pieces[col][raw] != Piece.EMPTY && pieces[col][raw] == pieces[col + 1][raw] && pieces[col][raw] == pieces[col + 2][raw] && pieces[col][raw] == pieces[col + 3][raw]) {
                    return new Winner(pieces[col][raw], col, raw, col + 3, raw);
                }
            }
        }

        for (int col = 0; col < NUM_OF_COLS; col++) {
            for (int raw = 0; raw < 2; raw++) {
                if (pieces[col][raw] != Piece.EMPTY && pieces[col][raw] == pieces[col][raw + 1] && pieces[col][raw] == pieces[col][raw + 2] && pieces[col][raw] == pieces[col][raw + 3]) {
                    return new Winner(pieces[col][raw], col, raw, col, raw + 3);
                }
            }
        }
        return null;
    }


}
