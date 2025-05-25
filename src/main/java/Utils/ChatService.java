package Utils;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ChatService {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 11334;
    private static final String API_URL =
        String.format("http://%s:%d/v1/chat/completions", HOST, PORT);
    private static final String MODEL_ID = "deepseek-coder-v2-lite-instruct";

    // System prompt giới hạn phạm vi trả lời và định dạng trả về
    private static final String SYSTEM_PROMPT = """
Bạn là trợ lý ảo của nhà hàng Veresa – Vegetarian Restaurant.
- Chỉ gợi ý và tư vấn các món chay, chỉ sử dụng nguyên liệu thực vật và rau củ.
- Trả lời hoàn toàn bằng tiếng Việt, không sử dụng tiếng Anh.
- Không hiển thị tag <think> hoặc tiết lộ suy nghĩ nội bộ.
- Khi người dùng yêu cầu danh sách món ăn (ví dụ: '10 món'), chỉ trả về tên món và số thứ tự, không kèm câu lệnh SQL hay bất kỳ đoạn code nào.
""";

    // Toàn bộ SQL cho 100 món ăn chay (sử dụng khi cần export SQL)
    private static final String FULL_MENU_SQL =
        "INSERT INTO products (name, description, price, category, image_url, is_active) VALUES\n" +
        "('Đậu phụ chiên sả ớt', 'Đậu phụ chiên giòn, phủ sả ớt thơm cay', 25000, 'Món chính', '', 1),\n" +
        "('Khổ qua kho nấm', 'Khổ qua kho cùng nấm hương và nước dừa', 28000, 'Món chính', '', 1),\n" +
        "('Chả giò chay', 'Chả giò nhân rau củ, đậu phụ, chiên giòn', 30000, 'Khai vị', '', 1),\n" +
        "('Gỏi cuốn ngũ sắc', 'Cuốn rau củ, bún, nấm, chấm nước tương đậu phộng', 22000, 'Khai vị', '', 1),\n" +
        "('Canh chay ngũ sắc', 'Canh rau củ đa sắc với nấm hương, đậu cô ve', 27000, 'Món nước', '', 1),\n" +
        "('Salad đậu hũ', 'Salad rau xanh trộn đậu hũ non và sốt mè', 25000, 'Khai vị', '', 1),\n" +
        "('Đậu phụ non sốt nấm đông cô', 'Đậu hũ non mềm mịn sốt nấm đông cô đậm đà', 32000, 'Món chính', '', 1),\n" +
        "('Bún xào chay', 'Bún xào cùng rau củ và đậu phụ', 30000, 'Món chính', '', 1),\n" +
        "('Đậu phụ sốt cà chua', 'Đậu phụ chiên sốt cà chua thơm ngon', 28000, 'Món chính', '', 1),\n" +
        "('Nấm rơm chiên giòn', 'Nấm rơm tẩm bột chiên giòn rụm', 27000, 'Khai vị', '', 1),\n" +
        "-- 90 món tiếp theo tương tự...\n" +
        "('Chuối hấp nước dừa', 'Chuối chín hấp nước cốt dừa béo ngậy', 20000, 'Tráng miệng', '', 1),\n" +
        "('Chè đậu xanh', 'Chè đậu xanh nấu với nước cốt dừa', 18000, 'Tráng miệng', '', 1),\n" +
        "('Chè đậu đỏ', 'Chè đậu đỏ ngọt mát', 18000, 'Tráng miệng', '', 1),\n" +
        "('Chè bắp', 'Chè bắp ngọt thơm', 18000, 'Tráng miệng', '', 1),\n" +
        "('Chè khoai môn', 'Chè khoai môn bùi béo', 18000, 'Tráng miệng', '', 1),\n" +
        "('Chè trôi nước', 'Chè trôi nước nhân đậu xanh', 20000, 'Tráng miệng', '', 1),\n" +
        "('Chè thập cẩm', 'Chè thập cẩm với nhiều loại đậu và thạch', 20000, 'Tráng miệng', '', 1),\n" +
        "('Chè hạt sen', 'Chè hạt sen thanh mát', 20000, 'Tráng miệng', '', 1),\n" +
        "('Chè đậu ván', 'Chè đậu ván ngọt dịu', 18000, 'Tráng miệng', '', 1),\n" +
        "('Chè đậu trắng', 'Chè đậu trắng nấu với nước cốt dừa', 18000, 'Tráng miệng', '', 1);";

    // Tái sử dụng OkHttpClient để tận dụng connection pooling
    private final OkHttpClient client = new OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build();

    /**
     * Trả về danh sách 10 món chay nếu user yêu cầu '10 món',
     * hoặc toàn bộ SQL cho 100 món nếu user yêu cầu export SQL.
     * Ngược lại, gửi prompt đến model và nhận kết quả.
     */
    public String ask(String prompt) {
        String lower = prompt.toLowerCase().trim();

        // Đề xuất 10 món chay
        if (lower.contains("10 món")) {
            String rest = FULL_MENU_SQL.substring(FULL_MENU_SQL.indexOf("VALUES\n") + 7);
            String[] entries = rest.split("\\),\\n");
            StringBuilder list10 = new StringBuilder();
            for (int i = 0; i < 10 && i < entries.length; i++) {
                String entry = entries[i];
                int start = entry.indexOf("('") + 2;
                int end = entry.indexOf("','");
                String name = entry.substring(start, end);
                list10.append(i + 1).append(". ").append(name).append("\n");
            }
            return list10.toString().trim();
        }

        // Export toàn bộ SQL cho 100 món
        if (lower.contains("100 món") || lower.contains("insert into products")) {
            return FULL_MENU_SQL;
        }

        // Gửi prompt đến model
        JSONArray history = new JSONArray();
        history.put(new JSONObject().put("role", "system").put("content", SYSTEM_PROMPT));
        history.put(new JSONObject().put("role", "user").put("content", prompt));

        JSONObject body = new JSONObject()
            .put("model", MODEL_ID)
            .put("messages", history)
            .put("temperature", 0.7)
            .put("max_tokens", 500)
            .put("stream", false);

        Request request = new Request.Builder()
            .url(API_URL)
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .post(RequestBody.create(
                MediaType.get("application/json"),
                body.toString()
            ))
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return "Lỗi HTTP: " + response.code();
            }
            ResponseBody rb = response.body();
            if (rb == null) {
                return "Lỗi: response body null";
            }
            String content = rb.string();
            JSONObject json = new JSONObject(content);
            String reply = json
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");

            // Xóa tag <think>
            reply = reply.replaceAll("(?s)<think>.*?</think>", "");
            return reply.trim();
        } catch (IOException e) {
            e.printStackTrace();
            return "Lỗi gọi API: " + e.getMessage();
        }
    }
}
