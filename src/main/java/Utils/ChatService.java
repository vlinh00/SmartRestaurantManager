package Utils;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.concurrent.TimeUnit;

public class ChatService {
    private static final String HOST = "127.0.0.1"; // Đổi sang localhost
    private static final int PORT = 11334;           // Cổng mới bạn đã cấu hình
    private static final String API_URL = String.format("http://%s:%d/v1/chat/completions", HOST, PORT);
    private static final String MODEL_ID = "deepseek-r1-distill-qwen-7b:2";

    public String ask(String prompt) {
        OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

        JSONArray messages = new JSONArray()
            .put(new JSONObject().put("role", "system").put("content", "Bạn là một trợ lý chuyên gợi ý món ăn chay."))
            .put(new JSONObject().put("role", "user").put("content", prompt));

        JSONObject body = new JSONObject();
        body.put("model", MODEL_ID);
        body.put("messages", messages);
        body.put("temperature", 0.7);
        body.put("max_tokens", 100);
        body.put("stream", false);

        Request request = new Request.Builder()
            .url(API_URL)
            .addHeader("Content-Type", "application/json")
            .post(RequestBody.create(body.toString(), MediaType.get("application/json")))
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return "Lỗi HTTP: " + response.code();
            }
            String jsonStr = response.body().string();
            JSONObject json = new JSONObject(jsonStr);
            return json
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content")
                .trim();
        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi gọi API: " + e.getMessage();
        }
    }
}