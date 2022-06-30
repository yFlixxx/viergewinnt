import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Game {
    public static final int VIER_GEWINNT = 4;
    public static final int ROWS = 6;
    public static final int COLUMNS = 7;
    public static final int NUMBER_OF_PLAYERS = 2;
    private static final int[][] EMPTY_BOARD = {
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0}
    };

    public static int[][] board = EMPTY_BOARD;

    public static int activePlayer = 1;
    public static int winner = 0;

    public static int previewedColumn = -1;

    public static String statusMessage = "";
    public static int previewChip;
    public static Date startDate;
    public static String timer = "";
    private static boolean timerStop;
    private static boolean timerHour = false;
    private static int filledColumns = 0;
    public static String playerName1;
    public static String playerName2;

    public static void startNewGame(String player1, String... player2) {
        playerName1 = player1;
        if(player2.length >= 1) {
            playerName2 = player2[0];
        } else {
            playerName2 = "Spieler 2";
        }

        activePlayer = 1;
        winner = 0;
        statusMessage = "";
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                board[row][column] = 0;
            }
        }

        startDate = new Date();
        timerHour = false;
        timerStop = false;
    }

    public static String getTimer() {

        if (!timerStop) {
            if(timer.equals("59:59")) {
                timerHour = true;
            }
            SimpleDateFormat sdf;
            Date now = new Date();
            if (timerHour == true) {
                sdf = new SimpleDateFormat("hh:mm:ss");
            } else {
                sdf = new SimpleDateFormat("mm:ss");
            }
            timer = sdf.format(new Date(now.getTime() - startDate.getTime()));
        }
        return timer;
    }

    public static void setPreviewedColumn(int column) {
        previewedColumn = column;
    }

    public static void playChip(int column) {
        if (winner > 0) {
            return;
        }

        int numberOfChipsInColumn = getNumberOfChipsInColumn(column);
        if (numberOfChipsInColumn < 6) {

            int rowForChip = 5 - numberOfChipsInColumn;
            board[rowForChip][column] = activePlayer;

            if (activePlayer == 1) {
                activePlayer = 2;
            } else {
                activePlayer = 1;
            }

            checkBoard();
        }
    }

    public static int getNumberOfChipsInColumn(int column) {
        int numberOfChipsInColumn = 0;
        for (int row = 5; row >= 0; row--) {
            if (board[row][column] > 0) {
                numberOfChipsInColumn = numberOfChipsInColumn + 1;
            }

        }
        return numberOfChipsInColumn;
    }

    private static int checkRowsForWinner() {

        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS-3; column++) {
                if (board[row][column] == 1 && board[row][column + 1] == 1 && board[row][column + 2] == 1 && board[row][column + 3] == 1) {
                    return 1;
                } else if (board[row][column] == 2 && board[row][column + 1] == 2 && board[row][column + 2] == 2 && board[row][column + 3] == 2) {
                    return 2;
                }
            }
        }
        return 0;
    }

    private static int checkColumnsForWinner() {

        for (int row = 0; row < ROWS-3; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                if (board[row][column] == 1 && board[row + 1][column] == 1 && board[row + 2][column] == 1 && board[row + 3][column] == 1) {
                    return 1;
                } else if (board[row][column] == 2 && board[row + 1][column] == 2 && board[row + 2][column] == 2 && board[row + 3][column] == 2) {
                    return 2;
                }
            }
        }
        return 0;
    }


    private static int checkUpperLeftToLowerRightForWinner() {

        for (int row = 0; row < ROWS-3; row++) {
            for (int column = 0; column < COLUMNS-3; column++) {
                if (board[row][column] == 1 && board[row + 1][column + 1] == 1 && board[row + 2][column + 2] == 1 && board[row + 3][column + 3] == 1) {
                    return 1;
                } else if (board[row][column] == 2 && board[row + 1][column + 1] == 2 && board[row + 2][column + 2] == 2 && board[row + 3][column + 3] == 2) {
                    return 2;
                }
            }
        }
        return 0;
    }

    private static int checkLowerLeftToUpperRight() {

        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                if (column-3 >= 0 && row+3 < 6 && board[row][column] == 1 && board[row + 1][column - 1] == 1 && board[row +  2][column - 2] == 1 && board[row +  3][column - 3] == 1) {
                    return 1;
                } else if (column-3 >= 0 && row+3 < 6 && board[row][column] == 2 && board[row + 1][column - 1] == 2 && board[row + 2][column - 2] == 2 && board[row + 3][column - 3] == 2) {
                    return 2;
                }
            }
        }
        return 0;
    }

    private static void checkBoard() {
        // check rows for a winner

        int winnerInRow = checkRowsForWinner();
        if (winnerInRow > 0) {
            statusMessage = "Spieler " + winnerInRow + " hat gewonnen!";
            winner = winnerInRow;
            activePlayer = 0;
            UserInterface.newGameButton.setVisible(true);
            timerStop = true;
            return;
        }

        // check columns for a winner
        int winnerInColumns = checkColumnsForWinner();
        if (winnerInColumns > 0) {
            statusMessage = "Spieler " + winnerInColumns + " hat gewonnen!";
            winner = winnerInColumns;
            activePlayer = 0;
            UserInterface.newGameButton.setVisible(true);
            timerStop = true;
            return;
        }

        // check diagonals for a winner
        int winnerInDiagonals = checkUpperLeftToLowerRightForWinner();
        if (winnerInDiagonals > 0) {
            statusMessage = "Spieler " + winnerInDiagonals + " hat gewonnen!";
            winner = winnerInDiagonals;
            activePlayer = 0;
            UserInterface.newGameButton.setVisible(true);
            timerStop = true;
            return;
        }

        winnerInDiagonals = checkLowerLeftToUpperRight();
        if (winnerInDiagonals > 0) {
            statusMessage = "Spieler " + winnerInDiagonals + " hat gewonnen!";
            winner = winnerInDiagonals;
            activePlayer = 0;
            UserInterface.newGameButton.setVisible(true);
            timerStop = true;
            return;
        }

        //draw
        if(getNumberOfChipsInColumn(0) == 6 && getNumberOfChipsInColumn(1) == 6 && getNumberOfChipsInColumn(2) == 6 && getNumberOfChipsInColumn(3) == 6 && getNumberOfChipsInColumn(4) == 6 && getNumberOfChipsInColumn(5) == 6 && getNumberOfChipsInColumn(6)== 6) {
            statusMessage = "Keiner hat gewonnen!";
            winner = 0;
            activePlayer = 0;
            UserInterface.newGameButton.setVisible(true);
            timerStop = true;
            return;
        }
    }
}
