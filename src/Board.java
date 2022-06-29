import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.AttributedCharacterIterator;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.io.IOException;

public class Board extends JComponent {
    public static final int chipDiameter = 70;
    public static final int spacing = 4;
    public static final Color backgroundColor = new Color(86, 138, 199);
    private static final Color colorPlayer2 = new Color(246, 200, 35);
    private static final Color backgroundColorPlayer2 = new Color(246, 162, 35);
    private static final Color backgroundColorPlayer1 = new Color(218, 0, 0);
    private static final Color colorPlayer1 = new Color(255, 0, 0);
    private static final Color previewColorPlayer2 = new Color(246, 200, 35, 125);
    private static final Color previewBackgroundColorPlayer2 = new Color(246, 162, 35, 125);
    private static final Color previewBackgroundColorPlayer1 = new Color(218, 0, 0, 125);
    private static final Color previewColorPlayer1 = new Color(255, 0, 0, 125);
    private static final Color colorWinner = new Color(22, 193, 40);


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        try {
            g2D.setFont(Font.createFont(Font.TRUETYPE_FONT, new File("/Users/felixgorke/Documents/Poppins-SemiBold.ttf")).deriveFont(Font.BOLD, 20));
        } catch (Exception ex) {
            g2D.setFont(new Font("Default", Font.PLAIN, 20));
        }

        g2D.setColor(backgroundColor);
        g2D.fillRect(0, 0, 800, 600);

        renderBoard(g2D, Game.board, Game.previewedColumn, Game.activePlayer);
        printStatusMessage(g2D, Game.statusMessage);
        renderActivePlayer(g2D, Game.activePlayer);
        renderTimer(g2D, Game.getTimer());
        renderPlayer(g2D, Game.winner, Game.playerName1, Game.playerName2);
        repaint();
    }

    private void printStatusMessage(Graphics g2D, String message) {
        g2D.setColor(Color.white);
        g2D.drawString(message, 280, 70);
    }

    private void renderTimer(Graphics2D g2D, String timer) {
        g2D.setColor(Color.white);
        g2D.drawString(timer, 370, 35);
    }

    private void renderPlayer(Graphics g2D, int winner, String playerName1, String playerName2) {
        int xPosition = 560;
        if (winner == 0) {
            g2D.setColor(Color.white);
            g2D.drawString(playerName1, 120, 70);
            g2D.setColor(backgroundColorPlayer1);
            g2D.fillOval(90, 52, 20, 20);
            g2D.setColor(colorPlayer1);
            g2D.fillOval(90 + 3, 52 + 3, 20 - 5, 20 - 5);

            int playerName2Length = playerName2.length();
            if (playerName2Length > 6) {
                xPosition = 560 - (2 * (playerName2Length - 2)) / 2;
            }

            g2D.setColor(Color.white);
            g2D.drawString(playerName2, xPosition, 70);
            g2D.setColor(backgroundColorPlayer2);
            g2D.fillOval(660, 52, 20, 20);
            g2D.setColor(colorPlayer2);
            g2D.fillOval(660 + 3, 52 + 3, 20 - 5, 20 - 5);
        } else if (winner == 1) {
            g2D.setColor(Color.green);
            g2D.drawString(playerName1, 120, 70);
            g2D.setColor(backgroundColorPlayer1);
            g2D.fillOval(90, 52, 20, 20);
            g2D.setColor(colorPlayer1);
            g2D.fillOval(90 + 3, 52 + 3, 20 - 5, 20 - 5);

            g2D.setColor(Color.red);
            g2D.drawString(playerName2, xPosition, 70);
            g2D.setColor(backgroundColorPlayer2);
            g2D.fillOval(660, 52, 20, 20);
            g2D.setColor(colorPlayer2);
            g2D.fillOval(660 + 3, 52 + 3, 20 - 5, 20 - 5);
        } else if (winner == 2) {
            g2D.setColor(Color.red);
            g2D.drawString(playerName1, 120, 70);
            g2D.setColor(backgroundColorPlayer1);
            g2D.fillOval(90, 52, 20, 20);
            g2D.setColor(colorPlayer1);
            g2D.fillOval(90 + 3, 52 + 3, 20 - 5, 20 - 5);

            g2D.setColor(Color.green);
            g2D.drawString(playerName2, xPosition, 70);
            g2D.setColor(backgroundColorPlayer2);
            g2D.fillOval(660, 52, 20, 20);
            g2D.setColor(colorPlayer2);
            g2D.fillOval(660 + 3, 52 + 3, 20 - 5, 20 - 5);
        }
    }

    private void renderActivePlayer(Graphics g2D, int player) {
        g2D.setColor(Color.white);

        if (player > 0) {
            g2D.drawString("Spieler " + player + " ist am Zug.", 280, 70);
        }

    }

    private void renderBoard(Graphics g2D, int[][] board, int previewChip, int activePlayer) {

        int xPosition = 120;
        int yPosition = 90;



        for (int row = 0; row < Game.ROWS; row++) {
            for (int column = 0; column < Game.COLUMNS; column++) {
                renderChip(g2D, xPosition + (column * chipDiameter) + (column * spacing), yPosition + (row * chipDiameter) + (row * spacing), board[row][column]);
            }
        }

        if (previewChip > -1) {
            int column = previewChip;
            int numberOfChipsInColumn = Game.getNumberOfChipsInColumn(column);
            if (numberOfChipsInColumn < 6) {
                int row = 5 - numberOfChipsInColumn;

                if (activePlayer == 1) {
                    activePlayer = 4;
                } else {
                    activePlayer = 5;
                }

                renderChip(g2D, xPosition + (column * chipDiameter) + (column * spacing), yPosition + (row * chipDiameter) + (row * spacing), activePlayer);
            }
        }
    }

    private void renderChip(Graphics g2D, int x, int y, int player) {

        int spacing = 2;

        if (player == 1) {
            g2D.setColor(backgroundColorPlayer1);
            g2D.fillOval(x, y, chipDiameter, chipDiameter);
            g2D.setColor(colorPlayer1);
            g2D.fillOval(x + 10, y + 10, chipDiameter - 20, chipDiameter - 20);

        }

        if (player == 2) {
            g2D.setColor(backgroundColorPlayer2);
            g2D.fillOval(x, y, chipDiameter, chipDiameter);
            g2D.setColor(colorPlayer2);
            g2D.fillOval(x + 10, y + 10, chipDiameter - 20, chipDiameter - 20);
        }

        if (player == 3) {
            g2D.setColor(colorWinner);
            g2D.fillOval(x, y, chipDiameter, chipDiameter);
        }

        if (player == 4) {
            g2D.setColor(previewBackgroundColorPlayer1);
            g2D.fillOval(x, y, chipDiameter, chipDiameter);
            g2D.setColor(previewColorPlayer1);
            g2D.fillOval(x + 10, y + 10, chipDiameter - 20, chipDiameter - 20);
        }

        if (player == 5) {
            g2D.setColor(previewBackgroundColorPlayer2);
            g2D.fillOval(x, y, chipDiameter, chipDiameter);
            g2D.setColor(previewColorPlayer2);
            g2D.fillOval(x + 10, y + 10, chipDiameter - 20, chipDiameter - 20);
        }

        if (player > 0) {

        } else {
            g2D.setColor(Color.white);
            g2D.fillOval(x, y, chipDiameter, chipDiameter);
            g2D.setColor(Color.black);
            g2D.drawOval(x, y, chipDiameter, chipDiameter);
        }
    }
}
