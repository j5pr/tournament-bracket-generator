package ui;

import ui.menu.AppFrame;
import ui.menu.SplashScreen;

// entry point for tournament app
public class Main {
    // EFFECTS: runs tournament app
    public static void main(String[] args) throws InterruptedException {
        SplashScreen splash = new SplashScreen();
        Thread.sleep(2500);
        splash.dispose();
        new AppFrame();
    }
}
