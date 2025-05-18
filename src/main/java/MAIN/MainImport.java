/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MAIN;

/**
 *
 * @author Asus VivoBook
 */
import javax.swing.*;
import gui.ImportFormPanel;

public class MainImport {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Form Nhập Hàng - Nhà Hàng Chay");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 600);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new ImportFormPanel());
            frame.setVisible(true);
        });
    }
}
