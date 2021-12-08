package utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;

public class ResourceBundle {

    /**
     * ��������������һ��ResourceBundleʵ������ʵ����һ�����԰���
     * @param baseName .properties�ļ��Ļ�׼��
     * @param locale new��������new Local("zh", "cn")����ʾ���ģ���½��
     * @return һ��ResourceBundleʵ��
     */
    public static ResourceBundle getBundle(String baseName, Locale locale) throws IOException {
        StringBuilder fileName = new StringBuilder(baseName);
        fileName.append("_").append(locale.getLanguage()).append("_").
                append(locale.getCountry().toLowerCase()).append(".properties");
        InputStream property = ResourceBundle.class.getResourceAsStream("/" + fileName);
        if (property == null){
            //JOptionPane.showMessageDialog(null, "cant open language package: " + fileName);
            System.out.println("cant open language package: " + fileName);
            System.exit(1);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(property));
        HashMap<String, String> langConverter = new HashMap<>();
        String aLine;
        while ((aLine = reader.readLine()) != null){
            String[] key_word = aLine.split(" = ");
            if (key_word.length != 2){
                //JOptionPane.showMessageDialog(null, "language package is damaged.");
                System.out.println("language package is damaged.");
                System.exit(1);
            }
            langConverter.put(key_word[0], key_word[1]);
        }
        reader.close();
        return new ResourceBundle(langConverter, locale.getLanguage().equals("en"));
    }

    private final HashMap<String, String> converter;
    private final boolean isEng;

    public ResourceBundle(HashMap<String, String> converter, boolean isEng){
        this.isEng = isEng;
        this.converter = converter;
    }

    public String getString(String key){
        return isEng? converter.get(key) : unicodeDecode(converter.get(key));
    }

    public static void main(String[] args) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("language", new Locale("zh", "cn"));
        System.out.println(bundle.getString("rmPins"));
    }

    /**
     * �����԰������unicodeת��ΪString, Ӣ�Ĳ���Ҫת��
     * @param unicode Ҫת����unicode(�����Ͼ����ַ���)
     * @return ת�����String
     */
    private static String unicodeDecode(String unicode) {
        StringBuilder string = new StringBuilder();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int data = Integer.parseInt(hex[i], 16);
            string.append((char) data);
        }
        return string.toString();
    }
}
