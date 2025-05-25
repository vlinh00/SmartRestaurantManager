/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

/**
 *
 * @author lelin
 */
import javax.swing.*;
import java.awt.*;
import static MAIN.Main.changLNF;
import Utils.Session;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class MainDashboard extends JFrame {

    private JPanel contentPanel;
    
    JLabel btnDoiMatKhau;
    JPanel pnTitle, pnMenuLeft, pnCard, pnBanHang, pnKhuyenMai, pnNhapHang, pnSanPham, pnNhanVien, pnKhachHang, pnThongKe;
    
    ProductForm productForm;
    SellForm sellForm;
    PromotionForm promotionForm;
//    PnQuanLyBanHangGUI banHangPanel;
//    PnQuanLyKhuyenMaiGUI khuyenMaiPanel;
//    PnQuanLyNhapHangGUI nhapHangPanel;
//    PnQuanLySanPhamGUI sanPhamPanel;
    PnQuanLyNhanVienGUI nhanVienPanel;
    PnQuanLyKhachHangGUI khachHangPanel;
//    PnQuanLyThongKeGUI thongKePanel;

    JLabel btnClose, btnMinimize, lblBanHang, lblKhuyenMai, lblNhapHang, lblSanPham, lblNhanVien, lblKhachHang, lblThongKe;
    final Color clLeftItem = new Color(63, 74, 89);
    final Color clLeftItemHover = new Color(72, 88, 107);
    final Color clLeftItemSelected = new Color(51, 202, 187);
    final Color colorGreen = new Color(67, 205, 128);
    ArrayList<JLabel> listMenuLeft;
    CardLayout cardMenuLeftGroup = new CardLayout();

    public MainDashboard() {
        changLNF("Windows");
        this.setTitle("Phần mềm quản lý Quán ăn Chay");
        this.setSize(1280, 1000);
        Image icon = Toolkit.getDefaultToolkit().getImage("images/ManagerUI/icon-app.jpg");
        this.setIconImage(icon);
        initComponents();
        addEvents();
    }
    
    public void showWindow() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    private void initComponents(){
        int width = this.getWidth();
        int height = this.getHeight();

        Container con = getContentPane();

        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BorderLayout());

        /*
        ============================================================
                                TITLE BAR
        ============================================================
         */
        pnTitle = new JPanel(null);
        pnTitle.setPreferredSize(new Dimension(width, 46));
        pnTitle.setBackground(colorGreen);

        btnDoiMatKhau = new JLabel(new ImageIcon("images/ManagerUI/icons8_gear_46px.png"));
        btnDoiMatKhau.setToolTipText("Đổi mật khẩu");
        btnDoiMatKhau.setBounds(0, 0, 46, 46);
        btnDoiMatKhau.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        pnTitle.add(btnDoiMatKhau);

//        JLabel lblTitleText = new JLabel(new ImageIcon("images/ManagerUI/title-text.png")));
//        lblTitleText.setBounds(width / 2 - 428 / 2, 3, 428, 38);
//        pnTitle.add(lblTitleText);

JLabel lblTitle = new JLabel("QUẢN LÝ QUÁN ĂN CHAY");
lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
lblTitle.setForeground(Color.WHITE); // Chữ trắng nổi bật trên nền xanh
//lblTitle.setBounds(550, 10, 300, 30); // (x, y, width, height) tùy chỉnh
lblTitle.setBounds(width / 2 - width / 8, 10, 300, 30);
pnTitle.add(lblTitle);

//        btnMinimize = new JLabel(new ImageIcon("images/ManagerUI/btn-minimize.png")));
//        btnMinimize.setBounds(width - 85, 5, 38, 35);
//        btnMinimize.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        pnTitle.add(btnMinimize);

//        btnClose = new JLabel(new ImageIcon("images/ManagerUI/btn-close.png")));
//        btnClose.setBounds(width - 40, 5, 35, 35);
//        btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        pnTitle.add(btnClose);

        pnMain.add(pnTitle, BorderLayout.NORTH);
        /*
        ============================================================
                                SIDE BAR MENU
        ============================================================
         */
        pnMenuLeft = new JPanel();
        pnMenuLeft.setPreferredSize(new Dimension(250, height - pnTitle.getHeight()));
        pnMenuLeft.setBackground(clLeftItem);
        pnMenuLeft.setLayout(new BoxLayout(pnMenuLeft, BoxLayout.Y_AXIS));

        JLabel lblAvatar = new JLabel(new ImageIcon("images/ManagerUI/avatar.jpg"), JLabel.CENTER);
        lblAvatar.setPreferredSize(new Dimension(250, 210));
        pnMenuLeft.add(lblAvatar);

        lblBanHang = new JLabel(new ImageIcon("images/ManagerUI/lblBanHang.png"));
        lblKhuyenMai = new JLabel(new ImageIcon("images/ManagerUI/lblKhuyenMai.png"));
        lblNhapHang = new JLabel(new ImageIcon("images/ManagerUI/lblNhapHang.png"));
        lblSanPham = new JLabel(new ImageIcon("images/ManagerUI/lblSanPham.png"));
        lblNhanVien = new JLabel(new ImageIcon("images/ManagerUI/lblNhanVien.png"));
        lblKhachHang = new JLabel(new ImageIcon("images/ManagerUI/lblKhachHang.png"));
        lblThongKe = new JLabel(new ImageIcon("images/ManagerUI/lblThongKe.png"));

        listMenuLeft = new ArrayList<>();
        listMenuLeft.add(lblBanHang);
        listMenuLeft.add(lblKhuyenMai);
        listMenuLeft.add(lblSanPham);
        listMenuLeft.add(lblNhanVien);
        listMenuLeft.add(lblKhachHang);
        listMenuLeft.add(lblNhapHang);
        listMenuLeft.add(lblThongKe);

        for (JLabel lbl : listMenuLeft) {
            lbl.setVisible(false);
            lbl.setPreferredSize(new Dimension(250, 65));
            lbl.setOpaque(true);
            lbl.setBackground(clLeftItem);
            lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            pnMenuLeft.add(lbl);
        }

        lblBanHang.setBackground(clLeftItemSelected);
        lblBanHang.setVisible(true);
        lblKhuyenMai.setVisible(true);

        pnMain.add(pnMenuLeft, BorderLayout.WEST);

        /*
        ============================================================
                                CARD PANEL           
        ============================================================
         */
        pnCard = new JPanel(cardMenuLeftGroup);

        pnBanHang = new JPanel();
        pnKhuyenMai = new JPanel();
        pnNhapHang = new JPanel();
        pnSanPham = new JPanel();
        pnNhanVien = new JPanel();
        pnKhachHang = new JPanel();
        pnThongKe = new JPanel();

        pnCard.add(pnBanHang, "1");
        pnCard.add(pnKhuyenMai, "2");
        pnCard.add(pnNhapHang, "3");
        pnCard.add(pnSanPham, "4");
        pnCard.add(pnNhanVien, "5");
        pnCard.add(pnKhachHang, "6");
        pnCard.add(pnThongKe, "7");

        //==========ADD PANEL BÁN HÀNG + KHUYẾN MÃI (Ko phân quyền)==========
        sellForm = new SellForm();
        pnBanHang.setLayout(new BorderLayout());
        pnBanHang.add(sellForm, BorderLayout.CENTER);
        
        productForm = new ProductForm();
        pnSanPham.setLayout(new BorderLayout());
        pnSanPham.add(productForm, BorderLayout.CENTER);
        lblSanPham.setVisible(true);

        promotionForm = new PromotionForm();
        pnKhuyenMai.setLayout(new BorderLayout());
        pnKhuyenMai.add(promotionForm, BorderLayout.CENTER);
        
        if (Session.role != null && Session.role.equals("admin"))
        {
            nhanVienPanel = new PnQuanLyNhanVienGUI();
            pnNhanVien.setLayout(new BorderLayout());
            pnNhanVien.add(nhanVienPanel, BorderLayout.CENTER);
            lblNhanVien.setVisible(true);
            
             khachHangPanel = new PnQuanLyKhachHangGUI();
            pnKhachHang.setLayout(new BorderLayout());
            pnKhachHang.add(khachHangPanel, BorderLayout.CENTER);
            lblKhachHang.setVisible(true);
        }

        //======XỬ LÝ PHÂN QUYỀN=======
//        PhanQuyen quyen = PhanQuyenBUS.quyenTK;
//
//        if (quyen.getNhapHang() == 1) {
//            nhapHangPanel = new PnQuanLyNhapHangGUI();
//            pnNhapHang.setLayout(new BorderLayout());
//            pnNhapHang.add(nhapHangPanel, BorderLayout.CENTER);
//            lblNhapHang.setVisible(true);
//        }

//        if (quyen.getQlSanPham() == 1) {
//            sanPhamPanel = new PnQuanLySanPhamGUI();
//            pnSanPham.setLayout(new BorderLayout());
//            pnSanPham.add(sanPhamPanel, BorderLayout.CENTER);
//            lblSanPham.setVisible(true);
//        }
//
//        if (quyen.getQlNhanVien() == 1) {
//            nhanVienPanel = new PnQuanLyNhanVienGUI();
//            pnNhanVien.setLayout(new BorderLayout());
//            pnNhanVien.add(nhanVienPanel, BorderLayout.CENTER);
//            lblNhanVien.setVisible(true);
//        }
//
//        if (quyen.getQlKhachHang() == 1) {
//            khachHangPanel = new PnQuanLyKhachHangGUI();
//            pnKhachHang.setLayout(new BorderLayout());
//            pnKhachHang.add(khachHangPanel, BorderLayout.CENTER);
//            lblKhachHang.setVisible(true);
//        }
//
//        if (quyen.getThongKe() == 1) {
//            thongKePanel = new PnQuanLyThongKeGUI();
//            pnThongKe.setLayout(new BorderLayout());
//            pnThongKe.add(thongKePanel, BorderLayout.CENTER);
//            lblThongKe.setVisible(true);
//        }
        pnMain.add(pnCard);
        /*
        ============================================================
                                CARD PANEL           
        ============================================================
         */
        con.add(pnMain);
    }

    int xMouse, yMouse;
