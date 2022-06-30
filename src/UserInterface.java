import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class UserInterface {
    JFrame window;
    JPanel menupanel;
    JPanel gamepanel;
    JPanel modepanel;
    int mode = 0;
    String playerName = "";
    static int difficulty = 0;

    String gamemode = "";

    static JButton newGameButton = new JButton("Neues Spiel");

    public UserInterface() {
        window = new JFrame();
        window.setSize(800, 600);
        window.setTitle("VierGewinnt");
        window.setResizable(false);
        menupanel = new JPanel();
        gamepanel = new JPanel();
        modepanel = new JPanel();
        modepanel.setLayout(null);
        menupanel.setLayout(null);
        gamepanel.setLayout(null);
    }

    private void initializeBoardButtons() {

        int labelHeight = 50;
        int buttonHeight = 50;
        int chipWidth = Board.chipDiameter;

        newGameButton.setBounds(680, 450, 100, 50);
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.startNewGame(Game.playerName1, Game.playerName2);
                newGameButton.setVisible(false);
            }
        });
        newGameButton.setVisible(false);
        gamepanel.add(newGameButton);

        JButton leaveGameButton = new JButton("Spiel verlassen");
        leaveGameButton.setBounds(680, 510, 100, 50);
        leaveGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePanel(menupanel);
            }
        });
        leaveGameButton.setVisible(true);
        gamepanel.add(leaveGameButton);
    }

    private void initializeMenuButtons() {

        JButton startGameButton = new JButton("Spiel starten");
        startGameButton.setBounds(350, 300, 100, 50);
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePanel(modepanel);
            }
        });
        startGameButton.setVisible(true);
        menupanel.add(startGameButton);

        JButton leaveGameButton = new JButton("Spiel schließen");
        leaveGameButton.setBounds(325, 360, 150, 50);
        leaveGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        leaveGameButton.setVisible(true);
        menupanel.add(leaveGameButton);
    }

    private void initializeMenu() {
        Menu menu = new Menu();
        menu.setBounds(0,0,800,600);
        menupanel.add(menu);
    }

    private void initializeBoard() {
        Board board = new Board();
        board.setBounds(0,0,800,600);
        board.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseEntered(e);
                Point currentPoint = e.getPoint();
                if (Game.winner > 0) {
                    Game.setPreviewedColumn(-1);
                    return;
                } else {
                    if(currentPoint.getY() < 85 || currentPoint.getY() > 530) {
                        Game.setPreviewedColumn(-1);
                    } else if(currentPoint.getX() < 120 || currentPoint.getX() > 640) {
                        Game.setPreviewedColumn(-1);
                    } else if(currentPoint.getX() > 120 && currentPoint.getX() < 190) {
                        Game.setPreviewedColumn(0);
                    } else if(currentPoint.getX() > 195 && currentPoint.getX() < 265) {
                        Game.setPreviewedColumn(1);
                    } else if(currentPoint.getX() > 270 && currentPoint.getX() < 340) {
                        Game.setPreviewedColumn(2);
                    } else if(currentPoint.getX() > 345 && currentPoint.getX() < 415) {
                        Game.setPreviewedColumn(3);
                    } else if(currentPoint.getX() > 420 && currentPoint.getX() < 490) {
                        Game.setPreviewedColumn(4);
                    } else if(currentPoint.getX() > 495 && currentPoint.getX() < 565) {
                        Game.setPreviewedColumn(5);
                    } else if(currentPoint.getX() > 570 && currentPoint.getX() < 640) {
                        Game.setPreviewedColumn(6);
                    }
                }
            }
        });
        board.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(Game.previewedColumn > -1) {
                    Game.playChip(Game.previewedColumn);
                }
            }
        });
        gamepanel.add(board);
    }


    private void initializeModeSelectButtons() {

        JTextField playerName2Input = new RoundJTextField(10, "Spieler 2");
        playerName2Input.setBounds(350, 350, 100, 30);
        playerName2Input.setVisible(false);

        JButton startGameButton = new JButton("Spiel starten");
        startGameButton.setEnabled(false);
        JSlider difficultySlider = new JSlider();


        JButton pvpModeButton = new JButton("PvP");
        pvpModeButton.setBounds(325, 250, 50, 50);
        pvpModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    gamemode = "pvp";
                    difficulty = 0;
                    playerName2Input.setVisible(true);
                    difficultySlider.setVisible(false);
                    startGameButton.setEnabled(true);
                    startGameButton.setBounds(350, 400, 100, 50);
            }
        });
        pvpModeButton.setVisible(true);
        modepanel.add(pvpModeButton);

        difficultySlider.setBounds(350, 350, 100, 30);
        difficultySlider.setMinimum(1);
        difficultySlider.setMaximum(5);
        difficultySlider.createStandardLabels(1);
        difficultySlider.setPaintLabels(true);
        difficultySlider.setValue(1);
        difficultySlider.setVisible(false);
        difficultySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                difficulty = difficultySlider.getValue();
            }
        });


        JButton cpuModeButton = new JButton("CPU");
        cpuModeButton.setBounds(430, 250, 50, 50);
        cpuModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamemode = "cpu";

                difficultySlider.setVisible(true);
                playerName2Input.setVisible(false);
                startGameButton.setEnabled(false);
                startGameButton.setBounds(350, 400, 100, 50);
            }
        });
        cpuModeButton.setVisible(true);
        modepanel.add(cpuModeButton);

        JTextField playerNameInput = new RoundJTextField(10, "Spieler 1");
        playerNameInput.setBounds(350, 310, 100, 30);


        modepanel.add(playerNameInput);
        startGameButton.setBounds(350, 350, 100, 50);
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(gamemode == "pvp") {
                    Game.startNewGame(playerNameInput.getText(), playerName2Input.getText());
                } else if (gamemode == "cpu") {
                    Game.startNewGame(playerNameInput.getText(), "CPU");
                }
                changePanel(gamepanel);
            }
        });
        startGameButton.setVisible(true);
        modepanel.add(startGameButton);
        modepanel.add(playerName2Input);
        modepanel.add(difficultySlider);

    }

    private void initializeModeSelect() {
        ModeSelect modeselect = new ModeSelect();
        modeselect.setBounds(0,0,800,600);
        modepanel.add(modeselect);
    }


    public void initialize() {
        initializeBoardButtons();
        initializeBoard();
        initializeMenuButtons();
        initializeMenu();
        initializeModeSelectButtons();
        initializeModeSelect();
        window.add(menupanel);
        window.setVisible(true);
    }

    private void changePanel(JPanel panel) {
        window.getContentPane().removeAll();
        window.getContentPane().add(panel, BorderLayout.CENTER);
        window.getContentPane().doLayout();
        window.repaint();
    }
}

class RoundJTextField extends JTextField {
    private Shape shape;
    public RoundJTextField(int size, String text) {
        super(text, size);
        setOpaque(false); // As suggested by @AVD in comment.
    }
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 25, 25);
        super.paintComponent(g);
    }
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 25, 25);
    }
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 25, 25);
        }
        return shape.contains(x, y);
    }
}
