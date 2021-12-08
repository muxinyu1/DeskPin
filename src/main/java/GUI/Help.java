package GUI;

import utility.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Help extends JFrame {

    public static boolean existed = false;

    public Help(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new Dimension((int)(screenSize.width * 0.2), (int) (screenSize.height * 0.2)));
        setTitle(Utility.language.get("help"));
        setIconImage(Toolkit.getDefaultToolkit().createImage(
                Utility.getFileInTmpDir("help.png")
        ));
        setLocation(screenSize.width - getSize().width, screenSize.height - getSize().height - 60);
        addInfo(this, (int)(screenSize.width * 0.2), (int) (screenSize.height * 0.2));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                existed = false;
            }
        });
        setVisible(true);
        existed = true;
    }

    private static void addInfo(Help help, int width, int height) {
        String helpInfo;
        switch (Utility.language.langSymbol){
            case "zh":
                helpInfo = "helpInfoChn.png";
                break;
            case "ja":
                helpInfo = "helpInfoJpn.png";
                break;
            default:
                helpInfo = "helpInfoEng.png";
        }
        ImageIcon infoIcon = new ImageIcon(Utility.getFileInTmpDir(helpInfo));
        infoIcon.setImage(infoIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        JLabel info = new JLabel(infoIcon);
        help.getContentPane().add(info);
    }
}
