package GUI;

/**
 * Quản lý khách hàng – phiên bản LOCAL, không cần CSDL
 * @author tienn
 */

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomerDashboardFrame extends JFrame {

    /* =================================================================== */
    /* =======================  DTO (đơn giản)  ========================== */
    /* =================================================================== */

    public static class Customer {
        private int    customerId;
        private String fullName;
        private String phone;
        private String email;
        private String address;

        /* getters & setters */
        public int  getCustomerId()           { return customerId; }
        public void setCustomerId(int id)     { this.customerId = id; }
        public String getFullName()           { return fullName; }
        public void setFullName(String n)     { this.fullName = n; }
        public String getPhone()              { return phone; }
        public void setPhone(String p)        { this.phone = p; }
        public String getEmail()              { return email; }
        public void setEmail(String e)        { this.email = e; }
        public String getAddress()            { return address; }
        public void setAddress(String a)      { this.address = a; }
    }

    /* =================================================================== */
    /* =======================  Service In-Memory  ======================= */
    /* =================================================================== */

    private static class LocalCustomerService {
        private final List<Customer> store = new ArrayList<>();
        private final AtomicInteger  seq   = new AtomicInteger(1);

        public LocalCustomerService() {         // demo data
            add(mock("Nguyễn Văn A", "0901234567", "a@mail.com", "Hà Nội"));
            add(mock("Trần B",        "0912345678", "b@mail.com", "HCM"));
        }
        private Customer mock(String n,String p,String e,String a){
            Customer c = new Customer();
            c.setFullName(n); c.setPhone(p); c.setEmail(e); c.setAddress(a);
            return c;
        }

        public List<Customer> getAll() { return new ArrayList<>(store); }

        public boolean add(Customer c){
            c.setCustomerId(seq.getAndIncrement());
            return store.add(c);
        }

        public boolean update(Customer c){
            for (int i = 0; i < store.size(); i++){
                if (store.get(i).getCustomerId() == c.getCustomerId()){
                    store.set(i, c); return true;
                }
            }
            return false;
        }

        public boolean delete(int id){
            return store.removeIf(c -> c.getCustomerId() == id);
        }

        public List<Customer> search(String kw){
            kw = kw.toLowerCase();
            List<Customer> res = new ArrayList<>();
            for (Customer c : store){
                if (c.getFullName().toLowerCase().contains(kw)
                 || c.getPhone().contains(kw)
                 || c.getEmail().toLowerCase().contains(kw))
                    res.add(c);
            }
            return res;
        }
    }

    /* =================================================================== */
    /* =========================  UI – FRAME  ============================ */
    /* =================================================================== */

    private final LocalCustomerService service = new LocalCustomerService();
    private final DefaultTableModel    model   = new DefaultTableModel();

    private JTable       tbl;
    private JTextField   txtId, txtName, txtPhone, txtEmail, txtSearch;
    private JTextArea    txtAddress;

    public CustomerDashboardFrame() {
        setTitle("Quản lý khách hàng (Local)");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI();
        loadTable(service.getAll());

        setVisible(true);
    }

    private void initUI() {
        /* ------- FORM INPUT ------- */
        JPanel form = new JPanel(new GridLayout(5, 2, 4, 4));
        form.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));

        txtId      = new JTextField(); txtId.setEditable(false);
        txtName    = new JTextField();
        txtPhone   = new JTextField();
        txtEmail   = new JTextField();
        txtAddress = new JTextArea(3, 20);

        form.add(new JLabel("Mã KH:"));     form.add(txtId);
        form.add(new JLabel("Họ tên:"));     form.add(txtName);
        form.add(new JLabel("Điện thoại:")); form.add(txtPhone);
        form.add(new JLabel("Email:"));      form.add(txtEmail);
        form.add(new JLabel("Địa chỉ:"));    form.add(new JScrollPane(txtAddress));

        /* ------- BUTTONS ------- */
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        JButton btnAdd = new JButton("Thêm"),
                btnUpd = new JButton("Cập nhật"),
                btnDel = new JButton("Xoá"),
                btnClr = new JButton("Làm mới");
        btnPanel.add(btnAdd); btnPanel.add(btnUpd); btnPanel.add(btnDel); btnPanel.add(btnClr);

        /* ------- SEARCH ------- */
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtSearch = new JTextField(25);
        searchPanel.add(new JLabel("Tìm kiếm:")); searchPanel.add(txtSearch);

        /* ------- TABLE ------- */
        model.setColumnIdentifiers(new String[]{"ID","Họ tên","Điện thoại","Email","Địa chỉ"});
        tbl = new JTable(model);
        tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scr = new JScrollPane(tbl);

        /* ------- LAYOUT ------- */
        setLayout(new BorderLayout(8,8));
        add(form, BorderLayout.NORTH);
        add(scr,  BorderLayout.CENTER);

        JPanel south = new JPanel(new BorderLayout());
        south.add(searchPanel, BorderLayout.NORTH);
        south.add(btnPanel,    BorderLayout.SOUTH);
        add(south, BorderLayout.SOUTH);

        /* ------- ACTIONS ------- */
        btnAdd.addActionListener(e -> handleAdd());
        btnUpd.addActionListener(e -> handleUpdate());
        btnDel.addActionListener(e -> handleDelete());
        btnClr.addActionListener(e -> clearForm());

        tbl.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { loadSelectedRow(); }
        });

        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e){ liveSearch(); }
            public void removeUpdate(DocumentEvent e){ liveSearch(); }
            public void changedUpdate(DocumentEvent e){ /* not used */ }
        });
    }

    /* =================================================================== */
    /* ======================  HELPER / CRUD OPS  ======================== */
    /* =================================================================== */

    private void loadTable(List<Customer> list){
        model.setRowCount(0);
        for (Customer c : list){
            model.addRow(new Object[]{
                c.getCustomerId(), c.getFullName(),
                c.getPhone(),      c.getEmail(), c.getAddress()
            });
        }
    }

    private Customer getFormData(){
        Customer c = new Customer();
        if (!txtId.getText().isBlank()){
            try { c.setCustomerId(Integer.parseInt(txtId.getText().trim())); }
            catch (NumberFormatException ignore){}
        }
        c.setFullName(txtName.getText().trim());
        c.setPhone   (txtPhone.getText().trim());
        c.setEmail   (txtEmail.getText().trim());
        c.setAddress (txtAddress.getText().trim());
        return c;
    }

    private void handleAdd(){
        if (service.add(getFormData())){
            loadTable(service.getAll());
            clearForm();
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
        }
    }

    private void handleUpdate(){
        if (service.update(getFormData())){
            loadTable(service.getAll());
            clearForm();
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
        }
    }

    private void handleDelete(){
        if (txtId.getText().isBlank()) return;
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            if (service.delete(id)){
                loadTable(service.getAll());
                clearForm();
                JOptionPane.showMessageDialog(this, "Đã xoá!");
            }
        } catch (NumberFormatException ignore){}
    }

    private void clearForm(){
        txtId.setText(""); txtName.setText(""); txtPhone.setText("");
        txtEmail.setText(""); txtAddress.setText("");
    }

    private void loadSelectedRow(){
        int r = tbl.getSelectedRow(); if (r == -1) return;
        txtId.setText(model.getValueAt(r,0).toString());
        txtName.setText(model.getValueAt(r,1).toString());
        txtPhone.setText(model.getValueAt(r,2).toString());
        txtEmail.setText(model.getValueAt(r,3).toString());
        txtAddress.setText(model.getValueAt(r,4).toString());
    }

    private void liveSearch(){
        loadTable(service.search(txtSearch.getText().trim()));
    }

    /* =================================================================== */
    /* =============================== MAIN ============================== */
    /* =================================================================== */

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CustomerDashboardFrame::new);
    }
}
