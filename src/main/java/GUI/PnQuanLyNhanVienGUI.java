package GUI;

import BLL.EmployeeService;
import BLL.UserService;
import DTO.Employee;
import MAIN.Main;
import Utils.ExcelHelper;
import Utils.ImagePanel;
import Utils.MyDialog;
import Utils.MyTable;
import Utils.TransparentPanel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PnQuanLyNhanVienGUI extends JPanel {

    public PnQuanLyNhanVienGUI() {
        Main.changLNF("Windows");
        addControlsNhanVien();
        addEventsNhanVien();
        addEventsPhanQuyen();
    }

    //private PhanQuyenBUS phanQuyenBUS = new PhanQuyenBUS();
    private EmployeeService employeeService = new EmployeeService();
    private UserService userService = new UserService();

    JLabel lblTabbedNhanVien, lblTabbedQuyen;
    final ImageIcon tabbedSelected = new ImageIcon("images/ManagerUI/tabbed-btn--selected.png");
    final ImageIcon tabbedDefault = new ImageIcon("images/ManagerUI/tabbed-btn.png");
    final Color colorPanel = new Color(247, 247, 247);
    CardLayout cardNhanVienGroup = new CardLayout();
    JPanel pnCardTabNhanVien;
    JTextField txtMaNV, txtTen, txtRole, txtChucVu, txtTimNV;
    JComboBox<String> cmbGioiTinh;
    MyTable tblNhanVien;
    DefaultTableModel dtmNhanVien;
    JButton btnReset, btnThemNV, btnSuaNV, btnXoaNV, btnTimNV, btnCapTaiKhoan, btnResetMatKhau, btnXoaTaiKhoan, btnXuatExcel, btnNhapExcel;

    private void addControlsNhanVien() {
        this.setLayout(new BorderLayout());
        this.setBackground(colorPanel);
        int w = 1030;
        int h = 844;
        /*
        =========================================================================
                                    PANEL TABBED
        =========================================================================
         */
        JPanel pnTop = new TransparentPanel();
        //<editor-fold defaultstate="collapsed" desc="Panel Tab Nhân viên & Quyền">
        Font font = new Font("", Font.PLAIN, 20);
        pnTop.setPreferredSize(new Dimension(w, 41));
        pnTop.setLayout(null);
        pnTop.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.GRAY));

        lblTabbedNhanVien = new JLabel("Nhân viên");
        lblTabbedNhanVien.setHorizontalTextPosition(JLabel.CENTER);
        lblTabbedNhanVien.setVerticalTextPosition(JLabel.CENTER);
        lblTabbedNhanVien.setIcon(tabbedSelected);
        lblTabbedNhanVien.setBounds(2, 2, 140, 37);
        lblTabbedNhanVien.setFont(font);
        lblTabbedNhanVien.setForeground(Color.white);
        lblTabbedNhanVien.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        lblTabbedQuyen = new JLabel("Quyền");
        lblTabbedQuyen.setHorizontalTextPosition(JLabel.CENTER);
        lblTabbedQuyen.setVerticalTextPosition(JLabel.CENTER);
        lblTabbedQuyen.setIcon(tabbedDefault);
        lblTabbedQuyen.setBounds(143, 2, 140, 37);
        lblTabbedQuyen.setFont(font);
        lblTabbedQuyen.setForeground(Color.white);
        lblTabbedQuyen.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        pnTop.add(lblTabbedNhanVien);
        pnTop.add(lblTabbedQuyen);
        //</editor-fold>
        this.add(pnTop, BorderLayout.NORTH);

        /*
        =========================================================================
                                    PANEL NHÂN VIÊN
        =========================================================================
         */
        JPanel pnNhanVien = new TransparentPanel();
        pnNhanVien.setLayout(new BoxLayout(pnNhanVien, BoxLayout.Y_AXIS));

        JPanel pnTopNV = new TransparentPanel();
        pnTopNV.setLayout(new BoxLayout(pnTopNV, BoxLayout.Y_AXIS));

        JPanel pnTitle = new TransparentPanel();
        JLabel lblTitle = new JLabel("<html><h1>QUẢN LÝ NHÂN VIÊN</h1></html>");
        btnReset = new JButton(new ImageIcon("images/Refresh-icon.png"));
        btnReset.setPreferredSize(new Dimension(40, 40));
        pnTitle.add(lblTitle);
        pnTitle.add(btnReset);
        pnTopNV.add(pnTitle);
        pnTopNV.setBackground(Color.DARK_GRAY);
        //==========
        JPanel pnText = new TransparentPanel();
        pnText.setLayout(new BoxLayout(pnText, BoxLayout.Y_AXIS));

        txtMaNV = new JTextField(25);
        txtMaNV.setEditable(false);
        txtTen = new JTextField(25);
        txtRole = new JTextField(25);
        cmbGioiTinh = new JComboBox<>();
        txtChucVu = new JTextField(25);

        txtMaNV.setFont(font);
        txtTen.setFont(font);
        txtRole.setFont(font);
        cmbGioiTinh.setFont(font);
        txtChucVu.setFont(font);

        cmbGioiTinh.addItem("Chọn giới tính");
        cmbGioiTinh.addItem("Nam");
        cmbGioiTinh.addItem("Nữ");

        JLabel lblMa, lblHo, lblTen, lblGioiTinh, lblChucVu;

        lblMa = new JLabel("Mã Nhân viên");
        lblHo = new JLabel("Họ đệm");
        lblTen = new JLabel("Tên");
        lblGioiTinh = new JLabel("Giới tính");
        lblChucVu = new JLabel("Chức vụ");

        lblMa.setFont(font);
        lblHo.setFont(font);
        lblTen.setFont(font);
        lblGioiTinh.setFont(font);
        lblChucVu.setFont(font);

        JPanel pnMa = new TransparentPanel();
        pnMa.add(lblMa);
        pnMa.add(txtMaNV);
        pnText.add(pnMa);

        JPanel pnHo = new TransparentPanel();
        pnHo.add(lblHo);
        pnHo.add(txtTen);
        pnText.add(pnHo);

        JPanel pnTen = new TransparentPanel();
        pnTen.add(lblTen);
        pnTen.add(txtRole);
        pnText.add(pnTen);

        JPanel pnGioiTinh = new TransparentPanel();
        pnGioiTinh.add(lblGioiTinh);
        pnGioiTinh.add(cmbGioiTinh);
        pnText.add(pnGioiTinh);

        JPanel pnChucVu = new TransparentPanel();
        pnChucVu.add(lblChucVu);
        pnChucVu.add(txtChucVu);
        pnText.add(pnChucVu);

        Dimension lblSize = lblMa.getPreferredSize();
        lblMa.setPreferredSize(lblSize);
        lblHo.setPreferredSize(lblSize);
        lblTen.setPreferredSize(lblSize);
        lblGioiTinh.setPreferredSize(lblSize);
        lblChucVu.setPreferredSize(lblSize);
        cmbGioiTinh.setPreferredSize(txtChucVu.getPreferredSize());

        pnTopNV.add(pnText);

        //==========
        JPanel pnTimNV = new TransparentPanel();
        JLabel lblTim = new JLabel("Từ khoá tìm");
        lblTim.setFont(font);
        txtTimNV = new JTextField(25);
        txtTimNV.setFont(font);
        pnTimNV.add(lblTim);
        pnTimNV.add(txtTimNV);
        pnTopNV.add(pnTimNV);
        lblTim.setPreferredSize(lblSize);
        //==========
        JPanel pnButton = new TransparentPanel();

        btnThemNV = new JButton("Thêm");
        btnSuaNV = new JButton("Lưu");
        btnXoaNV = new JButton("Xoá");
        btnTimNV = new JButton("Tìm kiếm");
        btnXuatExcel = new JButton("Xuất");
        btnNhapExcel = new JButton("Nhập");

        Font fontButton = new Font("Tahoma", Font.PLAIN, 16);
        btnThemNV.setFont(fontButton);
        btnSuaNV.setFont(fontButton);
        btnXoaNV.setFont(fontButton);
        btnTimNV.setFont(fontButton);
        btnXuatExcel.setFont(fontButton);
        btnNhapExcel.setFont(fontButton);

        btnThemNV.setIcon(new ImageIcon("images/add-icon.png"));
        btnSuaNV.setIcon(new ImageIcon("images/Pencil-icon.png"));
        btnXoaNV.setIcon(new ImageIcon("images/delete-icon.png"));
        btnTimNV.setIcon(new ImageIcon("images/Search-icon.png"));
        btnXuatExcel.setIcon(new ImageIcon("images/excel-icon.png"));
        btnNhapExcel.setIcon(new ImageIcon("images/excel-icon.png"));

        pnButton.add(btnThemNV);
        pnButton.add(btnSuaNV);
        pnButton.add(btnXoaNV);
        pnButton.add(btnTimNV);
        pnButton.add(btnXuatExcel);
        pnButton.add(btnNhapExcel);

        Dimension btnSize = btnTimNV.getPreferredSize();
        btnThemNV.setPreferredSize(btnSize);
        btnSuaNV.setPreferredSize(btnSize);
        btnXoaNV.setPreferredSize(btnSize);
        btnTimNV.setPreferredSize(btnSize);
        btnXuatExcel.setPreferredSize(btnSize);
        btnNhapExcel.setPreferredSize(btnSize);

        JPanel pnButton2 = new TransparentPanel();
        btnCapTaiKhoan = new JButton("Cấp tài khoản");
        btnResetMatKhau = new JButton("Mật khẩu/Quyền");
        btnXoaTaiKhoan = new JButton("Khoá tài khoản");
        btnCapTaiKhoan.setIcon(new ImageIcon("images/icons8_man_with_key_32px.png"));
        btnResetMatKhau.setIcon(new ImageIcon("images/icons8_password_reset_32px.png"));
        btnXoaTaiKhoan.setIcon(new ImageIcon("images/icons8_denied_32px.png"));
        btnCapTaiKhoan.setFont(fontButton);
        btnResetMatKhau.setFont(fontButton);
        btnXoaTaiKhoan.setFont(fontButton);
        pnButton2.add(btnCapTaiKhoan);
        pnButton2.add(btnResetMatKhau);
        pnButton2.add(btnXoaTaiKhoan);

        pnNhanVien.add(pnTopNV);
        pnNhanVien.add(pnButton);
        pnNhanVien.add(pnButton2);
        //===================TABLE NHÂN VIÊN=====================
        JPanel pnTableNhanVien = new TransparentPanel();
        pnTableNhanVien.setLayout(new BorderLayout());
        dtmNhanVien = new DefaultTableModel();
        dtmNhanVien.addColumn("Mã NV");
        dtmNhanVien.addColumn("Họ tên");
        dtmNhanVien.addColumn("Giới tính");
        dtmNhanVien.addColumn("Chức vụ");
        dtmNhanVien.addColumn("Tài khoản");
        dtmNhanVien.addColumn("Quyền");
        tblNhanVien = new MyTable(dtmNhanVien);
        JScrollPane scrTblNhanVien = new JScrollPane(tblNhanVien);
        pnTableNhanVien.add(scrTblNhanVien, BorderLayout.CENTER);
        pnNhanVien.add(scrTblNhanVien);
        /*
        =========================================================================
                                    PANEL QUYỀN
        =========================================================================
         */
        JPanel pnPhanQuyen = new TransparentPanel();
        pnPhanQuyen.setLayout(new BoxLayout(pnPhanQuyen, BoxLayout.Y_AXIS));

        JPanel pnTitlePhanQuyen = new TransparentPanel();
        JLabel lblTitlePhanQuyen = new JLabel("<html><h1>Quản lý phân quyền</h1></html>");
        pnTitlePhanQuyen.add(lblTitlePhanQuyen);
        pnPhanQuyen.add(pnTitlePhanQuyen);

        JPanel pnCmbQuyen = new TransparentPanel();
        JLabel lblCmbQuyen = new JLabel("<html><b>Nhóm quyền:</b></html>");
        lblCmbQuyen.setFont(font);
        cmbQuyen = new JComboBox<String>();
        cmbQuyen.setFont(font);
        pnCmbQuyen.add(lblCmbQuyen);
        pnCmbQuyen.add(cmbQuyen);
        pnPhanQuyen.add(pnCmbQuyen);

        JPanel pnCheckNhapHang = new TransparentPanel();
        ckbNhapHang = new JCheckBox("Quản lý nhập hàng.");
        ckbNhapHang.setFont(font);
        pnCheckNhapHang.add(ckbNhapHang);
        pnPhanQuyen.add(pnCheckNhapHang);

        JPanel pnCheckQLSanPham = new TransparentPanel();
        ckbQLSanPham = new JCheckBox("Quản lý sản phẩm.");
        ckbQLSanPham.setFont(font);
        pnCheckQLSanPham.add(ckbQLSanPham);
        pnPhanQuyen.add(pnCheckQLSanPham);

        JPanel pnCheckQLNhanVien = new TransparentPanel();
        ckbQLNhanVien = new JCheckBox("Quản lý nhân viên.");
        ckbQLNhanVien.setFont(font);
        pnCheckQLNhanVien.add(ckbQLNhanVien);
        pnPhanQuyen.add(pnCheckQLNhanVien);

        JPanel pnCheckQLKhachHang = new TransparentPanel();
        ckbQLKhachHang = new JCheckBox("Quản lý khách hàng.");
        ckbQLKhachHang.setFont(font);
        pnCheckQLKhachHang.add(ckbQLKhachHang);
        pnPhanQuyen.add(pnCheckQLKhachHang);

        JPanel pnCheckQLThongKe = new TransparentPanel();
        ckbThongKe = new JCheckBox("Quản lý thống kê.");
        ckbThongKe.setFont(font);
        pnCheckQLThongKe.add(ckbThongKe);
        pnPhanQuyen.add(pnCheckQLThongKe);

        Dimension ckbSize = ckbQLKhachHang.getPreferredSize();
        cmbQuyen.setPreferredSize(ckbSize);
        ckbNhapHang.setPreferredSize(ckbSize);
        ckbQLSanPham.setPreferredSize(ckbSize);
        ckbQLNhanVien.setPreferredSize(ckbSize);
        ckbQLKhachHang.setPreferredSize(ckbSize);
        ckbThongKe.setPreferredSize(ckbSize);

        JPanel pnButtonQuyen = new TransparentPanel();
        btnThemQuyen = new JButton("Thêm quyền");
        btnSuaQuyen = new JButton("Sửa quyền");
        btnXoaQuyen = new JButton("Xoá quyền");
        btnThemQuyen.setFont(font);
        btnSuaQuyen.setFont(font);
        btnXoaQuyen.setFont(font);
        btnThemQuyen.setIcon(new ImageIcon("images/add-icon.png"));
        btnSuaQuyen.setIcon(new ImageIcon("images/Pencil-icon.png"));
        btnXoaQuyen.setIcon(new ImageIcon("images/delete-icon.png"));
        pnButtonQuyen.add(btnThemQuyen);
        pnButtonQuyen.add(btnSuaQuyen);
        pnButtonQuyen.add(btnXoaQuyen);
        btnSuaQuyen.setPreferredSize(btnThemQuyen.getPreferredSize());
        btnXoaQuyen.setPreferredSize(btnThemQuyen.getPreferredSize());
        pnPhanQuyen.add(pnButtonQuyen);

        JPanel pnImage = new ImagePanel("images/backgroundManagerment.jpg");
        pnImage.setPreferredSize(new Dimension(w, 450));
        pnPhanQuyen.add(pnImage);
        //========================
        pnCardTabNhanVien = new JPanel(cardNhanVienGroup);
        pnCardTabNhanVien.add(pnNhanVien, "1");
        pnCardTabNhanVien.add(pnPhanQuyen, "2");
        this.add(pnCardTabNhanVien);

        loadDataTblNhanVien();
        //loadDataCmbQuyen();
    }

    JComboBox<String> cmbQuyen;
    JCheckBox ckbNhapHang, ckbQLSanPham, ckbQLNhanVien, ckbQLKhachHang, ckbThongKe;
    JButton btnSuaQuyen, btnThemQuyen, btnXoaQuyen;

    private void addEventsNhanVien() {
        lblTabbedNhanVien.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblTabbedNhanVien.setIcon(tabbedSelected);
                lblTabbedQuyen.setIcon(tabbedDefault);
                cardNhanVienGroup.show(pnCardTabNhanVien, "1");
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        lblTabbedQuyen.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblTabbedQuyen.setIcon(tabbedSelected);
                lblTabbedNhanVien.setIcon(tabbedDefault);
                cardNhanVienGroup.show(pnCardTabNhanVien, "2");
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        tblNhanVien.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                xuLyClickTblNhanVien();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        txtTimNV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyTimKiemNhanVien();
            }
        });

        btnTimNV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyTimKiemNhanVien();
            }
        });

        btnThemNV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyThemNhanVien();
            }
        });

        btnSuaNV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLySuaNhanVien();
            }
        });

        btnXoaNV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyXoaNhanVien();
            }
        });

        btnXuatExcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyXuatExcel();
            }
        });

        btnNhapExcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyNhapExcel();
            }
        });

        btnCapTaiKhoan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //xuLyCapTaiKhoan();
            }
        });

        btnResetMatKhau.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //xuLyResetMatKhau();
            }
        });

        btnXoaTaiKhoan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //xuLyKhoaTaiKhoan();
            }
        });

        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadDataTblNhanVien();
                txtMaNV.setText("");
                txtTen.setText("");
                txtRole.setText("");
                txtChucVu.setText("");
                txtTimNV.setText("");
                cmbGioiTinh.setSelectedIndex(0);
            }
        });
    }

    private void addEventsPhanQuyen() {
        cmbQuyen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //xuLyHienThiChiTietQuyen();
            }
        });
        btnThemQuyen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //xuLyThemQuyen();
            }
        });
        btnSuaQuyen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //xuLySuaQuyen();
            }
        });
        btnXoaQuyen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //xuLyXoaQuyen();
            }
        });
    }

