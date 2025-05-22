package GUI;

import DAL.CartDAL;
import DAL.OrderDAL;
import DAL.ProductDAL;
import DTO.CartItem;
import DTO.Order;
import DTO.OrderItem;
import DTO.Product;
import GUI.ChatService;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * ChatbotAI – phiên bản đầy đủ, từng dòng rõ ràng.
 */
public class ChatbotAI extends JFrame {
    /* -------------- Field -------------- */
    private JPanel sidebar;
    private JPanel contentPanel;
    private JTextPane contentPane;
    private JDialog chatPopup;
    private JTextArea chatArea;
    private JTextField inputField;

    private final ProductDAL productDAL = new ProductDAL();
    private final CartDAL    cartDAL    = new CartDAL();
    private final OrderDAL   orderDAL   = new OrderDAL();
    private final ChatService chatService = new ChatService();

    private final int userId = 1; // tạm thời hard‑code

    /* -------------- Constructor -------------- */
    public ChatbotAI() {
        super("ChatbotAI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);

        /* —— Layout gốc —— */
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.setBackground(Color.WHITE);

        // sidebar
        sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(270, 0));
        sidebar.setBackground(new Color(0x1E1E1E));
        initSidebar();
        cp.add(sidebar, BorderLayout.WEST);

        // content
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new CompoundBorder(new LineBorder(Color.LIGHT_GRAY), new EmptyBorder(3, 3, 3, 3)));
        contentPane = new JTextPane();
        contentPane.setEditable(false);
        contentPanel.add(new JScrollPane(contentPane), BorderLayout.CENTER);
        cp.add(contentPanel, BorderLayout.CENTER);

        // font base
        StyledDocument doc = contentPane.getStyledDocument();
        SimpleAttributeSet base = new SimpleAttributeSet();
        StyleConstants.setFontFamily(base, "Segoe UI");
        StyleConstants.setFontSize(base, 16);
        StyleConstants.setLineSpacing(base, 0.3f);
        doc.setParagraphAttributes(0, doc.getLength(), base, false);

