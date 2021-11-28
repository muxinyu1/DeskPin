package GUI;

import WIN32.Win32Instance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class Tray {
    public static void GUILunch(boolean isBlackTheme)
    {
        //当系统不支持托盘给出警告：
        if (!SystemTray.isSupported())
        {
            JOptionPane.showMessageDialog(null,"不支持系统托盘！\nThe computer doesn't support systemTray!");
            return;
        }
        //构建GUI和事件：
        //创建右键菜单，这时菜单里面什么也没有：
        final PopupMenu popupMenu = new PopupMenu();
        //设置托盘图标：
        final TrayIcon icon = new TrayIcon(Objects.requireNonNull(
                Toolkit.getDefaultToolkit().createImage(isBlackTheme?
                        Tray.class.getResource("/images/trayImageBlack.png")
                        : Tray.class.getResource("/images/trayImageWhite.png"))
        ));
        //获得系统托盘：
        final SystemTray tray = SystemTray.getSystemTray();
        //给右键菜单添加内容：
        //退出键：
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(e -> {
            Win32Instance.WIN_32_API.ClearAllWindows();
            tray.remove(icon);
            //System.out.println("退出！");
            System.exit(0);
        });
        //Pin键：
        MenuItem pin = new MenuItem("Pin");
        pin.addActionListener(e -> {
            //点击“Pin”触发：
            new FreezeWindow(icon);
        });
        //Remove Pins 键：
        MenuItem remove = new MenuItem("Remove Pins");
        remove.addActionListener(e -> {
            Win32Instance.WIN_32_API.ClearAllWindows();
            icon.displayMessage("All Foreground Windows Have Been Removed", "", TrayIcon.MessageType.INFO);
        });
        //修改透明度键：
        MenuItem changeAlpha = new MenuItem("Change Alpha");
        changeAlpha.addActionListener(e -> Win32Instance.WIN_32_API.SetTopWindowTransparent());
        //往空的右键菜单里面添加Item：
        popupMenu.add(pin);
        //TODO:修改窗口透明度：
        //popupMenu.add(changeAlpha);
        popupMenu.add(remove);
        popupMenu.add(exitItem);
        //双击也可以打开Pin; 鼠标悬停显示details：
        icon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 2 )
                    new FreezeWindow(icon);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                new MouseMoveMessage("Hello");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }
        });
        //设置图标的右键菜单：
        icon.setPopupMenu(popupMenu);
        icon.setImageAutoSize(true);
        //启动：
        try {
            tray.add(icon);
        } catch (AWTException e) {
            System.out.println("TrayIcon can't be added!");
        }
    }
}
