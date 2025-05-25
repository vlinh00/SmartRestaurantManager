package Utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class DBConfigLoader {
    private static final Properties props = new Properties();

    static {
        try (InputStream is = new FileInputStream("src/main/java/Utils/dbconfig.txt")) {
            props.load(is);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi đọc file dbconfig.txt", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
