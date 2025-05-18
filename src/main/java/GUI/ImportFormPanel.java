/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

/**
 *
 * @author Asus VivoBook
 */
import bll.ImportService;
import dto.ImportItemDTO;
import dto.ProductDTO;
import dal.InventoryDAO;
import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class ImportFormPanel extends JPanel {
    private JTable tblProducts;
    private JTable tblImportQueue;
    private JTextField txtSearch;
    private JLabel lblName, lblCategory, lblPrice, lblDescription;
    private JTextField txtQuantity, txtImportPrice;
    private DefaultTableModel modelProducts;
    private DefaultTableModel modelQueue;
    private List<ImportItemDTO> importItems = new ArrayList<>();
    private ImportService importService = new ImportService();

    public ImportFormPanel() {
        setLayout(new BorderLayout());

        // Left Panel: Product Table and Search
        JPanel leftPanel = new JPanel(new BorderLayout());
        txtSearch = new JTextField();
        JButton btnSearch = new JButton("Tìm kiếm");
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(txtSearch, BorderLayout.CENTER);
        searchPanel.add(btnSearch, BorderLayout.EAST);
        leftPanel.add(searchPanel, BorderLayout.NORTH);

        modelProducts = new DefaultTableModel(new Object[]{"ID", "Tên", "Giá", "Danh mục"}, 0);
        tblProducts = new JTable(modelProducts);
        leftPanel.add(new JScrollPane(tblProducts), BorderLayout.CENTER);

        add(leftPanel, BorderLayout.WEST);

        // Right Panel
        JPanel rightPanel = new JPanel(new BorderLayout());

        JPanel detailPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        lblName = new JLabel();
        lblCategory = new JLabel();
        lblPrice = new JLabel();
        lblDescription = new JLabel();
        txtQuantity = new JTextField();
        txtImportPrice = new JTextField();

        detailPanel.add(new JLabel("Tên:")); detailPanel.add(lblName);
        detailPanel.add(new JLabel("Danh mục:")); detailPanel.add(lblCategory);
        detailPanel.add(new JLabel("Giá bán:")); detailPanel.add(lblPrice);
        detailPanel.add(new JLabel("Mô tả:")); detailPanel.add(lblDescription);
        detailPanel.add(new JLabel("Số lượng nhập:")); detailPanel.add(txtQuantity);
        detailPanel.add(new JLabel("Đơn giá nhập:")); detailPanel.add(txtImportPrice);

        JButton btnAdd = new JButton("Thêm vào bảng chờ");
        rightPanel.add(detailPanel, BorderLayout.NORTH);
        rightPanel.add(btnAdd, BorderLayout.CENTER);

        // Import Queue Table
        modelQueue = new DefaultTableModel(new Object[]{"Tên", "Số lượng", "Đơn giá", "Thành tiền"}, 0);
        tblImportQueue = new JTable(modelQueue);
        rightPanel.add(new JScrollPane(tblImportQueue), BorderLayout.SOUTH);

        add(rightPanel, BorderLayout.CENTER);

        JButton btnConfirm = new JButton("Xác nhận nhập hàng");
        add(btnConfirm, BorderLayout.SOUTH);

        // Load products
        loadProducts("");

        // Events
        btnSearch.addActionListener(e -> loadProducts(txtSearch.getText()));

        tblProducts.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tblProducts.getSelectedRow();
                if (row != -1) {
                    lblName.setText(modelProducts.getValueAt(row, 1).toString());
                    lblPrice.setText(modelProducts.getValueAt(row, 2).toString());
                    lblCategory.setText(modelProducts.getValueAt(row, 3).toString());
                    loadDescription((int) modelProducts.getValueAt(row, 0));
                }
            }
        });

        btnAdd.addActionListener(e -> {
            int row = tblProducts.getSelectedRow();
            if (row == -1) return;
            try {
                int quantity = Integer.parseInt(txtQuantity.getText());
                double price = Double.parseDouble(txtImportPrice.getText());
                int id = (int) modelProducts.getValueAt(row, 0);
                String name = (String) modelProducts.getValueAt(row, 1);
                double salePrice = Double.parseDouble(modelProducts.getValueAt(row, 2).toString());
                String category = (String) modelProducts.getValueAt(row, 3);

                ProductDTO product = new ProductDTO(id, name, lblDescription.getText(), salePrice, category, true);
                ImportItemDTO item = new ImportItemDTO(product, quantity, price);
                importItems.add(item);
                modelQueue.addRow(new Object[]{name, quantity, price, item.getTotalPrice()});

                txtQuantity.setText("");
                txtImportPrice.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Nhập số hợp lệ");
            }
        });

        btnConfirm.addActionListener(e -> {
            try {
                importService.processImport(importItems);
                JOptionPane.showMessageDialog(this, "Đã nhập hàng thành công!");
                importItems.clear();
                modelQueue.setRowCount(0);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi nhập hàng");
            }
        });
    }

    private void loadProducts(String keyword) {
        modelProducts.setRowCount(0);
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM products WHERE is_active = 1 AND (name LIKE ? OR category LIKE ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, "%" + keyword + "%");
                stmt.setString(2, "%" + keyword + "%");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    modelProducts.addRow(new Object[]{
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("category")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadDescription(int productId) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT description FROM products WHERE product_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, productId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    lblDescription.setText(rs.getString("description"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
