public class TicTacToeGame {

    private static final int ROW = 3;
    private static final int COL = 3;
    private String[][] board = new String[ROW][COL];
    private String currentPlayer = "X";
    private int moveCnt = 0;
    private final int MOVES_FOR_WIN = 5;

    public TicTacToeGame() {
        clearBoard();
    }

    public void clearBoard() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                board[row][col] = " ";
            }
        }
        currentPlayer = "X";
        moveCnt = 0;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public String getWinner() {
        return currentPlayer;
    }

    public boolean isValidMove(int row, int col) {
        return board[row][col].equals(" ");
    }

    /**
     * Attempts to make a move and returns the game status.
     * @param row The row index (0-2).
     * @param col The column index (0-2).
     * @return "WIN", "TIE", or "CONTINUE".
     */
    public String makeMove(int row, int col) {
        board[row][col] = currentPlayer;
        moveCnt++;

        if (moveCnt >= MOVES_FOR_WIN) {
            if (isWin(currentPlayer)) {
                return "WIN";
            }
        }

        // CHECK FOR TIE using the complex logic starting at move 7
        if (moveCnt >= 7) {
            if (isTie()) {
                return "TIE";
            }
        }

        // Switch player for the next turn
        currentPlayer = (currentPlayer.equals("X")) ? "O" : "X";

        return "CONTINUE";
    }

    // --- Win Check Methods (unchanged) ---

    private boolean isWin(String player) {
        return isColWin(player) || isRowWin(player) || isDiagnalWin(player);
    }

    private boolean isColWin(String player) {
        for (int col = 0; col < COL; col++) {
            if (board[0][col].equals(player) && board[1][col].equals(player) && board[2][col].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private boolean isRowWin(String player) {
        for (int row = 0; row < ROW; row++) {
            if (board[row][0].equals(player) && board[row][1].equals(player) && board[row][2].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDiagnalWin(String player) {
        if (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) {
            return true;
        }
        if (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player)) {
            return true;
        }
        return false;
    }

    // --- Reintegrated Complex Tie Check (from your console code) ---

    private boolean isTie() {
        boolean xFlag = false;
        boolean oFlag = false;

        // Check for row ties
        for(int row=0; row < ROW; row++) {
            if(board[row][0].equals("X") || board[row][1].equals("X") || board[row][2].equals("X")) {
                xFlag = true;
            }
            if(board[row][0].equals("O") || board[row][1].equals("O") || board[row][2].equals("O")) {
                oFlag = true;
            }
            if(! (xFlag && oFlag) ) {
                return false; // Row not blocked by both X and O, so a win is still possible
            }
            xFlag = oFlag = false;
        }

        // Now scan the columns
        for(int col=0; col < COL; col++) {
            if(board[0][col].equals("X") || board[1][col].equals("X") || board[2][col].equals("X")) {
                xFlag = true;
            }
            if(board[0][col].equals("O") || board[1][col].equals("O") || board[2][col].equals("O")) {
                oFlag = true;
            }
            if(! (xFlag && oFlag) ) {
                return false; // Column not blocked by both X and O
            }
            xFlag = oFlag = false;
        }

        // Now check for the diagonals
        xFlag = oFlag = false;
        if(board[0][0].equals("X") || board[1][1].equals("X") || board[2][2].equals("X") ) { xFlag = true; }
        if(board[0][0].equals("O") || board[1][1].equals("O") || board[2][2].equals("O") ) { oFlag = true; }
        if(! (xFlag && oFlag) ) { return false; } // Diagonal 1 not blocked

        xFlag = oFlag = false;
        if(board[0][2].equals("X") || board[1][1].equals("X") || board[2][0].equals("X") ) { xFlag = true; }
        if(board[0][2].equals("O") || board[1][1].equals("O") || board[2][0].equals("O") ) { oFlag = true; }
        if(! (xFlag && oFlag) ) { return false; } // Diagonal 2 not blocked

        // Checked every vector, so a tie is guaranteed
        return true;
    }
}