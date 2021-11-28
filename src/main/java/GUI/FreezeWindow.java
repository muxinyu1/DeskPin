package GUI;

import WIN32.WIN32API;
import WIN32.Win32Instance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class FreezeWindow extends JFrame{
    public FreezeWindow(TrayIcon icon) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setTitle("Choose A Window to Pin");
        setIconImage(Toolkit.getDefaultToolkit().createImage(FreezeWindow.class.getResource("/images/hintWindow.png")));
        setUndecorated(true);
        setSize(screenSize.getSize());
        setBackground(new Color(1, 1, 1, 0.1f));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Button clickToClose = new Button("");
        clickToClose.setSize(screenSize.getSize());
        clickToClose.addActionListener(e -> {
            dispose();
            Win32Instance.WIN_32_API.SetWindow();
            //TODO:点击Pin给置顶窗口添加一个随之移动的子窗口：
            //new PinImage(32, 32);
            icon.displayMessage("Pined A Window", "", TrayIcon.MessageType.INFO);
        });
        clickToClose.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                    dispose();
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
                new ImageIcon(Objects.requireNonNull(FreezeWindow.class.getResource("/images/pin.png"))).getImage(),
                new Point(10, 10), "Pin"
                )
        );
        //getGraphicsConfiguration().getDevice().setFullScreenWindow(this);
        setVisible(true);
    }
}
