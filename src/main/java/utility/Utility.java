package utility;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;

import java.awt.*;

public class Utility {
    //Get Windows10 的主题颜色，以便在系统托盘使用不同颜色的图标
    public static void getTheme(){
        //long color = Advapi32Util.registryGetIntValue(WinReg.HKEY_CURRENT_USER, "SOFTWARE\\Microsoft\\Windows\\DWM", "AccentColor");
        int systemColor = Advapi32Util.registryGetIntValue(
                WinReg.HKEY_CURRENT_USER,
                "Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\History\\Colors",
                "ColorHistory0"
        );
        Color rgbColor = new Color(systemColor);
        System.out.println(rgbColor);
    }
}
