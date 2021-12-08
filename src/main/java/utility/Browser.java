package utility;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class Browser {
    public static void browse(URI url) throws IOException {
        if (Desktop.isDesktopSupported()){
            Desktop.getDesktop().browse(url);
        }
        else
            JOptionPane.showMessageDialog(null, "Can't open this website.\nCheck your Browser.");
    }
}
