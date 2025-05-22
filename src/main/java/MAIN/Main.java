
package MAIN;

import GUI.LoginForm;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginForm().setVisible(true);  // ✅ Hiển thị form đăng nhập trước
        });
    }
}
