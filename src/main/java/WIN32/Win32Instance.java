package WIN32;

import com.sun.jna.Native;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class Win32Instance {
    public static class SourceLoad{
        public void Source() throws URISyntaxException, IOException {
            String tempDir = System.getProperty("java.io.tmpdir");
            File targetDir = new File(tempDir);
            File dllSource = new File(Objects.requireNonNull(this.getClass()
                            .getResource("/dll/WIN_32.dll")).toURI());
            if (!new File(tempDir + "WIN_32.dll").exists())
                FileUtils.copyFileToDirectory(dllSource, targetDir);
            File themeExe = new File(Objects.requireNonNull(this.getClass()
                    .getResource("/exe/Theme.exe")).toURI());
            if (!new File(tempDir + "Theme.exe").exists())
                FileUtils.copyFileToDirectory(themeExe, targetDir);
        }
    }
    public static WIN32API WIN_32_API = Native.load(System.getProperty("java.io.tmpdir") + "WIN_32",
            WIN32API.class
    );
}
