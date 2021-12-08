package Application;

import GUI.Tray;
import utility.FileCheck;
import utility.Utility;
import java.io.IOException;

public class Program {
    static {
        //预处理：拷贝文件
        try {
            //JOptionPane.showMessageDialog(null, "pretreatment() is to be executed.");
            Utility.pretreatment();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        //运行前检查是否缺少文件：
        FileCheck.fileCheck();
        //运行Theme.exe并截获其输出，确定win10主题：
        boolean isBlackTheme = isBlackTheme();
        Tray.GUILunch(isBlackTheme);
    }

    private static boolean isBlackTheme() throws IOException {
        return Runtime.getRuntime().exec(Utility.getFileInTmpDir("Theme.exe"))
                .getInputStream().read() == 49;
    }
}