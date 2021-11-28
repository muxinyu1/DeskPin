package WIN32;

import com.sun.jna.Library;

public interface WIN32API extends Library{
    int Add(int a, int b);
    void SetWindow();
    void ClearAllWindows();
    boolean GetSystemTheme();
    void SetTopWindowTransparent();
}
