package GUI;

import javax.swing.*;
import java.awt.*;

public class Error extends JFrame {
    public Error(String file){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) (screenSize.getWidth() * 0.4), (int) (screenSize.getHeight() * 0.4));
        setLocationRelativeTo(null);
        getContentPane().add(new JLabel("Can't load file: " + file));
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Error("lk");
    }
}
