package GUI;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class ChatService {
    private static final String API_KEY = "sk-3b9d01dda53c4d91bf6346ceab734e29"; // Thay bằng key thật bạn sẽ cung cấp
    private static final String API_URL = "https://api.deepseek.com/v1/chat/completions"; // Ví dụ, thay đúng endpoint

    public String ask(String prompt) {
        try {
            OkHttpClient client = new OkHttpClient();

            // Bắt buộc model trả lời đúng yêu cầu
            String strictPrompt = """
                Trả lời thật ngắn gọn. Chỉ trả lời tên món ăn chay ở Việt Nam, không nói thêm gì cả. Nếu câu hỏi không liên quan đến món ăn chay, chỉ trả về 'Chỉ trả lời món ăn chay'.
                Câu hỏi: """ + prompt;

            JSONArray messages = new JSONArray()
                    .put(new JSONObject().put("role", "system").put("content", "Bạn là chuyên gia món ăn chay."))
                    .put(new JSONObject().put("role", "user").put("content", strictPrompt));

            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "deepseek-chat"); // hoặc model phù hợp DeepSeek cung cấp
            requestBody.put("messages", messages);
            requestBody.put("max_tokens", 100);

            Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(requestBody.toString(), MediaType.get("application/json")))
                .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) return "Lỗi gọi API: " + response.code();
                JSONObject resJson = new JSONObject(response.body().string());
                return resJson.getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content").trim();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Lỗi gọi API.";
        }
    }
}