private void addEvents() {
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                moverFrame(e.getXOnScreen(), e.getYOnScreen());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                xMouse = e.getX();
                yMouse = e.getY();
            }
        });

        btnDoiMatKhau.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new DlgDoiMatKhau().setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnDoiMatKhau.setOpaque(true);
                btnDoiMatKhau.setBackground(clLeftItemHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnDoiMatKhau.setOpaque(false);
                btnDoiMatKhau.setBackground(new Color(0, 0, 0, 0));
            }
        });

//        btnMinimize.addMouseListener(new MouseListener() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                thuNhoFrame();
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                //btnMinimize.setIcon(new ImageIcon("image/ManagerUI/btn-minimize--hover.png"));
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//                //btnMinimize.setIcon(new ImageIcon("image/ManagerUI/btn-minimize.png"));
//            }
//        });

//        btnClose.addMouseListener(new MouseListener() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                //thoatChuongTrinh();
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                //btnClose.setIcon(new ImageIcon("image/ManagerUI/btn-close--hover.png"));
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//               //btnClose.setIcon(new ImageIcon("image/ManagerUI/btn-close.png"));
//            }
//        });

        for (JLabel lbl : listMenuLeft) {
            lbl.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    for (JLabel lblDisable : listMenuLeft) {
                        lblDisable.setBackground(clLeftItem);
                    }
                    lbl.setBackground(clLeftItemSelected);

                    // Xử lý lật trang theo menu
                    String cardName = "";
                    if (lbl == lblBanHang) {
                        cardName = "1";
                    } else if (lbl == lblKhuyenMai) {
                        cardName = "2";
                    } else if (lbl == lblNhapHang) {
                        cardName = "3";
                    } else if (lbl == lblSanPham) {
                        cardName = "4";
                    } else if (lbl == lblNhanVien) {
                        cardName = "5";
                    } else if (lbl == lblKhachHang) {
                        cardName = "6";
                    } else {
                        cardName = "7";
                    }
                    cardMenuLeftGroup.show(pnCard, cardName);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (lbl.getBackground().equals(clLeftItem)) {
                        lbl.setBackground(clLeftItemHover);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (lbl.getBackground().equals(clLeftItemHover)) {
                        lbl.setBackground(clLeftItem);
                    }
                }
            });
        }

    }    

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(200, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(60, 80, 100));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        return button;
    }

    private void showPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void moverFrame(int x, int y) {
        this.setLocation(x - xMouse, y - yMouse);
    }

    private void thuNhoFrame() {
        this.setState(Frame.ICONIFIED);
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainDashboard().setVisible(true));
    }
}

