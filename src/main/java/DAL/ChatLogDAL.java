package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Lớp DAL chịu trách nhiệm lưu chat log vào bảng chat_logs.
 */
public class ChatLogDAL {

    /**
     * Lưu một bản ghi chat (userPrompt, botResponse) vào CSDL.
     */
    public void saveChatLog(String userPrompt, String botResponse) {
        String sql = "INSERT INTO chat_logs (user_prompt, bot_response) VALUES (?, ?)";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userPrompt);
            stmt.setString(2, botResponse);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
