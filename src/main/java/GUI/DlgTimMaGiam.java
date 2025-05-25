package GUI;

import BLL.PromotionService;
import DTO.Promotion;
import Utils.MyTable;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class DlgTimMaGiam extends JDialog {

    public static Promotion promotion = null;
    private PromotionService promotionService = new PromotionService();
    private double tongTien = 0;

    public DlgTimMaGiam(double tongTien) {
        this.tongTien = tongTien;
        addControls();
        addEvents();

        this.setSize(750, 500);
        this.setModal(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    private JTextField txtTuKhoa;
    private JTable tblMaGiam;
    private DefaultTableModel dtmMaGiam;
    private JButton btnChon, btnThoat;

    private void addControls() {
        Container con = getContentPane();
        con.setLayout(new BorderLayout());

        Font font = new Font("Tahoma", Font.PLAIN, 16);
        JPanel pnTop = new JPanel();
        JLabel lblTuKhoa = new JLabel("Từ khoá tìm");
        txtTuKhoa = new JTextField(20);
        lblTuKhoa.setFont(font);
        txtTuKhoa.setFont(font);
        pnTop.add(lblTuKhoa);
        pnTop.add(txtTuKhoa);
        con.add(pnTop, BorderLayout.NORTH);

        JPanel pnTable = new JPanel();
        pnTable.setLayout(new BorderLayout());
        dtmMaGiam = new DefaultTableModel();
        dtmMaGiam.addColumn("Mã");
        dtmMaGiam.addColumn("Chương trình");
        dtmMaGiam.addColumn("% KM");
        dtmMaGiam.addColumn("Điều kiện");
        dtmMaGiam.addColumn("Bắt đầu");
        dtmMaGiam.addColumn("Kết thúc");
        dtmMaGiam.addColumn("Trạng thái");
        tblMaGiam = new MyTable(dtmMaGiam);
        JScrollPane scrMaGiam = new JScrollPane(tblMaGiam);
        pnTable.add(scrMaGiam, BorderLayout.CENTER);
        con.add(pnTable, BorderLayout.CENTER);

        JPanel pnButton = new JPanel();
        btnChon = new JButton("Chọn");
        btnThoat = new JButton("Thoát");
        btnChon.setFont(font);
        btnThoat.setFont(font);
        pnButton.add(btnChon);
        pnButton.add(btnThoat);
        con.add(pnButton, BorderLayout.SOUTH);

        TableColumnModel columnModelBanHang = tblMaGiam.getColumnModel();
        columnModelBanHang.getColumn(0).setPreferredWidth(56);
        columnModelBanHang.getColumn(1).setPreferredWidth(213);
        columnModelBanHang.getColumn(2).setPreferredWidth(30);
        columnModelBanHang.getColumn(3).setPreferredWidth(62);
        columnModelBanHang.getColumn(4).setPreferredWidth(58);
        columnModelBanHang.getColumn(5).setPreferredWidth(61);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tblMaGiam.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        tblMaGiam.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);

        btnChon.setPreferredSize(new Dimension(120, 40));
        btnThoat.setPreferredSize(btnChon.getPreferredSize());

        loadDataLenTable();
    }

    private void addEvents() {
        txtTuKhoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadDataLenTable(txtTuKhoa.getText());
            }
        });

        btnChon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyChonMaGiam();
            }
        });

        btnThoat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyThoat();
            }
        });
    }

    private void xuLyChonMaGiam() {
        int row = tblMaGiam.getSelectedRow();
        if (row > -1) {
            try {
                if (tblMaGiam.getValueAt(row, 6).equals("Không hiệu lực")) {
                    JOptionPane.showMessageDialog(null, "Mã này đã hết hiệu lực!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadDataLenTable();
                    return;
                }
                int ma = Integer.parseInt(tblMaGiam.getValueAt(row, 0) + "");
                String ten = tblMaGiam.getValueAt(row, 1) + "";
                double phanTram = Double.parseDouble(tblMaGiam.getValueAt(row, 2) + "");
                String dieuKienst = tblMaGiam.getValueAt(row, 3) + "";
                dieuKienst = dieuKienst.replace(">", "");
                dieuKienst = dieuKienst.replace(",", "");
                double dieuKien = Double.parseDouble(dieuKienst);

                if (dieuKien > tongTien) {
                    JOptionPane.showMessageDialog(null, "Không đủ điều kiện áp dụng mã giảm này!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//                Date ngayBD = (Date) sdf.parse(tblMaGiam.getValueAt(row, 4) + "");
//                Date ngayKT = (Date) sdf.parse(tblMaGiam.getValueAt(row, 5) + "");

                Object valBD = tblMaGiam.getValueAt(row, 4);
                java.util.Date utilNgayBD = valBD instanceof Date
                        ? new java.util.Date(((Date) valBD).getTime())
                        : sdf.parse(valBD.toString());
                java.sql.Date ngayBD = new java.sql.Date(utilNgayBD.getTime());

                Object valKT = tblMaGiam.getValueAt(row, 5);
                java.util.Date utilNgayKT = valKT instanceof Date
                        ? new java.util.Date(((Date) valKT).getTime())
                        : sdf.parse(valKT.toString());
                java.sql.Date ngayKT = new java.sql.Date(utilNgayKT.getTime());

                promotion = new Promotion();
                promotion.setPromoId(ma);
                promotion.setName(ten);
                promotion.setDiscountPercent(phanTram);
                promotion.setMinOrderAmount(dieuKien);
                promotion.setStartDate(ngayBD);
                promotion.setEndDate(ngayKT);
            } catch (ParseException ex) {
            }
        }
        xuLyThoat();
    }

    private void xuLyThoat() {
        dispose();
    }

    private void loadDataLenTable() {
        dtmMaGiam.setRowCount(0);
        List<Promotion> lstPromotion = promotionService.getAllPromotions();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        DecimalFormat dcf = new DecimalFormat(">###,###");
        for (Promotion prom : lstPromotion) {
            Vector vec = new Vector();
            vec.add(prom.getPromoId());
            vec.add(prom.getName());
            vec.add(prom.getDiscountPercent());
            vec.add(dcf.format(prom.getMinOrderAmount()));
            vec.add(sdf.format(prom.getStartDate()));
            vec.add(sdf.format(prom.getEndDate()));

            Date now = new java.sql.Date(System.currentTimeMillis());
            if (prom.getStartDate().before(now) && prom.getEndDate().after(now)) {
                vec.add("Có hiệu lực");
            } else {
                vec.add("Không hiệu lực");
            }
            dtmMaGiam.addRow(vec);
        }
    }

    private void loadDataLenTable(String tuKhoa) {
        TableColumnModel columnModelBanHang = tblMaGiam.getColumnModel();

    }

}
