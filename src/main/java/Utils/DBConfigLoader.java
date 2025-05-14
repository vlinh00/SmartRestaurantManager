/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import DTO.DBConfig;
import com.google.gson.Gson;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author lelin
 */
public class DBConfigLoader {
    public static DBConfig loadConfig() {
        try {
            InputStream input = DBConfigLoader.class.getClassLoader().getResourceAsStream("dbconfig.json");
            if (input == null) {
                System.err.println("Không tìm thấy file dbconfig.json");
                return null;
            }

            Gson gson = new Gson();
            return gson.fromJson(new InputStreamReader(input), DBConfig.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
