package GUI;

import WIN32.WIN32API;
import sun.awt.image.URLImageSource;
import utility.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.util.Objects;

public class FreezeWindow extends JFrame{

    public static boolean existed = false;

    public FreezeWindow(TrayIcon icon) throws MalformedURLException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setTitle("Choose A Window to Pin");
        setIconImage(Toolkit.getDefaultToolkit().createImage(Utility.getFileInTmpDir("hintWindow.png")));
        setUndecorated(true);
        setSize(screenSize.getSize());
        setBackground(new Color(1, 1, 1, 0.1f));
        setLocationRelativeTo(null);
        Button clickToClose = new Button("");
        clickToClose.setSize(screenSize.getSize());
        clickToClose.addActionListener(e -> {
            existed = false;
            dispose();
            WIN32API.WIN_32_API.SetWindow();
            //TODO:点击Pin给置顶窗口添加一个随之移动的子窗口：
            //new PinImage(32, 32);
            icon.displayMessage(Utility.language.get("pinedAWindow"), "", TrayIcon.MessageType.INFO);
        });
        clickToClose.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    existed = false;
                    dispose();
                }
            }
        });
        clickToClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                new MouseMoveMessage("JK");
            }
        });
        getContentPane().add(clickToClose);
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                Toolkit.getDefaultToolkit().createImage(Utility.getFileInTmpDir("pin.png")),
                new Point(10, 10),
                "Pin"
        ));
        existed = true;
        setVisible(true);
    }
}