//    private void xuLyXoaQuyen() {
//        if (cmbQuyen.getSelectedIndex() < 1) {
//            new MyDialog("Chưa chọn nhóm quyền để xoá!", MyDialog.ERROR_DIALOG);
//            return;
//        }
//        MyDialog dlg = new MyDialog("Bạn có chắc chắn muốn xoá?", MyDialog.WARNING_DIALOG);
//        if (dlg.getAction() == MyDialog.CANCEL_OPTION) {
//            return;
//        }
//        String tenQuyen = cmbQuyen.getSelectedItem() + "";
//        boolean flag = phanQuyenBUS.xoaQuyen(tenQuyen);
//        if (flag) {
//            loadDataCmbQuyen();
//        }
//    }

//    private void xuLyThemQuyen() {
//        String tenQuyen = JOptionPane.showInputDialog("Nhập tên quyền");
//
//        boolean flag = phanQuyenBUS.themQuyen(tenQuyen);
//        if (flag) {
//            loadDataCmbQuyen();
//        }
//    }

//    private void xuLySuaQuyen() {
//        if (cmbQuyen.getSelectedIndex() < 1) {
//            new MyDialog("Chưa chọn nhóm quyền để sửa!", MyDialog.ERROR_DIALOG);
//            return;
//        }
//        String tenQuyen = cmbQuyen.getSelectedItem() + "";
//        int nhapHang = ckbNhapHang.isSelected() ? 1 : 0;
//        int sanPham = ckbQLSanPham.isSelected() ? 1 : 0;
//        int nhanVien = ckbQLNhanVien.isSelected() ? 1 : 0;
//        int khachHang = ckbQLKhachHang.isSelected() ? 1 : 0;
//        int thongKe = ckbThongKe.isSelected() ? 1 : 0;
//
//        boolean flag = phanQuyenBUS.suaQuyen(tenQuyen, nhapHang, sanPham, nhanVien, khachHang, thongKe);
//        if (flag) {
//            loadDataCmbQuyen();
//        }
//    }

