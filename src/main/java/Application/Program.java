package Application;

import GUI.Tray;
import WIN32.WIN32API;
import WIN32.Win32Instance;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class Program {
    static {
        try {
            new Win32Instance.SourceLoad().Source();
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        boolean isBlackTheme = Runtime.getRuntime().exec(System.getProperty("java.io.tmpdir") + "Theme.exe")
                        .getInputStream().read() == 49;
        Tray.GUILunch(isBlackTheme);
    }
    public void print(){

        String path = Objects.requireNonNull(WIN32API.class.getResource("/")).getPath();
        System.out.println(
                Objects.requireNonNull(WIN32API.class.getResource("/")).getPath().substring(1) +
                        "dll/WIN_32");
    }
}