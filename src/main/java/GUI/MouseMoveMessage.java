package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseMoveMessage extends JPanel {
    private final String message;
    private int x;
    private int y;
    public MouseMoveMessage(String message){
        System.out.println("创建了一个MouseMoveMessage对象。");
        this.message = message;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                x = e.getX();
                y = e.getY();
                repaint();
            }
        });
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString(message, x, y);
    }
}
