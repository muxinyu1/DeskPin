package utility;

import GUI.Error;

import java.io.*;

public class FileCheck {
    public static void fileCheck() throws IOException {
        InputStream fileList = FileCheck.class.getResourceAsStream("/properties/fileList");
        //assert fileList != null;
        if (fileList != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileList));
            String file;
            while ((file = reader.readLine()) != null) {
                System.out.println("Checking file: " + file);
                if (!new File(Utility.getFileInTmpDir(file)).exists()) {
                    new Error(file);
                    reader.close();
                }
            }
            reader.close();
        }
    }
}