//    private void xuLyHienThiChiTietQuyen() {
//        ArrayList<PhanQuyen> dsq = phanQuyenBUS.getListQuyen();
//        PhanQuyen phanQuyen = new PhanQuyen();
//        for (PhanQuyen pq : dsq) {
//            if (pq.getQuyen().equals(cmbQuyen.getSelectedItem())) {
//                phanQuyen.setQuyen(pq.getQuyen());
//                phanQuyen.setNhapHang(pq.getNhapHang());
//                phanQuyen.setQlSanPham(pq.getQlSanPham());
//                phanQuyen.setQlNhanVien(pq.getQlNhanVien());
//                phanQuyen.setQlKhachHang(pq.getQlKhachHang());
//                phanQuyen.setThongKe(pq.getThongKe());
//                break;
//            }
//        }
//        ckbNhapHang.setSelected(false);
//        ckbQLSanPham.setSelected(false);
//        ckbQLNhanVien.setSelected(false);
//        ckbQLKhachHang.setSelected(false);
//        ckbThongKe.setSelected(false);
//        if (phanQuyen.getNhapHang() == 1) {
//            ckbNhapHang.setSelected(true);
//        }
//        if (phanQuyen.getQlSanPham() == 1) {
//            ckbQLSanPham.setSelected(true);
//        }
//        if (phanQuyen.getQlNhanVien() == 1) {
//            ckbQLNhanVien.setSelected(true);
//        }
//        if (phanQuyen.getQlKhachHang() == 1) {
//            ckbQLKhachHang.setSelected(true);
//        }
//        if (phanQuyen.getThongKe() == 1) {
//            ckbThongKe.setSelected(true);
//        }
//    }
//    private void loadDataCmbQuyen() {
//        phanQuyenBUS.docDanhSachQuyen();
//        ArrayList<PhanQuyen> dsq = phanQuyenBUS.getListQuyen();
//        cmbQuyen.removeAllItems();
//        cmbQuyen.addItem("Chọn quyền");
//        for (PhanQuyen pq : dsq) {
//            cmbQuyen.addItem(pq.getQuyen());
//        }
//    }
//    private void xuLyResetMatKhau() {
//        String maNV = txtMaNV.getText();
//        if (maNV.trim().equals("")) {
//            new MyDialog("Hãy chọn nhân viên!", MyDialog.ERROR_DIALOG);
//            return;
//        }
//        DlgQuyen_MatKhau dialog = new DlgQuyen_MatKhau(maNV);
//        dialog.setVisible(true);
//    }

