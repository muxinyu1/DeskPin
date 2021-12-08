package GUI;

import WIN32.WIN32API;
import utility.ResourceBundle;
import utility.Utility;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Locale;

public class Tray {

    public static void GUILunch(boolean isBlackTheme) throws IOException {
        //当系统不支持托盘给出警告：
        if (!SystemTray.isSupported()) {
            //JOptionPane.showMessageDialog(null, "不支持系统托盘！\nThe computer doesn't support systemTray!");
            return;
        }
        //构建GUI和事件：
        //创建右键菜单，这时菜单里面什么也没有：
        final PopupMenu popupMenu = new PopupMenu();
        //设置托盘图标：
        //JOptionPane.showMessageDialog(null, "start setting TrayIcon().");
        final TrayIcon icon = new TrayIcon(Toolkit.getDefaultToolkit().createImage(
                isBlackTheme?
                        Utility.getFileInTmpDir("trayImageBlack.png"):
                        Utility.getFileInTmpDir("trayImageWhite.png")
        ));
        //JOptionPane.showMessageDialog(null, "finish setting TrayIcon().");
        //获得系统托盘：
        final SystemTray tray = SystemTray.getSystemTray();
        //给右键菜单添加内容：
        //退出键：
        MenuItem exitItem = new MenuItem(Utility.language.get("exit"));
        exitItem.addActionListener(e -> {
            //清除所有置顶窗口：
            WIN32API.WIN_32_API.ClearAllWindows();
            //托盘移除应用图标：
            tray.remove(icon);
            //注销热键：
            Utility.removeHotKeyListener();
            System.exit(0);
        });
        //Pin键：
        MenuItem pin = new MenuItem(Utility.language.get("pin"));
        pin.addActionListener(e -> {
            //点击“Pin”触发：
            try {
                if (!FreezeWindow.existed)
                    new FreezeWindow(icon);
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        });
        //Remove Pins 键：
        MenuItem remove = new MenuItem(Utility.language.get("rm"));
        remove.addActionListener(e -> {
            WIN32API.WIN_32_API.ClearAllWindows();
            icon.displayMessage(Utility.language.get("rmPins"), "", TrayIcon.MessageType.INFO);
        });
        //修改透明度键：
        MenuItem changeAlpha = new MenuItem("Change Alpha");
        changeAlpha.addActionListener(e -> WIN32API.WIN_32_API.SetTopWindowTransparent());
        //往空的右键菜单里面添加Item：
        popupMenu.add(pin);
        //TODO:修改窗口透明度：
        popupMenu.add(remove);
        addLanguageOption(popupMenu, icon);
        addHelpOption(popupMenu);
        addAboutOption(popupMenu);
        popupMenu.add(exitItem);
        //双击也可以打开Pin; 鼠标悬停显示details：
        icon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() >= 1) {
                    try {
                        if (!FreezeWindow.existed)
                            new FreezeWindow(icon);
                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                    }
                }
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
        //注册全局热键：
        Utility.registerHotKey(icon);
        //启动：
        try {
            tray.add(icon);
        } catch (AWTException e) {
            //JOptionPane.showMessageDialog(null, "TrayIcon can't be added!");
        }
    }

    private static CheckboxMenuItem japanese = null;

    private static CheckboxMenuItem chinese = null;

    private static CheckboxMenuItem english = null;
    /**
     * 给右键菜单添加更改语言的选项
     * @param popupMenu， 要加入语言选项的目录
     */
    private static void addLanguageOption(PopupMenu popupMenu, TrayIcon icon) throws IOException {
        //JOptionPane.showMessageDialog(null, "start addLangOption().");
        Menu changeLang = new Menu(Utility.language.get("changeLang"));
        CheckboxMenuItem en = new CheckboxMenuItem(ResourceBundle.getBundle("language",
                new Locale("en", "us")).getString("language"),
                Utility.language.langSymbol.equals("en"));
        CheckboxMenuItem zh = new CheckboxMenuItem(ResourceBundle.getBundle("language",
                new Locale("zh", "cn")).getString("language"),
                Utility.language.langSymbol.equals("zh"));
        CheckboxMenuItem ja = new CheckboxMenuItem(ResourceBundle.getBundle("language",
                new Locale("ja", "jp")).getString("language"),
                Utility.language.langSymbol.equals("ja"));
        japanese = ja;
        chinese = zh;
        english = en;
        //JOptionPane.showMessageDialog(null, "finish building CheckboxMenuItem().");
        en.addItemListener(e -> {
            if (en.getState()) {
                try {
                    Utility.language.changeLangTo("en");
                    chinese.setState(false);
                    japanese.setState(false);
                    PopMsg(icon);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        zh.addItemListener(e -> {
            if (zh.getState()) {
                try {
                    Utility.language.changeLangTo("zh");
                    english.setState(false);
                    japanese.setState(false);
                    PopMsg(icon);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        ja.addItemListener(e -> {
            if (ja.getState()) {
                try {
                    Utility.language.changeLangTo("ja");
                    english.setState(false);
                    chinese.setState(false);
                    PopMsg(icon);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        changeLang.add(en);
        changeLang.add(zh);
        changeLang.add(ja);
        popupMenu.add(changeLang);
        //JOptionPane.showMessageDialog(null, "finish addLangOption().");
    }

    private static void addAboutOption(PopupMenu popupMenu){
        MenuItem about = new MenuItem(Utility.language.get("about"));
        about.addActionListener(e -> {
            if (!About.existed)
                new About();
        });
        popupMenu.add(about);
    }

    private static void addHelpOption(PopupMenu popupMenu){
        MenuItem help = new MenuItem(Utility.language.get("help"));
        help.addActionListener(e -> {
            if (!Help.existed)
                new Help();
        });
        popupMenu.add(help);
    }

    private static void PopMsg(TrayIcon icon){
        icon.displayMessage(Utility.language.get("changeLangTitle"), Utility.language.get("changeLangMsg"), TrayIcon.MessageType.INFO);
    }
}
