package GUI;

import utility.Browser;
import utility.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class About extends JFrame {
    public static boolean existed = false;

    public About() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new Dimension((int) (screenSize.width * 0.2), (int) (screenSize.height * 0.2)));
        setTitle(Utility.language.get("about"));
        setIconImage(Toolkit.getDefaultToolkit().createImage(
                Utility.getFileInTmpDir("about.png")
        ));
        setLocation(screenSize.width - getSize().width, screenSize.height - getSize().height - 60);
        addInfo(this, (int) (screenSize.width * 0.2), (int) (screenSize.height * 0.2));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                existed = false;
            }
        });
        setVisible(true);
        existed = true;
    }

    private static void addInfo(JFrame about, int width, int height) {
        ImageIcon infoIcon = new ImageIcon(Utility.getFileInTmpDir("info.png"));
        infoIcon.setImage(infoIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        JLabel info = new JLabel(infoIcon);
        info.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Browser.browse(new URI("https://cn.bing.com/?FORM=Z9FD1"));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
                existed = false;
                about.dispose();
            }
        });
        info.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        about.getContentPane().add(info);
    }
}
