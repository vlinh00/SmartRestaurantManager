/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author lelin
 */
public class ProductForm extends JPanel{
    public ProductForm() {
        setLayout(new BorderLayout());

        // Title
        JLabel lblTitle = new JLabel("QUẢN LÝ SẢN PHẨM", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        // Center content
        JPanel panelCenter = new JPanel(new BorderLayout());

        // Form nhập liệu
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"Mã SP:", "Tên SP:", "Loại:", "Số lượng:", "Đơn vị tính:", "Đơn giá:"};
        JTextField[] textFields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            formPanel.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            textFields[i] = new JTextField(20);
            formPanel.add(textFields[i], gbc);
        }

        // ComboBox loại
        String[] loaiSP = {"0 - Chọn loại", "1 - Pizza", "2 - Nước uống"};
        JComboBox<String> cboLoai = new JComboBox<>(loaiSP);
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(cboLoai, gbc);

        // Hình ảnh sản phẩm
        JPanel imagePanel = new JPanel(new BorderLayout());
        JLabel lblImage = new JLabel("default.png", JLabel.CENTER);
        lblImage.setPreferredSize(new Dimension(120, 120));
        lblImage.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JButton btnChonAnh = new JButton("Chọn ảnh");

        imagePanel.add(lblImage, BorderLayout.CENTER);
        imagePanel.add(btnChonAnh, BorderLayout.SOUTH);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 6;
        formPanel.add(imagePanel, gbc);

        panelCenter.add(formPanel, BorderLayout.NORTH);

        // Tìm kiếm
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Từ khóa tìm:"));
        JTextField txtSearch = new JTextField(20);
        searchPanel.add(txtSearch);
        JButton btnTim = new JButton("Tìm kiếm");
        searchPanel.add(btnTim);
        panelCenter.add(searchPanel, BorderLayout.CENTER);

        // Bảng sản phẩm
        String[] columnNames = {"Mã SP", "Tên SP", "Loại SP", "Đơn giá", "Số lượng", "Đơn vị tính"};
        Object[][] data = {}; // Bạn có thể load dữ liệu sau
        JTable table = new JTable(data, columnNames);
        JScrollPane tableScroll = new JScrollPane(table);
        panelCenter.add(tableScroll, BorderLayout.SOUTH);

        add(panelCenter, BorderLayout.CENTER);

        // Nút chức năng
        JPanel buttonPanel = new JPanel();
        JButton btnThem = new JButton("Thêm");
        JButton btnLuu = new JButton("Lưu");
        JButton btnXoa = new JButton("Xoá");
        JButton btnXuat = new JButton("Xuất");
        JButton btnNhap = new JButton("Nhập");

        buttonPanel.add(btnThem);
        buttonPanel.add(btnLuu);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnXuat);
        buttonPanel.add(btnNhap);

        add(buttonPanel, BorderLayout.SOUTH);
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Phần mềm quản lý cửa hàng pizza");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new ProductForm());
        frame.setVisible(true);
    }
}
