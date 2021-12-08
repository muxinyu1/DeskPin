package WIN32;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface WIN32API extends Library{
    WIN32API WIN_32_API = Native.load(System.getProperty("java.io.tmpdir") + "WIN_32",
            WIN32API.class);
    int Add(int a, int b);
    void SetWindow();
    void ClearAllWindows();
    void SetTopWindowTransparent();
}
