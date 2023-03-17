package lk.ijse.dep.service;

public class AiPlayer extends Player{
    public AiPlayer(Board board) {
        super(board);
    }

    @Override
    public void movePiece(int col) {
//        do{
//            col = (int) (Math.random()*6);
//            System.out.println(col);
//        }while (!board.isLegalMove(col));


        Piece[][] piece = board.getPiece();

        int maxEval = (int) Double.NEGATIVE_INFINITY;
        int row = 0;

        for(int lpClm=0;lpClm<board.NUM_OF_COLS;lpClm++){
            for(int lpRaw=0;lpRaw<board.NUM_OF_ROWS;lpRaw++){
                if(piece[lpClm][lpRaw]==Piece.EMPTY){
                    piece[lpClm][lpRaw]=Piece.GREEN;
                    int heuristicVal = minimax(piece,0, false);
                    piece[lpClm][lpRaw]=Piece.EMPTY;
                    if(maxEval < heuristicVal){
                        maxEval=heuristicVal;
                        col=lpClm;
                        row=lpRaw;
                    }
                }
            }
        }
        board.updateMove(col,row,Piece.GREEN);

        board.getBoardUI().update(col,false);
        Winner winner = board.findWinner();
        if(winner!=null){
            board.getBoardUI().notifyWinner(winner);
        }else{
            if (!board.existLegalMoves()){
                board.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
            }
        }
    }

    private int minimax(Piece [][] pieces,int depth, boolean maximizingPlayer){
        String winner = findWinner(pieces);
        if(depth == 4 || winner!=null){
            if(winner==null){
                return 0;
            }
            switch (winner){
                case "HUMAN WIN":
                    return -1;
                case "AI WIN":
                    return 1;
                case "GAME TIE":
                    return 0;
            }
        }
        if(maximizingPlayer){
            int maxEval = (int) Double.NEGATIVE_INFINITY;
            for(int j=0;j<Board.NUM_OF_ROWS;j++) {
                for (int i = 0; i < Board.NUM_OF_COLS; i++) {
                    if(pieces[i][j]== Piece.EMPTY) {
                        pieces[i][j]=Piece.GREEN;
                        int heuristicVal = minimax(pieces,depth + 1, false);
                        pieces[i][j]=Piece.EMPTY;
                        maxEval = Math.max(heuristicVal, maxEval);
                    }
                }
            }
            return  maxEval;
        }else {
            int minEval = (int) Double.POSITIVE_INFINITY;
            for (int j = 0; j < Board.NUM_OF_ROWS; j++) {
                for (int i = 0; i < Board.NUM_OF_COLS; i++) {
                    if (pieces[i][j] == Piece.EMPTY) {
                        pieces[i][j] = Piece.BLUE;
                        int heuristicVal = minimax(pieces, depth + 1, true);
                        pieces[i][j] = Piece.EMPTY;
                        minEval = Math.min(heuristicVal, minEval);
                    }
                }
            }
            return minEval;
        }
    }

    private String findWinner(Piece [][] pieces){
        String winner=null;
        for(int r=0;r<board.NUM_OF_ROWS;r++){
            for (int c = 0; c < 3; c++) {
                if (pieces[c][r]!=Piece.EMPTY && pieces[c][r] == pieces[c + 1][r] && pieces[c][r] == pieces[c + 2][r] && pieces[c][r] == pieces[c + 3][r]) {
                    if(pieces[c][r]==Piece.BLUE){
                        winner = "HUMAN WIN";
                    }else if(pieces[c][r]==Piece.GREEN){
                        winner = "AI WIN";
                    }
                }
            }
        }

        for(int c=0;c<board.NUM_OF_COLS;c++){
            for (int r = 0; r < 2; r++) {
                if (pieces[c][r]!=Piece.EMPTY && pieces[c][r] == pieces[c][r+1] && pieces[c][r] == pieces[c][r+2] && pieces[c][r] == pieces[c][r+3]) {
                    if(pieces[c][r]==Piece.BLUE){
                        winner = "HUMAN WIN";
                    }else if(pieces[c][r]==Piece.GREEN){
                        winner = "AI WIN";
                    }
                }
            }
        }
        boolean notEmpty = true;
        for(int i=0;i<board.NUM_OF_ROWS;i++){
            for(int j=0;j<board.NUM_OF_COLS;j++){
                if(pieces[j][i]==Piece.EMPTY){
                    notEmpty = false;
                }
            }
        }
        if(winner==null && notEmpty){
            return "GAME TIE";
        }
        return winner;
    }
}
