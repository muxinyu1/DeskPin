package utility;

import GUI.FreezeWindow;
import WIN32.WIN32API;
import com.melloware.jintellitype.JIntellitype;
import com.melloware.jintellitype.JIntellitypeConstants;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.util.Locale;

public class Utility {

    public static void pretreatment() throws IOException {
        //复制文件：
        copyFiles();
        //获取语言信息:
        try {
            language = new LanguageManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LanguageManager language;

    public static class LanguageManager {
        public void changeLangTo(String lang) throws IOException {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(Utility.getFileInTmpDir("lang"))
            );
            switch (lang) {
                case "ja":
                    writer.write("ja");
                    break;
                case "zh":
                    writer.write("zh");
                    break;
                default:
                    writer.write("en");
                    break;
            }
            writer.close();
        }

        public String get(String key) {
            return language.getString(key);
        }

        public String langSymbol;
        private ResourceBundle language = null;

        public LanguageManager() throws IOException {
            setLanguage();
        }

        private void setLanguage() throws IOException {
            //JOptionPane.showMessageDialog(null, "start setting language.");
            //File lang = new File(System.getProperty("java.io.tmpdir") + "lang");
            BufferedReader reader = new BufferedReader(
                    new FileReader(Utility.getFileInTmpDir("lang")));
            langSymbol = reader.readLine();
            System.out.println(langSymbol);
            switch (langSymbol) {
                case "zh":
                    language = ResourceBundle.getBundle("language",
                            new Locale("zh", "cn"));
                    break;
                case "ja":
                    language = ResourceBundle.getBundle("language",
                            new Locale("ja", "jp"));
                    break;
                default:
                    language = ResourceBundle.getBundle("language",
                            new Locale("en", "us"));
                    break;
            }
            reader.close();
        }
    }

    public static void copyFileToTmpDir(String fileName) throws IOException {
        String[] strings = fileName.split("/");
        File topBeCopied = new File(System.getProperty("java.io.tmpdir") + strings[strings.length - 1]);
        if (!topBeCopied.exists()) {
            if (!topBeCopied.createNewFile())
                JOptionPane.showMessageDialog(null, "Create " + strings[strings.length - 1] + " failed.");
        }
        else
            return;
        InputStream fileStream = Utility.class.getResourceAsStream(fileName);
        if (fileStream != null) {
            System.out.println(strings[strings.length - 1] + "is to be copied.");
            FileUtils.copyInputStreamToFile(fileStream, topBeCopied);
            fileStream.close();
        }
        else {
            JOptionPane.showMessageDialog(null, "file to be copied does not exist in .jar\n" + "name: " + fileName);
            System.exit(1);
        }
    }

    public static String getFileInTmpDir(String fileName) {
        return System.getProperty("java.io.tmpdir") + fileName;
    }

    private static void copyFiles() throws IOException {
        //JOptionPane.showMessageDialog(null, "start to copy files.");
        //把lang文件拷贝到tmpdir下
        copyFileToTmpDir("/properties/lang");
        //复制dll：
        copyFileToTmpDir("/dll/WIN_32.dll");
        //复制Theme.exe:
        copyFileToTmpDir("/exe/Theme.exe");
        //复制图片：
        copyFileToTmpDir("/images/hintWindow.png");
        copyFileToTmpDir("/images/pin.png");
        copyFileToTmpDir("/images/pinWindowImage.png");
        copyFileToTmpDir("/images/trayImageBlack.png");
        copyFileToTmpDir("/images/trayImageWhite.png");
        copyFileToTmpDir("/images/about.png");
        copyFileToTmpDir("/images/info.png");
        copyFileToTmpDir("/images/help.png");
        copyFileToTmpDir("/images/helpInfoEng.png");
        copyFileToTmpDir("/images/helpInfoChn.png");
        copyFileToTmpDir("/images/helpInfoJpn.png");
        //JOptionPane.showMessageDialog(null, "finish coping files.");
    }

    public static void registerHotKey(TrayIcon icon){
        JIntellitype.getInstance().registerHotKey(1, JIntellitypeConstants.MOD_ALT, 'Z');
        JIntellitype.getInstance().registerHotKey(2, JIntellitypeConstants.MOD_SHIFT, 'Z');
        JIntellitype.getInstance().addHotKeyListener(i -> {
            if (i == 1) {
                try {
                    new FreezeWindow(icon);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            else{
                WIN32API.WIN_32_API.ClearAllWindows();
                icon.displayMessage(Utility.language.get("rmPins"), "", TrayIcon.MessageType.INFO);
            }
        });

    }

    public static void removeHotKeyListener(){
        JIntellitype.getInstance().unregisterHotKey(1);
        JIntellitype.getInstance().unregisterHotKey(2);
    }
}
