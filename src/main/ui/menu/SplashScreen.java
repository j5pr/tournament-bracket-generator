package ui.menu;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {
    private final ImageIcon image;

    public SplashScreen() {
        ImageIcon base = new ImageIcon("data/splash.png");
        Image scaled = base.getImage().getScaledInstance(
                base.getIconWidth() * 2,
                base.getIconHeight() * 2,
                Image.SCALE_DEFAULT
        );

        image = new ImageIcon(scaled);
        setSize(image.getIconWidth(), image.getIconHeight());

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - image.getIconWidth()) / 2, (screen.height - image.getIconHeight()) / 2);

        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image.getImage(), 0, 0, this);
    }
}
