package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class PinImage extends JFrame {
    public PinImage(int width, int height){
        System.out.println("创建了一个PinImage窗口。");
        JLabel imageContainer = new JLabel("");
        ImageIcon pinIcon = new ImageIcon(Objects.requireNonNull(PinImage.class.getResource("/images/trayImageWhite.png")));
        pinIcon.setImage(pinIcon.getImage().getScaledInstance(width, height,Image.SCALE_DEFAULT ));
        imageContainer.setIcon(pinIcon);
        imageContainer.setSize(width,height);
        imageContainer.setAlignmentX(CENTER_ALIGNMENT);
        imageContainer.setAlignmentY(CENTER_ALIGNMENT);
        imageContainer.setOpaque(false);
        getContentPane().add(imageContainer);
        setUndecorated(true);
        setBackground(new Color(0,0,0,1));
        setSize(width,height);
        setLocation(MouseInfo.getPointerInfo().getLocation());
        setVisible(true);
    }
}
