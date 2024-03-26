package ui.menu;

import javax.swing.*;
import java.awt.*;

// the splash screen for the tournament app, to be shown when the app is launched
public class SplashScreen extends JWindow {
    private final ImageIcon image;

    // EFFECTS: constructs a new SplashScreen
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

    // MODIFIES: this
    // EFFECTS: paints the image on the splash screen window
    @Override
    public void paint(Graphics g) {
        g.drawImage(image.getImage(), 0, 0, this);
    }
}