//    private void xuLyCapTaiKhoan() {
//        if (txtMaNV.getText().trim().equals("")) {
//            new MyDialog("Hãy chọn nhân viên!", MyDialog.ERROR_DIALOG);
//            return;
//        }
//        DlgCapTaiKhoan dialog = new DlgCapTaiKhoan(txtMaNV.getText());
//        dialog.setVisible(true);
//        loadDataTblNhanVien();
//    }

//    private void xuLyKhoaTaiKhoan() {
//        TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();
//        taiKhoanBUS.khoaTaiKhoan(txtMaNV.getText());
//        loadDataTblNhanVien();
//    }

    private void xuLyNhapExcel() {
        MyDialog dlg = new MyDialog("Dữ liệu cũ sẽ bị xoá, tiếp tục?", MyDialog.WARNING_DIALOG);
        if (dlg.getAction() != MyDialog.OK_OPTION) {
            return;
        }

        ExcelHelper importFile = new ExcelHelper();
        importFile.importFromExcel(tblNhanVien);
        employeeService.importFromTable(tblNhanVien);

    }

    private void xuLyXuatExcel() {
        ExcelHelper exportFile = new ExcelHelper();
        exportFile.exportToExcel(tblNhanVien);
    }

    private void xuLyXoaNhanVien() {
        int empId = Integer.parseInt(txtMaNV.getText());
        boolean flag = employeeService.deleteEmployee(empId);
        if (flag) {
            loadDataTblNhanVien();
        }
    }

    private void xuLySuaNhanVien() {
        if (cmbGioiTinh.getSelectedIndex() == 0) {
            new MyDialog("Hãy chọn giới tính!", MyDialog.ERROR_DIALOG);
            return;
        }
        int empId = Integer.parseInt(txtMaNV.getText().toString());
        var emp = employeeService.getAllEmployeeById(empId);
        emp.setFullName(txtTen.getText().toString());    
        emp.setSex(cmbGioiTinh.getSelectedItem().toString());
        emp.setPosition(txtChucVu.getText().toString());

        if (employeeService.updateEmployee(emp)) {
            loadDataTblNhanVien();
        }
    }

    private void xuLyThemNhanVien() {
        if (cmbGioiTinh.getSelectedIndex() == 0) {
            new MyDialog("Hãy chọn giới tính!", MyDialog.ERROR_DIALOG);
            return;
        }
        Employee employee = new Employee();
        employee.setFullName(txtTen.getText().toString());
        employee.setSex(cmbGioiTinh.getSelectedItem().toString());
        employee.setPosition(txtChucVu.getText().toString());

        if (employeeService.addEmployee(employee)) {
            loadDataTblNhanVien();
        }
    }

    private void xuLyTimKiemNhanVien() {
        dtmNhanVien.setRowCount(0);
        List<Employee> lstEmp = employeeService.getAllEmployeeByName(txtTimNV.getText());

        for (Employee emp : lstEmp) {
            Vector vec = new Vector();
            vec.add(emp.getEmployeeId());
            vec.add(emp.getFullName());
            vec.add(emp.getSex());
            vec.add(emp.getPosition());
            var user = userService.getUserByEmpId(emp.getEmployeeId());
            vec.add(user.getUsername());
            vec.add(user.getRole());

            dtmNhanVien.addRow(vec);
        }
    }
 

    private void xuLyClickTblNhanVien() {
        int row = tblNhanVien.getSelectedRow();
        if (row > -1) {
            txtMaNV.setText(tblNhanVien.getValueAt(row, 0) + "");
            txtTen.setText(tblNhanVien.getValueAt(row, 1) + "");
            txtRole.setText(tblNhanVien.getValueAt(row, 2) + "");
            txtChucVu.setText(tblNhanVien.getValueAt(row, 4) + "");

            String gioiTinh = "";
            if ((tblNhanVien.getValueAt(row, 3) + "").equals("Nam")) {
                cmbGioiTinh.setSelectedIndex(1);
            } else {
                cmbGioiTinh.setSelectedIndex(2);
            }
        }
    }

    private void loadDataTblNhanVien() {
        dtmNhanVien.setRowCount(0);
        List<Employee> lstEmp = employeeService.getAllEmployees();

        for (Employee emp : lstEmp) {
            Vector vec = new Vector();
            vec.add(emp.getEmployeeId());
            vec.add(emp.getFullName());
            vec.add(emp.getSex());
            vec.add(emp.getPosition());
            var user = userService.getUserByEmpId(emp.getEmployeeId());
            vec.add(user.getUsername());
            vec.add(user.getRole());

            dtmNhanVien.addRow(vec);
        }
    }

}
