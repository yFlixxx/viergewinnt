import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Menu extends JComponent {
    public static final Color backgroundColor = new Color(86, 138, 199);

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        try {
            g2D.setFont(Font.createFont(Font.TRUETYPE_FONT, new File("/Users/felixgorke/Documents/Poppins-SemiBold.ttf")).deriveFont(Font.BOLD, 60));
        } catch (Exception ex) {
            g2D.setFont(new Font("Default", Font.PLAIN, 20));
        }

        g2D.setColor(backgroundColor);
        g2D.fillRect(0, 0, 800, 600);

        renderTitle(g2D);
        repaint();
    }

    private void renderTitle(Graphics g2D) {

        g2D.setColor(Color.white);
        g2D.drawString("Vier gewinnt", 210, 270);
    }

}