        /* —— Chat popup —— */
        initChatPopup();
        SwingUtilities.invokeLater(() -> {
            positionChatPopup();
            chatPopup.setVisible(true);
        });
        addComponentListener(new ComponentAdapter() {
            @Override public void componentMoved(ComponentEvent e)  { positionChatPopup(); }
            @Override public void componentResized(ComponentEvent e){ positionChatPopup(); }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /* ================== Sidebar ================== */
    private void initSidebar() {
        JLabel logo = new JLabel(new ImageIcon("src/main/java/Utils/Picture/logo veresa 256-tn.png"));
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        sidebar.add(logo, BorderLayout.NORTH);

        JPanel menu = new JPanel();
        menu.setOpaque(false);
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.add(Box.createVerticalStrut(50));

        String[] buttons = {
                "Thực đơn hôm nay",
                "Thông tin khuyến mãi",
                "Đặt món tự chọn",
                "Địa chỉ cửa hàng",
                "Giỏ hàng của tôi"
        };
        for (String txt : buttons) {
            JButton btn = new JButton(txt);
            styleMenuButton(btn);
            btn.addActionListener(e -> handleMenu(txt));
            menu.add(Box.createVerticalStrut(15));
            menu.add(btn);
        }
        sidebar.add(menu, BorderLayout.CENTER);

        JButton historyBtn = new JButton("Lịch sử đặt hàng");
        styleMenuButton(historyBtn);
        historyBtn.setBackground(new Color(0x00AA00));
        historyBtn.addActionListener(e -> showHistory());

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.add(Box.createVerticalStrut(20));
        bottom.add(historyBtn);
        bottom.add(Box.createVerticalStrut(20));
        sidebar.add(bottom, BorderLayout.SOUTH);
    }

    private void styleMenuButton(JButton b) {
        b.setFont(new Font("Segoe UI", Font.BOLD, 15));
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setPreferredSize(new Dimension(230, 40));
        b.setMaximumSize(new Dimension(230, 40));
        b.setBackground(new Color(0xFF6633));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
    }

    /* ================== Hiển thị menu ================== */
    private void showMenuWithCheckbox(List<Product> products) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        NumberFormat fmt = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        int count = 0;
        for (Product p : products) {
            if (count++ == 20) break; // tối đa 20 món
            JCheckBox cb = new JCheckBox(p.getName() + " (" + fmt.format(p.getPrice()) + ")");
            cb.putClientProperty("pid", p.getProductId());
            cb.putClientProperty("price", p.getPrice());
            panel.add(cb);
        }

        panel.add(Box.createVerticalStrut(10));
        JButton addBtn = new JButton("Thêm vào giỏ hàng");
        addBtn.addActionListener(e -> addSelectedToCart(panel));
        panel.add(addBtn);

        contentPanel.removeAll();
        contentPanel.add(new JScrollPane(panel), BorderLayout.CENTER);
        contentPanel.revalidate();
    }

    private void addSelectedToCart(JPanel panel) {
        try {
            int cartId = cartDAL.getOrCreateOpenCart(userId);
            for (Component comp : panel.getComponents()) {
                if (comp instanceof JCheckBox cb && cb.isSelected()) {
                    int pid = (int) cb.getClientProperty("pid");
                    BigDecimal price = (BigDecimal) cb.getClientProperty("price");
                    cartDAL.addItem(cartId, pid, 1, price);
                }
            }
            JOptionPane.showMessageDialog(this, "Đã thêm vào giỏ hàng!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /* ================== Xử lý menu ================== */
    private void handleMenu(String action) {
        StyledDocument doc = contentPane.getStyledDocument();
        try { doc.remove(0, doc.getLength()); } catch (BadLocationException ignored) {}

        SimpleAttributeSet bold = new SimpleAttributeSet();
        StyleConstants.setBold(bold, true);
        StyleConstants.setFontSize(bold, 18);
        SimpleAttributeSet normal = new SimpleAttributeSet();
        StyleConstants.setFontSize(normal, 16);
        StyleConstants.setLineSpacing(normal, 0.3f);

        switch (action) {
            case "Thực đơn hôm nay" -> showMenuWithCheckbox(productDAL.getTodayMenu());

            case "Thông tin khuyến mãi" -> {
                try {
                    doc.insertString(doc.getLength(), "Chương trình Khuyến mãi\n", bold);
                    String[] promos = {
                            "1. Giảm 20% tất cả các món chay từ 11:00–14:00\n",
                            "2. Mua 1 tặng 1 nước ép trái cây\n",
                            "3. Combo gia đình chỉ 199.000đ/người\n",
                            "4. Miễn phí giao hàng trong bán kính 5 km\n",
                            "5. Tặng bánh flan chay khi check‑in tại quán\n",
                            "6. Ưu đãi nhóm từ 4 khách trở lên\n",
                            "7. Voucher sinh nhật 100.000đ\n",
                            "8. Áp dụng cả trực tiếp và online\n",
                            "9. Thời gian: ngày 1–15 mỗi tháng\n",
                            "10. Số lượng có hạn, đặt ngay!\n"
                    };
                    for (String s : promos) doc.insertString(doc.getLength(), s, normal);
                } catch (BadLocationException e) { e.printStackTrace(); }
            }

            case "Đặt món tự chọn" -> showMenuWithCheckbox(productDAL.getActiveProducts(null));

            case "Địa chỉ cửa hàng" -> {
                try {
                    doc.insertString(doc.getLength(), "10 Chi nhánh toàn quốc\n", bold);
                    String[] addrs = {
                            "1. Hà Nội – 123 Trần Phú, Ba Đình\n",
                            "2. TP.HCM – 456 Nguyễn Huệ, Quận 1\n",
                            "3. Đà Nẵng – 78 Bạch Đằng, Hải Châu\n",
                            "4. Hải Phòng – 90 Lê Lợi, Ngô Quyền\n",
                            "5. Cần Thơ – 55 Võ Văn Kiệt, Ninh Kiều\n",
                            "6. Nha Trang – 22 Trần Phú, Nha Trang\n",
                            "7. Huế – 10 Hùng Vương, TP. Huế\n",
                            "8. Vũng Tàu – 88 Trương Công Định\n",
                            "9. Biên Hòa – 15 Đồng Khởi, Biên Hòa\n",
                            "10. Đà Lạt – 34 Phan Chu Trinh, Đà Lạt\n"
                    };
                    for (String a : addrs) doc.insertString(doc.getLength(), a, normal);
                } catch (BadLocationException e) { e.printStackTrace(); }
            }

            case "Giỏ hàng của tôi" -> {
                try {
                    int cartId = cartDAL.getOrCreateOpenCart(userId);
                    List<CartItem> items = cartDAL.getItems(cartId);

                    StringBuilder sb = new StringBuilder("Giỏ hàng của tôi\n");
                    BigDecimal total = BigDecimal.ZERO;
                    NumberFormat fmt = NumberFormat.getCurrencyInstance(new Locale("vi","VN"));

                    for (CartItem it : items) {
                        sb.append(it.toLine()).append('\n');
                        total = total.add(it.getPrice().multiply(BigDecimal.valueOf(it.getQuantity())));
                    }
                    sb.append("\nTổng cộng: ").append(fmt.format(total));
                    contentPane.setText(sb.toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void showHistory() {
        try {
            var orders = orderDAL.selectByUser(userId);
            StyledDocument doc = contentPane.getStyledDocument();
            doc.remove(0, doc.getLength());
            SimpleAttributeSet bold = new SimpleAttributeSet();
            StyleConstants.setBold(bold, true);
            StyleConstants.setFontSize(bold, 18);
            doc.insertString(0, "Lịch sử đặt hàng\n", bold);

            SimpleAttributeSet normal = new SimpleAttributeSet();
            StyleConstants.setFontSize(normal, 16);
            NumberFormat fmt = NumberFormat.getCurrencyInstance(new Locale("vi","VN"));

            for (var o : orders) {
                doc.insertString(doc.getLength(), "Đơn #" + o.orderId() + " (" + o.status() + ")  " + fmt.format(o.total()) + "\n", normal);
                for (var it : orderDAL.selectItems(o.orderId())) {
                    doc.insertString(doc.getLength(), "   • " + it.productName() + " x" + it.quantity() + "\n", normal);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initChatPopup() {
        chatPopup = new JDialog(this);
        chatPopup.setUndecorated(true);
        chatPopup.setSize(300, 500);

        JPanel phone = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 60, 60);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        phone.setBackground(Color.WHITE);
        phone.setBorder(new CompoundBorder(new LineBorder(new Color(0xFF6633), 3, true), new EmptyBorder(2, 2, 2, 2)));

        JLabel header = new JLabel("ChatbotAI", SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(new Color(0xFF6633));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.setPreferredSize(new Dimension(300, 50));
        phone.add(header, BorderLayout.NORTH);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        phone.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        inputField = new JTextField("Tôi là ALice sẽ giúp bạn trả lời về các món ăn nhé !");
        inputField.setForeground(Color.GRAY);
        inputField.setPreferredSize(new Dimension(300, 40));
        inputField.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                if (inputField.getText().equals("Tôi là ALice sẽ giúp bạn trả lời về các món ăn nhé !")) {
                    inputField.setText("");
                    inputField.setForeground(Color.BLACK);
                }
            }
            @Override public void focusLost(FocusEvent e) {
                if (inputField.getText().trim().isEmpty()) {
                    inputField.setText("Tôi là ALice sẽ giúp bạn trả lời về các món ăn nhé !");
                    inputField.setForeground(Color.GRAY);
                }
            }
        });
        inputField.addActionListener(e -> {
            String p = inputField.getText().trim();
            if (p.isEmpty()) return;
            chatArea.append("Bạn: " + p + "\n");
            inputField.setText("");
            inputField.setForeground(Color.GRAY);
            inputField.setText("Tôi là ALice sẽ giúp bạn trả lời về các món ăn nhé !");
            chatArea.append("AI: " + chatService.ask(p) + "\n\n");
        });
        phone.add(inputField, BorderLayout.SOUTH);

        chatPopup.getContentPane().add(phone);
    }

    private void positionChatPopup() {
        if (!isShowing()) return;
        try {
            Point cp = contentPanel.getLocationOnScreen();
            int x = cp.x + contentPanel.getWidth() - chatPopup.getWidth() - 10;
            int y = cp.y + 50;
            chatPopup.setLocation(x, y);
        } catch (IllegalComponentStateException ignored) {}
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatbotAI::new);
    }
}
