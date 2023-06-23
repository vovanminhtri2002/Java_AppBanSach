/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import DAO.BanHangSachDAO;
import DAO.ChiTietDonHangDAO;
import DAO.DonHangDAO;
import DAO.KhachHangDAO;
import DAO.LoadSanPhamDAO;
import POJO.BanHangSach;
import POJO.ChiTietDonHang;
import POJO.DonHang;
import POJO.KhachHang;
import POJO.LoadSanPham;
import POJO.NhanVien;
import SP.Mes;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author HP
 */
public class fTrangChuNhanVienBanHang extends javax.swing.JFrame {

    /**
     * Creates new form TrangChu
     */
    fTrangChuNhanVienBanHang trangChuNhanVienBanHang;
    KhachHang khachhang = null;
    NhanVien nhanvien = null;
    KhachHangDAO khdao = new KhachHangDAO();
    BanHangSachDAO bsdao = new BanHangSachDAO();
    DonHangDAO dhdao = new DonHangDAO();
    ChiTietDonHangDAO ctdhdao = new ChiTietDonHangDAO();
    String tk = "";
    String btnKhachHang = "Không";

    public fTrangChuNhanVienBanHang(NhanVien nv) {
        initComponents();
        trangChuNhanVienBanHang = this;
        jpTaiKhoan.setVisible(true);
        jpSanPham.setVisible(false);
        jpKhachHang.setVisible(false);
        jpHoaDon.setVisible(false);
        jpBanHang.setVisible(false);
        tabTaiKhoan.setBackground(Color.white);
        tabSanPham.setBackground(new Color(255, 153, 153));
        tabKhachHang.setBackground(new Color(255, 153, 153));
        tabHoaDon.setBackground(new Color(255, 153, 153));
        tabBanHang.setBackground(new Color(255, 153, 153));
        Mes.SetEnibleJtextPanel(false, jpTaiKhoan);
        cboGioiTinh.setEnabled(false);

        nhanvien = nv;
        txtMaNhanVienDH.setText(nhanvien.getMaNV() + "");
        txtTrangThaiDH.setText("Chưa thanh toán");

        tabTaiKhoan.setBackground(Color.white);
        XetDLTaiKHoan(nv);
        PanelTaiKhoan();
        PanelKhachHang();
        PanelSanPham();
        PanelBanHang();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        lblNgayBan.setText(LocalDate.now().format(formatter));
        txtNgayMuaDH.setText(LocalDate.now().format(formatter));

        tblDonHang.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                clearCTDH();
                int row = tblDonHang.getSelectedRow();
                if (row >= 0 && row < tblDonHang.getRowCount()) {
                    ArrayList<ChiTietDonHang> dsctdh = ChiTietDonHangDAO.getChiTietDonHangBangMaDH(Integer.parseInt(tblDonHang.getValueAt(row, 0).toString()));
                    SetDataTableCTDonHang(dsctdh);
                    ComboBoxModel<Object> dskh = cboKHDH.getModel();
                    int i = 0;
                    for (; i < dskh.getSize(); i++) {
                        String mkh = ((KhachHang) dskh.getElementAt(i)) == null ? "" : ((KhachHang) dskh.getElementAt(i)).getMaKH() + "";
                        if (tblDonHang.getValueAt(row, 5).toString().equals(mkh)) {
                            break;
                        }
                    }
                    cboKHDH.setSelectedIndex(i);
                }
            }
        });
        tblSachBH.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                clearCTDH();
            }
        });

        btnBHThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowDH = tblDonHang.getSelectedRow();
                int rowSach = tblSachBH.getSelectedRow();
                if (rowDH < 0 || rowDH >= tblDonHang.getRowCount()) {
                    JOptionPane.showMessageDialog(trangChuNhanVienBanHang, "Vui lòng chọn đơn hàng cần thêm vào");
                    return;
                }
                if (rowSach < 0 || rowSach >= tblSachBH.getRowCount()) {
                    JOptionPane.showMessageDialog(trangChuNhanVienBanHang, "Vui lòng chọn sách cần thêm vào");
                    return;
                }
                txtMaHoaDonCTDH.setText(tblDonHang.getValueAt(rowDH, 0).toString());
                txtMaSachCTDH.setText(tblSachBH.getValueAt(rowSach, 0).toString());

            }
        });
        txtSoLuongCTDH.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int sl = 0;
                try {
                    if (e.getKeyChar() == Event.BACK_SPACE) {
                        String str = txtSoLuongCTDH.getText();
                        System.out.println(str.substring(0, str.length()));
                        sl = Integer.parseInt(str.substring(0, str.length()));
                    } else if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9') {
                        sl = Integer.parseInt(txtSoLuongCTDH.getText() + e.getKeyChar());
                    } else {
                        e.consume();
                    }
                } catch (NumberFormatException ex) {
                    sl = 0;
                }
                int row = tblSachBH.getSelectedRow();
                if (row >= 0 && row < tblSachBH.getRowCount()) {
                    txtTongTienCTDH.setText(Double.parseDouble(tblSachBH.getValueAt(row, 3).toString()) * sl + "");
                }
            }
        });
        btnBHLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtTrangThaiDH.getText().length() <= 0) {
                    JOptionPane.showMessageDialog(trangChuNhanVienBanHang, "Vui lòng nhập đủ thông tin");
                    return;
                }
                int makh = ((KhachHang) cboKHDH.getSelectedItem()) == null ? -1 : ((KhachHang) cboKHDH.getSelectedItem()).getMaKH();
                int ID = DonHangDAO.insertDonHang(makh, Integer.parseInt(txtMaNhanVienDH.getText()), txtTrangThaiDH.getText());
                txtMaDon.setText(ID + "");
                ArrayList<DonHang> dsdh = dhdao.getDonHang();
                SetDataTableDonHang(dsdh);
            }
        });
    }

    private void loadComboxKHDH() {
        ArrayList<KhachHang> ds = new ArrayList<>();
        ds.add(null);
        ds.addAll(khdao.getDanhSachKH());
        cboKHDH.setModel(new DefaultComboBoxModel(ds.toArray(new KhachHang[ds.size()])));
        cboKHDH.setPreferredSize(new Dimension(250, 40));
    }

    private void clearCTDH() {
        txtMaHoaDonCTDH.setText("");
        txtMaSachCTDH.setText("");
        txtSoLuongCTDH.setText("");
        txtTongTienCTDH.setText("");
    }

    private fTrangChuNhanVienBanHang() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void PanelTaiKhoan() {
        Mes.SetEnibleJtextPanel(false, jpTaiKhoan);
        cboGioiTinh.setEnabled(false);
    }

    public void PanelKhachHang() {
        Mes.SetEnibleJtextPanel(false, jpKhachHang);
        ArrayList<KhachHang> ds = khdao.getDanhSachKH();
        SetDataTableKH(ds);
        txtTimKiemKhachHang.setEnabled(true);
        btnLuuKhachHang.setEnabled(false);
    }

    public void PanelSanPham() {
        Mes.SetEnibleJtextPanel(true, jpSanPham);
        ArrayList<LoadSanPham> ds = LoadSanPhamDAO.getLoadSanPham();
        SetDataTableSP(ds);
//        txtTimKiemKhachHang.setEnabled(true);
    }

    public void PanelBanHang() {
        ArrayList<BanHangSach> ds = bsdao.getBanHangSach();
        SetDataTableBanHangSach(ds);
        ArrayList<DonHang> dsdh = dhdao.getDonHang();
        SetDataTableDonHang(dsdh);
        loadComboxKHDH();
    }

    public void XetDLTaiKHoan(NhanVien nv) {
        txtUsename.setText(nv.getUsername());
        txtHoTen.setText(nv.getTenNV());
        lblTenNhanVien.setText(nv.getTenNV());
        txtSoDienThoaiTK.setText(nv.getSoDienThoai());
        txtEmailTK.setText(nv.getEmail());
        String gt = nv.getGioiTinh().trim();
        cboGioiTinh.setSelectedItem(gt);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        txtNgaySinhTK.setText(nv.getNgaySinh().format(formatter));
        LocalDate ngaySinh = nv.getNgaySinh();
        txtSoCCCDTK.setText(nv.getSoCCCD());
        txtDiaChiTK.setText(nv.getDiaChi());
    }

    public void SetDataTableBanHangSach(ArrayList<BanHangSach> dsSach) {
        Vector titleTable = new Vector();
        titleTable.add("Mã Sách");
        titleTable.add("Tên Sách");
        titleTable.add("Số Lượng Tồn");
        titleTable.add("Đơn Giá Bán");
        Vector dataTable = new Vector();
        Mes.FormatTable(dataTable);
        for (BanHangSach kh : dsSach) {
            Vector h = new Vector();
            h.add(kh.getMaSach());
            h.add(kh.getTenSach());
            h.add(kh.getSoLuongTon());
            h.add(kh.getDonGiaBan());
            dataTable.add(h);
        }
        Mes.SetDuLieuTable(titleTable, dataTable, tblSachBH);
    }

    public void SetDataTableSP(ArrayList<LoadSanPham> dsSach) {
        Vector titleTable = new Vector();
        titleTable.add("Mã Sách");
        titleTable.add("Tên Sách");
        titleTable.add("Đơn Giá Bán");
        titleTable.add("Số Lượng Tồn");
        titleTable.add("Mô Tả");
        titleTable.add("Tên NXB");
        titleTable.add("Tên TL");
        titleTable.add("Tên TG");

        Vector dataTable = new Vector();
        Mes.FormatTable(dataTable);
        for (LoadSanPham kh : dsSach) {
            Vector h = new Vector();
            h.add(kh.getMaSach());
            h.add(kh.getTenSach());
            h.add(kh.getDonGiaBan());
            h.add(kh.getSoLuongTon());
            h.add(kh.getMoTa());
            h.add(kh.getTenNXB());
            h.add(kh.getTenTL());
            h.add(kh.getTenTG());
            dataTable.add(h);
        }
        Mes.SetDuLieuTable(titleTable, dataTable, tblSanPham);
    }

    public void SetDataTableCTDonHang(ArrayList<ChiTietDonHang> dsSach) {
        Vector titleTable = new Vector();
        titleTable.add("Mã Đơn Hàng");
        titleTable.add("Mã Sách");
        titleTable.add("Số Lượng");
        titleTable.add("Tổng Tiền");

        Vector dataTable = new Vector();
        Mes.FormatTable(dataTable);
        for (ChiTietDonHang kh : dsSach) {
            Vector h = new Vector();
            h.add(kh.getMaDH());
            h.add(kh.getMaSach());
            h.add(kh.getSoLuong());
            h.add(kh.getTongTien());
            dataTable.add(h);
        }
        Mes.SetDuLieuTable(titleTable, dataTable, tblChiTietDH);
    }

    public void SetDataTableDonHang(ArrayList<DonHang> dsSach) {
        Vector titleTable = new Vector();
        titleTable.add("Mã Đơn");
        titleTable.add("Ngày Mua");
        titleTable.add("Thành Tiền");
        titleTable.add("Trạng Thái");
        titleTable.add("Mã Nhân Viên");
        titleTable.add("Mã Khách Hàng");

        Vector dataTable = new Vector();
        Mes.FormatTable(dataTable);
        for (DonHang kh : dsSach) {
            Vector h = new Vector();
            h.add(kh.getMaDH());
            h.add(kh.getNgayMua());
            h.add(kh.getThanhTien());
            h.add(kh.getTrangThai());
            h.add(kh.getMaNV());
            h.add(kh.getMaKH());
            dataTable.add(h);
        }
        Mes.SetDuLieuTable(titleTable, dataTable, tblDonHang);
        Mes.SetDuLieuTable(titleTable, dataTable, tblBangDH);
    }

    public void SetDataTableKH(ArrayList<KhachHang> dsKH) {
        Vector titleTable = new Vector();
        titleTable.add("Mã");
        titleTable.add("Tên KH");
        titleTable.add("Ngày Sinh");
        titleTable.add("Giới Tính");
        titleTable.add("Email");
        titleTable.add("Địa Chỉ");
        titleTable.add("SDT");
        titleTable.add("SCCCD");

        Vector dataTable = new Vector();
        Mes.FormatTable(dataTable);
        for (KhachHang kh : dsKH) {
            Vector h = new Vector();
            h.add(kh.getMaKH());
            h.add(kh.getTenKH());
            h.add(kh.getNgaySinh());
            h.add(kh.getGioiTinh());
            h.add(kh.getEmail());
            h.add(kh.getDiaChi());
            h.add(kh.getSoDienThoai());
            h.add(kh.getSoCCCD());
            dataTable.add(h);
        }
        Mes.SetDuLieuTable(titleTable, dataTable, tblBangKhachHang);
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField14 = new javax.swing.JTextField();
        left = new javax.swing.JPanel();
        icon = new javax.swing.JLabel();
        tabTaiKhoan = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tabSanPham = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        tabKhachHang = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        tabHoaDon = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        tabBanHang = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        dongke = new javax.swing.JSeparator();
        tabDangXuat = new javax.swing.JPanel();
        lblDangXuat = new javax.swing.JLabel();
        right = new javax.swing.JPanel();
        jpTaiKhoan = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtUsename = new javax.swing.JTextField();
        txtEmailTK = new javax.swing.JTextField();
        cboGioiTinh = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtSoDienThoaiTK = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtNgaySinhTK = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtDiaChiTK = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        txtSoCCCDTK = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jpSanPham = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtSanPhamMaSach = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtSanPhamTenSach = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtSanPhamDonGiaBan = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtSanPhamNhaXuatBan = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtSanPhamSoLuongTon = new javax.swing.JTextField();
        txtSanPhamTacGia = new javax.swing.JTextField();
        txtSanPhamTheLoai = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        txtSanPhamTimKiem = new javax.swing.JTextField();
        btnSanPhamTimKiem = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        txtSanPhamMoTa = new javax.swing.JTextField();
        jpKhachHang = new javax.swing.JPanel();
        btnTimKiemKhachHang = new javax.swing.JButton();
        txtTimKiemKhachHang = new javax.swing.JTextField();
        btnLamMoiKhachHang = new javax.swing.JButton();
        btnXoaKhachHang = new javax.swing.JButton();
        btnSuaKhachHang = new javax.swing.JButton();
        btnThemKhachHang = new javax.swing.JButton();
        txtNgaySinhKhachHang = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblBangKhachHang = new javax.swing.JTable();
        jLabel39 = new javax.swing.JLabel();
        txtTenKhachHang = new javax.swing.JTextField();
        txtMaKhachHang = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        txtDiaChiKhachHang = new javax.swing.JTextField();
        txtEmailKhachHang = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        txtSoDienThoaiKhachHang = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        txtSoCCCDKhachHang = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        btnLuuKhachHang = new javax.swing.JButton();
        txtGioiTinhKhachHang = new javax.swing.JTextField();
        jpHoaDon = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblBangDH = new javax.swing.JTable();
        btnTimKiemDH = new javax.swing.JButton();
        txtTimKiemDH = new javax.swing.JTextField();
        jpBanHang = new javax.swing.JPanel();
        btnBHThem = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDonHang = new javax.swing.JTable();
        btnBHLamMoi = new javax.swing.JButton();
        jpThongTinNhanVien = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblTenNhanVien = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblNgayBan = new javax.swing.JLabel();
        btnThanhToan = new javax.swing.JButton();
        btnBHHoaDonMoi = new javax.swing.JButton();
        btnBHXuatHoaDon = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblSachBH = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblChiTietDH = new javax.swing.JTable();
        txtTimSachBan = new javax.swing.JTextField();
        btnTimSachBan = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        txtTenSach = new javax.swing.JTextField();
        txtMaSach = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        txtSoLuongSach = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        txtMaSachCTDH = new javax.swing.JTextField();
        txtMaHoaDonCTDH = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        txtTongTienCTDH = new javax.swing.JTextField();
        txtSoLuongCTDH = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel61 = new javax.swing.JLabel();
        txtNgayMuaDH = new javax.swing.JTextField();
        txtMaDon = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        txtMaNhanVienDH = new javax.swing.JTextField();
        txtTrangThaiDH = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        txtThanhTienDH = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        cboKHDH = new javax.swing.JComboBox<>();
        btnBHLuu = new javax.swing.JButton();

        jTextField14.setText("jTextField14");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        left.setBackground(new java.awt.Color(51, 51, 51));

        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RES/book-stack.png"))); // NOI18N

        tabTaiKhoan.setBackground(new java.awt.Color(255, 153, 153));
        tabTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabTaiKhoanMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RES/add-user.png"))); // NOI18N
        jLabel1.setText("Tài Khoản");

        javax.swing.GroupLayout tabTaiKhoanLayout = new javax.swing.GroupLayout(tabTaiKhoan);
        tabTaiKhoan.setLayout(tabTaiKhoanLayout);
        tabTaiKhoanLayout.setHorizontalGroup(
            tabTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabTaiKhoanLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tabTaiKhoanLayout.setVerticalGroup(
            tabTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabTaiKhoanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabSanPham.setBackground(new java.awt.Color(255, 153, 153));
        tabSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabSanPhamMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RES/icons8-extra-features-32.png"))); // NOI18N
        jLabel3.setText("Sản Phẩm");

        javax.swing.GroupLayout tabSanPhamLayout = new javax.swing.GroupLayout(tabSanPham);
        tabSanPham.setLayout(tabSanPhamLayout);
        tabSanPhamLayout.setHorizontalGroup(
            tabSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabSanPhamLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(33, 33, 33))
        );
        tabSanPhamLayout.setVerticalGroup(
            tabSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabKhachHang.setBackground(new java.awt.Color(255, 153, 153));
        tabKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabKhachHangMouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RES/icons8-people-50.png"))); // NOI18N
        jLabel4.setText("Khách Hàng");

        javax.swing.GroupLayout tabKhachHangLayout = new javax.swing.GroupLayout(tabKhachHang);
        tabKhachHang.setLayout(tabKhachHangLayout);
        tabKhachHangLayout.setHorizontalGroup(
            tabKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabKhachHangLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(20, 20, 20))
        );
        tabKhachHangLayout.setVerticalGroup(
            tabKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabKhachHangLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addContainerGap())
        );

        tabHoaDon.setBackground(new java.awt.Color(255, 153, 153));
        tabHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabHoaDonMouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RES/icons8-bill-24.png"))); // NOI18N
        jLabel6.setText("Hóa Đơn");

        javax.swing.GroupLayout tabHoaDonLayout = new javax.swing.GroupLayout(tabHoaDon);
        tabHoaDon.setLayout(tabHoaDonLayout);
        tabHoaDonLayout.setHorizontalGroup(
            tabHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabHoaDonLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tabHoaDonLayout.setVerticalGroup(
            tabHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabHoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabBanHang.setBackground(new java.awt.Color(255, 153, 153));
        tabBanHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabBanHangMouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RES/icons8-sell-50.png"))); // NOI18N
        jLabel8.setText("Bán Hàng");

        javax.swing.GroupLayout tabBanHangLayout = new javax.swing.GroupLayout(tabBanHang);
        tabBanHang.setLayout(tabBanHangLayout);
        tabBanHangLayout.setHorizontalGroup(
            tabBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabBanHangLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(33, 33, 33))
        );
        tabBanHangLayout.setVerticalGroup(
            tabBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabBanHangLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addContainerGap())
        );

        tabDangXuat.setBackground(new java.awt.Color(255, 153, 153));
        tabDangXuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabDangXuatMouseClicked(evt);
            }
        });

        lblDangXuat.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RES/icons8-log-out-24.png"))); // NOI18N
        lblDangXuat.setText("Đăng Xuất");
        lblDangXuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDangXuatMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout tabDangXuatLayout = new javax.swing.GroupLayout(tabDangXuat);
        tabDangXuat.setLayout(tabDangXuatLayout);
        tabDangXuatLayout.setHorizontalGroup(
            tabDangXuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabDangXuatLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(lblDangXuat)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tabDangXuatLayout.setVerticalGroup(
            tabDangXuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabDangXuatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDangXuat)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout leftLayout = new javax.swing.GroupLayout(left);
        left.setLayout(leftLayout);
        leftLayout.setHorizontalGroup(
            leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(icon)
                .addContainerGap(8, Short.MAX_VALUE))
            .addComponent(tabTaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabBanHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabDangXuat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabSanPham, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabKhachHang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(dongke, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        leftLayout.setVerticalGroup(
            leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftLayout.createSequentialGroup()
                .addComponent(icon, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(dongke, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tabDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );

        right.setBackground(new java.awt.Color(102, 102, 102));
        right.setLayout(new javax.swing.OverlayLayout(right));

        jpTaiKhoan.setBackground(new java.awt.Color(102, 102, 102));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Thông tin cá nhân");

        txtUsename.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        txtEmailTK.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        cboGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nữ", "Nam" }));

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Email:");

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Số điện thoại:");

        txtSoDienThoaiTK.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Họ tên:");

        txtNgaySinhTK.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Địa chỉ:");

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Ngày sinh:");

        txtDiaChiTK.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Giới tính:");

        txtHoTen.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        txtSoCCCDTK.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Số CCCD:");

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Tài Khoản:");

        javax.swing.GroupLayout jpTaiKhoanLayout = new javax.swing.GroupLayout(jpTaiKhoan);
        jpTaiKhoan.setLayout(jpTaiKhoanLayout);
        jpTaiKhoanLayout.setHorizontalGroup(
            jpTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTaiKhoanLayout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addGroup(jpTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(jpTaiKhoanLayout.createSequentialGroup()
                        .addGroup(jpTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel17))
                        .addGap(34, 34, 34)))
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jpTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTaiKhoanLayout.createSequentialGroup()
                        .addComponent(txtUsename, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14)
                        .addGap(77, 77, 77)
                        .addComponent(txtEmailTK, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTaiKhoanLayout.createSequentialGroup()
                        .addGroup(jpTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSoDienThoaiTK, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jpTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpTaiKhoanLayout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(43, 43, 43)
                                .addComponent(txtSoCCCDTK, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpTaiKhoanLayout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(43, 43, 43)
                                .addComponent(txtNgaySinhTK, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtDiaChiTK, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTaiKhoanLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(249, 249, 249)))
                .addGap(190, 190, 190))
        );
        jpTaiKhoanLayout.setVerticalGroup(
            jpTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTaiKhoanLayout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jLabel2)
                .addGap(56, 56, 56)
                .addGroup(jpTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpTaiKhoanLayout.createSequentialGroup()
                        .addGroup(jpTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpTaiKhoanLayout.createSequentialGroup()
                                .addGroup(jpTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(txtEmailTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jpTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16)
                                    .addComponent(txtNgaySinhTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jpTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel18)
                                    .addComponent(txtSoCCCDTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jpTaiKhoanLayout.createSequentialGroup()
                                .addComponent(txtUsename, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(34, 34, 34)
                        .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpTaiKhoanLayout.createSequentialGroup()
                        .addGroup(jpTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpTaiKhoanLayout.createSequentialGroup()
                                .addGap(94, 94, 94)
                                .addGroup(jpTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(txtSoDienThoaiTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jpTaiKhoanLayout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(28, 28, 28)
                                .addComponent(jLabel12)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17)
                        .addGap(37, 37, 37)
                        .addGroup(jpTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtDiaChiTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(80, Short.MAX_VALUE))
        );

        right.add(jpTaiKhoan);

        jpSanPham.setBackground(new java.awt.Color(102, 102, 102));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Mã sách:");

        txtSanPhamMaSach.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtSanPhamMaSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSanPhamMaSachActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Tên sách:");

        txtSanPhamTenSach.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtSanPhamTenSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSanPhamTenSachActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Đơn giá bán:");

        txtSanPhamDonGiaBan.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Nhà xuất bản:");

        txtSanPhamNhaXuatBan.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtSanPhamNhaXuatBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSanPhamNhaXuatBanActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Số lượng tồn:");

        txtSanPhamSoLuongTon.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        txtSanPhamTacGia.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        txtSanPhamTheLoai.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel35.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("Thể loại:");

        jLabel36.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("Tác giả:");

        tblSanPham.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã sách", "Tên sách", "Đơn giá bán", "Số lượng tồn", "Mô tả", "Nhà xuất bản", "Thể loại", "Tác giả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

        txtSanPhamTimKiem.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        btnSanPhamTimKiem.setBackground(new java.awt.Color(0, 153, 153));
        btnSanPhamTimKiem.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnSanPhamTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RES/icons8-find-24.png"))); // NOI18N
        btnSanPhamTimKiem.setText("Tìm kiếm");
        btnSanPhamTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSanPhamTimKiemActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Mô tả:");

        txtSanPhamMoTa.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        javax.swing.GroupLayout jpSanPhamLayout = new javax.swing.GroupLayout(jpSanPham);
        jpSanPham.setLayout(jpSanPhamLayout);
        jpSanPhamLayout.setHorizontalGroup(
            jpSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jpSanPhamLayout.createSequentialGroup()
                .addGroup(jpSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpSanPhamLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jpSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(jLabel25)
                            .addComponent(jLabel19)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jpSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSanPhamSoLuongTon)
                            .addComponent(txtSanPhamDonGiaBan)
                            .addComponent(txtSanPhamTenSach)
                            .addComponent(txtSanPhamMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(102, 102, 102)
                        .addGroup(jpSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpSanPhamLayout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addGap(6, 6, 6))
                            .addComponent(txtSanPhamTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpSanPhamLayout.createSequentialGroup()
                                .addGroup(jpSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel35)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel36))
                                .addGroup(jpSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpSanPhamLayout.createSequentialGroup()
                                        .addGap(71, 71, 71)
                                        .addComponent(txtSanPhamMoTa, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jpSanPhamLayout.createSequentialGroup()
                                        .addGap(70, 70, 70)
                                        .addGroup(jpSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtSanPhamNhaXuatBan, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                                            .addComponent(txtSanPhamTheLoai)))))))
                    .addGroup(jpSanPhamLayout.createSequentialGroup()
                        .addGap(217, 217, 217)
                        .addComponent(txtSanPhamTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(btnSanPhamTimKiem)))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jpSanPhamLayout.setVerticalGroup(
            jpSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpSanPhamLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jpSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtSanPhamMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(txtSanPhamNhaXuatBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSanPhamTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel35)
                    .addComponent(txtSanPhamTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtSanPhamDonGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36)
                    .addComponent(txtSanPhamTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSanPhamSoLuongTon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26)
                    .addComponent(txtSanPhamMoTa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(jpSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSanPhamTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSanPhamTimKiem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        right.add(jpSanPham);

        jpKhachHang.setBackground(new java.awt.Color(102, 102, 102));

        btnTimKiemKhachHang.setBackground(new java.awt.Color(0, 153, 153));
        btnTimKiemKhachHang.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnTimKiemKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RES/icons8-find-24.png"))); // NOI18N
        btnTimKiemKhachHang.setText("Tìm kiếm");
        btnTimKiemKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemKhachHangActionPerformed(evt);
            }
        });

        txtTimKiemKhachHang.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        btnLamMoiKhachHang.setBackground(new java.awt.Color(0, 153, 153));
        btnLamMoiKhachHang.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnLamMoiKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RES/icons8-refresh-32.png"))); // NOI18N
        btnLamMoiKhachHang.setText("Làm mới");
        btnLamMoiKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiKhachHangActionPerformed(evt);
            }
        });

        btnXoaKhachHang.setBackground(new java.awt.Color(0, 153, 153));
        btnXoaKhachHang.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnXoaKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RES/icons8-delete-trash-24.png"))); // NOI18N
        btnXoaKhachHang.setText("Xóa");
        btnXoaKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKhachHangActionPerformed(evt);
            }
        });

        btnSuaKhachHang.setBackground(new java.awt.Color(0, 153, 153));
        btnSuaKhachHang.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnSuaKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RES/icons8-tools-24.png"))); // NOI18N
        btnSuaKhachHang.setText("Sửa");
        btnSuaKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaKhachHangActionPerformed(evt);
            }
        });

        btnThemKhachHang.setBackground(new java.awt.Color(0, 153, 153));
        btnThemKhachHang.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnThemKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RES/icons8-add-24.png"))); // NOI18N
        btnThemKhachHang.setText("Thêm");
        btnThemKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKhachHangActionPerformed(evt);
            }
        });

        txtNgaySinhKhachHang.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel38.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Ngày sinh:");

        tblBangKhachHang.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblBangKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã khách hàng", "Tên khách hàng", "Ngày sinh", "Giới tính", "Email", "Địa chỉ", "Số điện thoại", "Số CCCD", "User name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBangKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBangKhachHangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblBangKhachHang);

        jLabel39.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Tên khách hàng:");

        txtTenKhachHang.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        txtMaKhachHang.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel40.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Mã khách hàng:");

        txtDiaChiKhachHang.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        txtEmailKhachHang.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel41.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Email:");

        txtSoDienThoaiKhachHang.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel42.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Số điện thoại:");

        jLabel43.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("Địa chỉ:");

        txtSoCCCDKhachHang.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel44.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("Giới tính:");

        jLabel45.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("Số CCCD:");

        btnLuuKhachHang.setBackground(new java.awt.Color(0, 153, 153));
        btnLuuKhachHang.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnLuuKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RES/icons8-save-24.png"))); // NOI18N
        btnLuuKhachHang.setText("Lưu");
        btnLuuKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuKhachHangActionPerformed(evt);
            }
        });

        txtGioiTinhKhachHang.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        javax.swing.GroupLayout jpKhachHangLayout = new javax.swing.GroupLayout(jpKhachHang);
        jpKhachHang.setLayout(jpKhachHangLayout);
        jpKhachHangLayout.setHorizontalGroup(
            jpKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpKhachHangLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtTimKiemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(btnTimKiemKhachHang)
                .addGap(289, 289, 289))
            .addGroup(jpKhachHangLayout.createSequentialGroup()
                .addGap(184, 184, 184)
                .addComponent(btnThemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(btnSuaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(btnXoaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btnLuuKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addComponent(btnLamMoiKhachHang)
                .addGap(86, 86, 86))
            .addGroup(jpKhachHangLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jpKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel40)
                    .addComponent(jLabel39)
                    .addComponent(jLabel38)
                    .addComponent(jLabel44))
                .addGap(18, 18, 18)
                .addGroup(jpKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgaySinhKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGioiTinhKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpKhachHangLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel41))
                    .addComponent(jLabel43)
                    .addComponent(jLabel42)
                    .addComponent(jLabel45))
                .addGap(28, 28, 28)
                .addGroup(jpKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDiaChiKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmailKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoDienThoaiKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoCCCDKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
        );
        jpKhachHangLayout.setVerticalGroup(
            jpKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpKhachHangLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jpKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpKhachHangLayout.createSequentialGroup()
                        .addGroup(jpKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel40)
                            .addComponent(txtMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jpKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel39)
                            .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel43))
                        .addGap(18, 18, 18)
                        .addGroup(jpKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(txtNgaySinhKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel42)
                            .addComponent(txtSoDienThoaiKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jpKhachHangLayout.createSequentialGroup()
                        .addGroup(jpKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel41)
                            .addComponent(txtEmailKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(txtDiaChiKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addGroup(jpKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(jLabel45)
                    .addComponent(txtSoCCCDKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGioiTinhKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68)
                .addGroup(jpKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemKhachHang)
                    .addComponent(btnSuaKhachHang)
                    .addComponent(btnXoaKhachHang)
                    .addComponent(btnLamMoiKhachHang)
                    .addComponent(btnLuuKhachHang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiemKhachHang))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE))
        );

        right.add(jpKhachHang);

        jpHoaDon.setBackground(new java.awt.Color(102, 102, 102));
        jpHoaDon.setPreferredSize(new java.awt.Dimension(774, 543));

        tblBangDH.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblBangDH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã đơn hàng", "Ngày mua", "Thành tiền", "Tên khách hàng", "Tên nhân viên", "Loại đơn hàng", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBangDH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBangDHMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblBangDH);

        btnTimKiemDH.setBackground(new java.awt.Color(0, 153, 153));
        btnTimKiemDH.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnTimKiemDH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RES/icons8-find-24.png"))); // NOI18N
        btnTimKiemDH.setText("Tìm kiếm");
        btnTimKiemDH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemDHActionPerformed(evt);
            }
        });

        txtTimKiemDH.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        javax.swing.GroupLayout jpHoaDonLayout = new javax.swing.GroupLayout(jpHoaDon);
        jpHoaDon.setLayout(jpHoaDonLayout);
        jpHoaDonLayout.setHorizontalGroup(
            jpHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
            .addGroup(jpHoaDonLayout.createSequentialGroup()
                .addGap(229, 229, 229)
                .addComponent(txtTimKiemDH, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(btnTimKiemDH)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpHoaDonLayout.setVerticalGroup(
            jpHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpHoaDonLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jpHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiemDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiemDH))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE))
        );

        right.add(jpHoaDon);

        jpBanHang.setBackground(new java.awt.Color(120, 120, 120));
        jpBanHang.setToolTipText("");

        btnBHThem.setBackground(new java.awt.Color(0, 153, 153));
        btnBHThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RES/icons8-add-24.png"))); // NOI18N
        btnBHThem.setText("Thêm CTDH");

        tblDonHang.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblDonHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã đơn", "Ngày mua", "Thành tiền", "Trạng thái", "Mã nhân viên", "Mã khách hàng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDonHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDonHangMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblDonHang);

        btnBHLamMoi.setBackground(new java.awt.Color(0, 153, 153));
        btnBHLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RES/icons8-refresh-32.png"))); // NOI18N
        btnBHLamMoi.setText("Làm mới");
        btnBHLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBHLamMoiActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel7.setText("Tên Nhân Viên:");

        lblTenNhanVien.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel9.setText("Ngày Bán:");

        lblNgayBan.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        javax.swing.GroupLayout jpThongTinNhanVienLayout = new javax.swing.GroupLayout(jpThongTinNhanVien);
        jpThongTinNhanVien.setLayout(jpThongTinNhanVienLayout);
        jpThongTinNhanVienLayout.setHorizontalGroup(
            jpThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpThongTinNhanVienLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jpThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpThongTinNhanVienLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(34, 34, 34)
                        .addComponent(lblNgayBan, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE))
                    .addGroup(jpThongTinNhanVienLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTenNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpThongTinNhanVienLayout.setVerticalGroup(
            jpThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpThongTinNhanVienLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTenNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNgayBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btnThanhToan.setBackground(new java.awt.Color(0, 153, 153));
        btnThanhToan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RES/icons8-sell-50.png"))); // NOI18N
        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        btnBHHoaDonMoi.setBackground(new java.awt.Color(0, 153, 153));
        btnBHHoaDonMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RES/icons8-add-properties-24.png"))); // NOI18N
        btnBHHoaDonMoi.setText("Hóa đơn mới");
        btnBHHoaDonMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBHHoaDonMoiActionPerformed(evt);
            }
        });

        btnBHXuatHoaDon.setBackground(new java.awt.Color(0, 153, 153));
        btnBHXuatHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RES/icons8-printer-32.png"))); // NOI18N
        btnBHXuatHoaDon.setText("Xuất hóa đơn");
        btnBHXuatHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBHXuatHoaDonActionPerformed(evt);
            }
        });

        tblSachBH.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblSachBH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã sách", "Tên sách", "Số lượng", "Đơn giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSachBH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSachBHMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblSachBH);

        tblChiTietDH.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblChiTietDH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã Đơn Hàng", "Mã Sách", "Số Lượng", "Tổng Tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblChiTietDH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChiTietDHMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblChiTietDH);

        btnTimSachBan.setBackground(new java.awt.Color(0, 153, 153));
        btnTimSachBan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RES/icons8-find-24.png"))); // NOI18N
        btnTimSachBan.setText("Tìm");
        btnTimSachBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimSachBanActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Sách"));

        jLabel49.setText("Tên Sách:");

        txtTenSach.setEnabled(false);

        txtMaSach.setEnabled(false);

        jLabel50.setText("Mã Sách:");

        txtSoLuongSach.setEnabled(false);

        jLabel60.setText("Số Lượng:");

        txtDonGia.setEnabled(false);

        jLabel54.setText("Đơn Giá:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel50)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel49)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel54)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel60)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                        .addComponent(txtSoLuongSach, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60)
                    .addComponent(txtSoLuongSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Chi Tiết Đơn Hàng"));

        jLabel55.setText("Mã Sách:");

        txtMaSachCTDH.setEnabled(false);

        txtMaHoaDonCTDH.setEnabled(false);

        jLabel56.setText("Mã Đơn Hàng:");

        jLabel57.setText("Tổng Tiền:");

        txtTongTienCTDH.setEnabled(false);

        jLabel58.setText("Số Lượng:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel56)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtMaHoaDonCTDH, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel57)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTongTienCTDH, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel58)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtSoLuongCTDH, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel55)
                                .addGap(40, 40, 40)
                                .addComponent(txtMaSachCTDH, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(txtMaHoaDonCTDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(txtMaSachCTDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(txtSoLuongCTDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(txtTongTienCTDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Đơn Hàng"));

        jLabel61.setText("Ngày Mua:");

        txtNgayMuaDH.setEnabled(false);
        txtNgayMuaDH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayMuaDHActionPerformed(evt);
            }
        });

        txtMaDon.setEnabled(false);

        jLabel62.setText("Mã Đơn:");

        jLabel63.setText("Mã Nhân Viên:");

        txtMaNhanVienDH.setEnabled(false);

        txtTrangThaiDH.setEnabled(false);

        jLabel64.setText("Trạng Thái:");

        txtThanhTienDH.setEnabled(false);

        jLabel65.setText("Thành Tiền:");

        jLabel66.setText("Mã Khách Hàng");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel62)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtMaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel63)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtMaNhanVienDH, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel64)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTrangThaiDH, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel61)
                        .addGap(40, 40, 40)
                        .addComponent(txtNgayMuaDH, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel65)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtThanhTienDH, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel66)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboKHDH, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel62)
                    .addComponent(txtMaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel61)
                    .addComponent(txtNgayMuaDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel65)
                    .addComponent(txtThanhTienDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(txtTrangThaiDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaNhanVienDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel63))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66)
                    .addComponent(cboKHDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        btnBHLuu.setBackground(new java.awt.Color(0, 153, 153));
        btnBHLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RES/icons8-save-24.png"))); // NOI18N
        btnBHLuu.setText("Lưu");
        btnBHLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBHLuuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpBanHangLayout = new javax.swing.GroupLayout(jpBanHang);
        jpBanHang.setLayout(jpBanHangLayout);
        jpBanHangLayout.setHorizontalGroup(
            jpBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBanHangLayout.createSequentialGroup()
                .addGroup(jpBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBanHangLayout.createSequentialGroup()
                        .addGap(0, 42, Short.MAX_VALUE)
                        .addGroup(jpBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jpBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnBHLamMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnBHXuatHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jpBanHangLayout.createSequentialGroup()
                                    .addComponent(txtTimSachBan, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnTimSachBan))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpBanHangLayout.createSequentialGroup()
                        .addGroup(jpBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jpThongTinNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpBanHangLayout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jpBanHangLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jpBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBHLuu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBHHoaDonMoi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(162, 162, 162)
                        .addGroup(jpBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnBHThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(135, 135, 135)))
                .addContainerGap())
        );
        jpBanHangLayout.setVerticalGroup(
            jpBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBanHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpThongTinNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addGroup(jpBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpBanHangLayout.createSequentialGroup()
                        .addGroup(jpBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBHThem)
                            .addComponent(btnBHHoaDonMoi))
                        .addGap(18, 18, 18)
                        .addGroup(jpBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThanhToan)
                            .addComponent(btnBHLuu))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                        .addGroup(jpBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTimSachBan)
                            .addComponent(txtTimSachBan, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(jpBanHangLayout.createSequentialGroup()
                        .addComponent(btnBHLamMoi)
                        .addGap(18, 18, 18)
                        .addComponent(btnBHXuatHoaDon)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        right.add(jpBanHang);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(left, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(right, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(left, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(right, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabTaiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabTaiKhoanMouseClicked
        // TODO add your handling code here:
        jpTaiKhoan.setVisible(true);
        jpSanPham.setVisible(false);
        jpKhachHang.setVisible(false);
        jpHoaDon.setVisible(false);
        jpBanHang.setVisible(false);
        tabTaiKhoan.setBackground(Color.white);
        tabSanPham.setBackground(new Color(255, 153, 153));
        tabKhachHang.setBackground(new Color(255, 153, 153));
        tabHoaDon.setBackground(new Color(255, 153, 153));
        tabBanHang.setBackground(new Color(255, 153, 153));
    }//GEN-LAST:event_tabTaiKhoanMouseClicked

    private void tabSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabSanPhamMouseClicked
        // TODO add your handling code here:
        jpSanPham.setVisible(true);
        jpTaiKhoan.setVisible(false);
        jpKhachHang.setVisible(false);
        jpHoaDon.setVisible(false);
        jpBanHang.setVisible(false);
        tabSanPham.setBackground(Color.white);
        tabTaiKhoan.setBackground(new Color(255, 153, 153));
        tabKhachHang.setBackground(new Color(255, 153, 153));
        tabHoaDon.setBackground(new Color(255, 153, 153));
        tabBanHang.setBackground(new Color(255, 153, 153));
    }//GEN-LAST:event_tabSanPhamMouseClicked

    private void tabKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabKhachHangMouseClicked
        // TODO add your handling code here:
        jpKhachHang.setVisible(true);
        jpSanPham.setVisible(false);
        jpTaiKhoan.setVisible(false);
        jpHoaDon.setVisible(false);
        jpBanHang.setVisible(false);
        tabKhachHang.setBackground(Color.white);
        tabSanPham.setBackground(new Color(255, 153, 153));
        tabTaiKhoan.setBackground(new Color(255, 153, 153));
        tabHoaDon.setBackground(new Color(255, 153, 153));
        tabBanHang.setBackground(new Color(255, 153, 153));
    }//GEN-LAST:event_tabKhachHangMouseClicked

    private void tabHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabHoaDonMouseClicked
        // TODO add your handling code here:
        jpHoaDon.setVisible(true);
        jpSanPham.setVisible(false);
        jpKhachHang.setVisible(false);
        jpTaiKhoan.setVisible(false);
        jpBanHang.setVisible(false);
        tabHoaDon.setBackground(Color.white);
        tabSanPham.setBackground(new Color(255, 153, 153));
        tabKhachHang.setBackground(new Color(255, 153, 153));
        tabTaiKhoan.setBackground(new Color(255, 153, 153));
        tabBanHang.setBackground(new Color(255, 153, 153));
    }//GEN-LAST:event_tabHoaDonMouseClicked

    private void tabBanHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabBanHangMouseClicked
        // TODO add your handling code here:
        jpBanHang.setVisible(true);
        jpSanPham.setVisible(false);
        jpKhachHang.setVisible(false);
        jpHoaDon.setVisible(false);
        jpTaiKhoan.setVisible(false);
        tabBanHang.setBackground(Color.white);
        tabSanPham.setBackground(new Color(255, 153, 153));
        tabKhachHang.setBackground(new Color(255, 153, 153));
        tabHoaDon.setBackground(new Color(255, 153, 153));
        tabTaiKhoan.setBackground(new Color(255, 153, 153));
    }//GEN-LAST:event_tabBanHangMouseClicked

    private void tabDangXuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabDangXuatMouseClicked
        // TODO add your handling code here:
        Mes.DangXuat(this);

    }//GEN-LAST:event_tabDangXuatMouseClicked

    private void txtSanPhamMaSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSanPhamMaSachActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSanPhamMaSachActionPerformed

    private void txtSanPhamTenSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSanPhamTenSachActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSanPhamTenSachActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        System.out.println("Chương trình đã kết thúc.");
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        Mes.Exit();
    }//GEN-LAST:event_formWindowClosing

    private void lblDangXuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangXuatMouseClicked
        // TODO add your handling code here:
        Mes.DangXuat(this);
    }//GEN-LAST:event_lblDangXuatMouseClicked

    public boolean KiemTraNhapKhachHang() {
        String tb = " Thông Báo Nhập!";
        if (Mes.boolJTextField(txtTenKhachHang) == true) {
            Mes.ThongBao(tb, "Chưa nhập Tên Khách Hàng");
            return false;
        }
        if (Mes.boolJTextField(txtDiaChiKhachHang) == true) {
            Mes.ThongBao(tb, "Chưa nhập Địa Chỉ Khách Hàng");
            return false;
        }
        if (Mes.boolJTextField(txtSoDienThoaiKhachHang) == true) {
            Mes.ThongBao(tb, "Chưa nhập SDT Khách Hàng");
            return false;
        }
        if (Mes.boolJTextField(txtEmailKhachHang) == true) {
            Mes.ThongBao(tb, "Chưa nhập Email Khách Hàng");
            return false;
        }
        if (Mes.boolJTextField(txtNgaySinhKhachHang) == true) {
            Mes.ThongBao(tb, "Chưa nhập Ngày Sinh Khách Hàng");
            return false;
        }
        if (Mes.boolInputDate(txtNgaySinhKhachHang.getText(), "dd-MM-yyyy") == false) {
            Mes.ThongBao(tb, "Vui lòng nhập đúng định dạng ngày: dd-MM-yyyy!");
            return false;
        }
        if (Mes.boolJTextField(txtSoCCCDKhachHang) == true) {
            Mes.ThongBao(tb, "Chưa nhập CCCD Khách Hàng");
            return false;
        }
        if (Mes.KiemTraCCCDNumber(txtSoCCCDKhachHang.getText()) == false) {
            Mes.ThongBao(tb, "Định dạng CCCD không đúng, 12 số");
            return false;
        }

        if (Mes.KiemTraEmail(txtEmailKhachHang.getText()) == false) {
            Mes.ThongBao(tb, "Định dạng email không đúng");
            return false;
        }
        if (Mes.KiemTraPhoneNumber(txtSoDienThoaiKhachHang.getText()) == false) {
            Mes.ThongBao(tb, "Định dạng SDT không đúng");
            return false;
        }
        return true;
    }

    private boolean kiemTraDLNhap(int ma) {
        String tb = " Thông Báo Định Dạng Nhâp!";
        if (khdao.KiemTraCCCD(ma, txtSoCCCDKhachHang.getText()) == false) {
            Mes.ThongBao(tb, "CCCD có trong hệ thống!");
            return false;
        }
        if (khdao.KiemTraSDT(ma, txtSoDienThoaiKhachHang.getText()) == false) {
            Mes.ThongBao(tb, "Số Điện Thoại có trong hệ thống!");
            return false;
        }
        if (khdao.KiemTraEmail(ma, txtEmailKhachHang.getText()) == false) {
            Mes.ThongBao(tb, "Email có trong hệ thống!");
            return false;
        }
        return true;
    }
    private void btnLuuKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuKhachHangActionPerformed
        // TODO add your handling code here:
        if (KiemTraNhapKhachHang() == false) {
            return;
        }
        String tenKH = txtTenKhachHang.getText().trim();
        String ngaySinhString = txtNgaySinhKhachHang.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate ngaySinh = LocalDate.parse(ngaySinhString, formatter);
        String gioiTinh = txtGioiTinhKhachHang.getText().trim();
        String email = txtEmailKhachHang.getText().trim();
        String diaChi = txtDiaChiKhachHang.getText().trim();
        String soDienThoai = txtSoDienThoaiKhachHang.getText().trim();
        String soCCCD = txtSoCCCDKhachHang.getText().trim();
        System.err.println(btnKhachHang);
        if (btnKhachHang.equals("Thêm") == true) {
            System.err.println(btnKhachHang);
            if (kiemTraDLNhap(0) == false) {
                System.err.println(btnKhachHang);
                return;
            }
            KhachHang employee = new KhachHang(0, tenKH, ngaySinh, gioiTinh, email, diaChi, soDienThoai, soCCCD);
            System.out.println(employee);
            if (khdao.ThemKhachHang(employee) == true) {
                Mes.ThongBao("Thêm Khách Hàng", "Thành Công");
            } else {
                Mes.ThongBao("Thêm Khách Hàng", "Không Thành Công");
                return;
            }

        } else if (btnKhachHang.equals("Sửa") == true) {
            int makh = Integer.parseInt(txtMaKhachHang.getText());
            System.out.println(makh);
            if (kiemTraDLNhap(makh) == false) {
                System.err.println("Sua");
                return;
            }
            KhachHang employee = new KhachHang(makh, tenKH, ngaySinh, gioiTinh, email, diaChi, soDienThoai, soCCCD);
            if (khdao.SuaKhachHang(employee) == true) {
                Mes.ThongBao("Cập Nhật", "Thành Công");
            } else {
                Mes.ThongBao("Cập Nhật", "Không Thành Công");
            }
        }
        ArrayList<KhachHang> ds = khdao.getDanhSachKH();
        SetDataTableKH(ds);
        Mes.SetEnibleJtextPanel(false, jpKhachHang);
        btnLuuKhachHang.setEnabled(false);
        txtGioiTinhKhachHang.setEnabled(false);
    }//GEN-LAST:event_btnLuuKhachHangActionPerformed

    private void btnXoaKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKhachHangActionPerformed
//         TODO add your handling code here:
        int ma = Integer.parseInt(txtMaKhachHang.getText());
        if (JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa tài khoản?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (khdao.XoaKhachHang(ma, tk) == true) {
                Mes.ThongBao("Thông báo Xoá", "Xoá Thành Công!");
            } else {
                Mes.ThongBao("Thông báo Xoá", "Xoá Không Thành Công!");
            }
        }
        ArrayList<KhachHang> ds = khdao.getDanhSachKH();
        SetDataTableKH(ds);
        btnKhachHang = "Không";
    }//GEN-LAST:event_btnXoaKhachHangActionPerformed

    private void btnThemKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKhachHangActionPerformed
        // TODO add your handling code here:
        Mes.SetEnibleJtextPanel(true, jpKhachHang);
        txtMaKhachHang.setEditable(false);
        txtMaKhachHang.setEnabled(false);
        Mes.SetDLJtextPanel("", jpKhachHang);
        btnKhachHang = "Thêm";
        btnLuuKhachHang.setEnabled(true);
        txtGioiTinhKhachHang.setEnabled(true);
    }//GEN-LAST:event_btnThemKhachHangActionPerformed

    private void btnSuaKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaKhachHangActionPerformed
        // TODO add your handling code here:
        if (Mes.boolJTextField(txtMaKhachHang) == true) {
            Mes.BaoLoi("Thông báo sửa!", "Vui lòng Chọn Khách Hàng!");
            btnLuuKhachHang.setEnabled(false);
            Mes.SetEnibleJtextPanel(false, jpKhachHang);
            return;
        }
        Mes.SetEnibleJtextPanel(true, jpKhachHang);
        txtMaKhachHang.setEditable(false);
        btnKhachHang = "Sửa";
        btnLuuKhachHang.setEnabled(true);
        txtTimKiemKhachHang.setEnabled(true);
        txtGioiTinhKhachHang.setEnabled(true);
    }//GEN-LAST:event_btnSuaKhachHangActionPerformed

    private void btnLamMoiKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiKhachHangActionPerformed
        // TODO add your handling code here:
        ArrayList<KhachHang> ds = khdao.getDanhSachKH();
        SetDataTableKH(ds);
        btnLuuKhachHang.setEnabled(false);
    }//GEN-LAST:event_btnLamMoiKhachHangActionPerformed

    private void tblBangKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBangKhachHangMouseClicked
        // TODO add your handling code here:
        int i = tblBangKhachHang.getSelectedRow();
        int maKH = (int) tblBangKhachHang.getValueAt(i, 0);
        String tenKH = tblBangKhachHang.getValueAt(i, 1).toString().trim();
        String ngaySinhString = tblBangKhachHang.getValueAt(i, 2).toString().trim();
        String gioiTinh = tblBangKhachHang.getValueAt(i, 3).toString().trim();
        String email = tblBangKhachHang.getValueAt(i, 4).toString().trim();
        String diaChi = tblBangKhachHang.getValueAt(i, 5).toString().trim();
        String soDienThoai = tblBangKhachHang.getValueAt(i, 6).toString().trim();
        String soCCCD = tblBangKhachHang.getValueAt(i, 7).toString().trim();
        //-----------
        txtMaKhachHang.setText(String.format("%d", maKH));
        txtTenKhachHang.setText(tenKH);
        txtDiaChiKhachHang.setText(diaChi);
        txtSoDienThoaiKhachHang.setText(soDienThoai);
        txtEmailKhachHang.setText(email);
        txtGioiTinhKhachHang.setText(gioiTinh);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ngaySinh = LocalDate.parse(ngaySinhString, formatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String ngaySinhFormatted = ngaySinh.format(outputFormatter);
        txtNgaySinhKhachHang.setText(ngaySinhFormatted);
        txtSoCCCDKhachHang.setText(soCCCD);
        Mes.SetEnibleJtextPanel(false, jpKhachHang);
    }//GEN-LAST:event_tblBangKhachHangMouseClicked

    private void btnTimKiemKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemKhachHangActionPerformed
        // TODO add your handling code here:
        ArrayList<KhachHang> ds = khdao.getDanhSachKHSearch(txtTimKiemKhachHang.getText());
        SetDataTableKH(ds);
    }//GEN-LAST:event_btnTimKiemKhachHangActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:
        int i = tblSanPham.getSelectedRow();
        int maSach = (int) tblSanPham.getValueAt(i, 0);
        String tenSach = tblSanPham.getValueAt(i, 1).toString().trim();
        String donGiaBan = tblSanPham.getValueAt(i, 2).toString().trim();
        String soLuongTon = tblSanPham.getValueAt(i, 3).toString().trim();
        String moTa = tblSanPham.getValueAt(i, 4).toString().trim();
        String tenNXB = tblSanPham.getValueAt(i, 5).toString().trim();
        String tenTL = tblSanPham.getValueAt(i, 6).toString().trim();
        String tenTG = tblSanPham.getValueAt(i, 7).toString().trim();
        //-----------
        txtSanPhamMaSach.setText(String.format("%d", maSach));
        txtSanPhamTenSach.setText(tenSach);
        txtSanPhamDonGiaBan.setText(donGiaBan);
        txtSanPhamSoLuongTon.setText(soLuongTon);
        txtSanPhamMoTa.setText(moTa);
        txtSanPhamNhaXuatBan.setText(tenNXB);
        txtSanPhamTheLoai.setText(tenTL);
        txtSanPhamTacGia.setText(tenTG);
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void txtSanPhamNhaXuatBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSanPhamNhaXuatBanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSanPhamNhaXuatBanActionPerformed

    private void btnSanPhamTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSanPhamTimKiemActionPerformed
        // TODO add your handling code here:
        ArrayList<LoadSanPham> ds = (new LoadSanPhamDAO()).getDanhSachSPSearch(txtSanPhamTimKiem.getText());
        SetDataTableSP(ds);
    }//GEN-LAST:event_btnSanPhamTimKiemActionPerformed

    private void btnTimSachBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimSachBanActionPerformed
        // TODO add your handling code here:
        ArrayList<BanHangSach> ds = (new BanHangSachDAO()).getBanHangSachSearch(txtTimSachBan.getText());
        SetDataTableBanHangSach(ds);

    }//GEN-LAST:event_btnTimSachBanActionPerformed

    private void tblSachBHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSachBHMouseClicked
        // TODO add your handling code here:
        int i = tblSachBH.getSelectedRow();
        int maSach = (int) tblSachBH.getValueAt(i, 0);
        String tenSach = tblSachBH.getValueAt(i, 1).toString().trim();
        String soLuongTon = tblSachBH.getValueAt(i, 2).toString().trim();
        String donGiaBan = tblSachBH.getValueAt(i, 3).toString().trim();
        //-----------
        txtMaSach.setText(String.format("%d", maSach));
        txtTenSach.setText(tenSach);
        txtDonGia.setText(donGiaBan);
        txtSoLuongSach.setText(soLuongTon);
    }//GEN-LAST:event_tblSachBHMouseClicked

    private void tblDonHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDonHangMouseClicked
        // TODO add your handling code here:
        int i = tblDonHang.getSelectedRow();
        int maDH = (int) tblDonHang.getValueAt(i, 0);
        String ngayMuaString = tblDonHang.getValueAt(i, 1).toString().trim();
        String thanhTien = tblDonHang.getValueAt(i, 2).toString().trim();
        String trangThai = tblDonHang.getValueAt(i, 3).toString().trim();
        String maNV = tblDonHang.getValueAt(i, 4).toString().trim();
        String maKH = tblDonHang.getValueAt(i, 5).toString().trim();
        //-----------
        txtMaDon.setText(String.format("%d", maDH));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ngayMua = LocalDate.parse(ngayMuaString, formatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String ngayMuaFormatted = ngayMua.format(outputFormatter);
        txtNgayMuaDH.setText(ngayMuaFormatted);
        txtThanhTienDH.setText(thanhTien);
        txtTrangThaiDH.setText(trangThai);
        txtMaNhanVienDH.setText(maNV);
//        txtMaKhachHangDH.setText(maKH);
    }//GEN-LAST:event_tblDonHangMouseClicked

    private void txtNgayMuaDHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayMuaDHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayMuaDHActionPerformed

    private void tblChiTietDHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTietDHMouseClicked
        // TODO add your handling code here:
        int i = tblChiTietDH.getSelectedRow();
        int maDH = (int) tblChiTietDH.getValueAt(i, 0);
        String maSach = tblChiTietDH.getValueAt(i, 1).toString().trim();
        String soLuong = tblChiTietDH.getValueAt(i, 2).toString().trim();
        String tongTien = tblChiTietDH.getValueAt(i, 3).toString().trim();
        //-----------
        txtMaHoaDonCTDH.setText(String.format("%d", maDH));
        txtMaSachCTDH.setText(maSach);
        txtSoLuongCTDH.setText(soLuong);
        txtTongTienCTDH.setText(tongTien);
    }//GEN-LAST:event_tblChiTietDHMouseClicked

    private void btnBHHoaDonMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBHHoaDonMoiActionPerformed
        // TODO add your handling code here:
        Mes.SetDLJtextPanel("", jPanel1);
        Mes.SetDLJtextPanel("", jPanel3);
        Mes.SetDLJtextPanel("", jPanel2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        txtNgayMuaDH.setText(LocalDate.now().format(formatter));
        txtMaNhanVienDH.setText(nhanvien.getMaNV() + "");
        txtTrangThaiDH.setText("Chưa thanh toán");
    }//GEN-LAST:event_btnBHHoaDonMoiActionPerformed

    private void btnBHLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBHLamMoiActionPerformed
        // TODO add your handling code here:
        PanelBanHang();
    }//GEN-LAST:event_btnBHLamMoiActionPerformed

    private void btnBHXuatHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBHXuatHoaDonActionPerformed
        // TODO add your handling code here:
        jpHoaDon.setVisible(true);
        jpSanPham.setVisible(false);
        jpKhachHang.setVisible(false);
        jpTaiKhoan.setVisible(false);
        jpBanHang.setVisible(false);
        tabHoaDon.setBackground(Color.white);
        tabSanPham.setBackground(new Color(255, 153, 153));
        tabKhachHang.setBackground(new Color(255, 153, 153));
        tabTaiKhoan.setBackground(new Color(255, 153, 153));
        tabBanHang.setBackground(new Color(255, 153, 153));
    }//GEN-LAST:event_btnBHXuatHoaDonActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        int rowDH = tblDonHang.getSelectedRow();
        if (rowDH < 0 || rowDH >= tblDonHang.getRowCount()) {
            JOptionPane.showMessageDialog(trangChuNhanVienBanHang, "Vui lòng chọn hóa đơn trước khi thêm chi tiết");
            return;
        }
        int rowSach = tblSachBH.getSelectedRow();
        if (rowSach < 0 || rowSach >= tblSachBH.getRowCount()) {
            JOptionPane.showMessageDialog(trangChuNhanVienBanHang, "Vui lòng chọn sách trước khi thêm chi tiết");
            return;
        }
        if (txtSoLuongCTDH.getText().length() <= 0) {
            JOptionPane.showMessageDialog(trangChuNhanVienBanHang, "Vui lòng chọn số lượng trước khi thêm chi tiết");
            return;
        }
        DonHangDAO.insertCTDH(Integer.parseInt(tblDonHang.getValueAt(rowDH, 0).toString()), Integer.parseInt(tblSachBH.getValueAt(rowSach, 0).toString()), Integer.parseInt(txtSoLuongCTDH.getText()));
        clearCTDH();
        int row = tblDonHang.getSelectedRow();
        if (row >= 0 && row < tblDonHang.getRowCount()) {
            tblDonHang.setValueAt((DonHangDAO.getDH(Integer.parseInt(tblDonHang.getValueAt(row, 0).toString()))).getThanhTien(), row, 2);
            ArrayList<ChiTietDonHang> dsctdh = ChiTietDonHangDAO.getChiTietDonHangBangMaDH(Integer.parseInt(tblDonHang.getValueAt(row, 0).toString()));
            SetDataTableCTDonHang(dsctdh);
            ComboBoxModel<Object> dskh = cboKHDH.getModel();
            int i = 0;
            for (; i < dskh.getSize(); i++) {
                if (tblDonHang.getValueAt(row, 5).toString().equals(((KhachHang) dskh.getElementAt(i)).getMaKH() + "")) {
                    break;
                }
            }
            cboKHDH.setSelectedIndex(i);
        }
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void tblBangDHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBangDHMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblBangDHMouseClicked

    private void btnTimKiemDHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemDHActionPerformed
        // TODO add your handling code here:
        ArrayList<DonHang> ds = (new DonHangDAO()).getDonHangSearch(txtTimKiemDH.getText());
        SetDataTableDonHang(ds);
    }//GEN-LAST:event_btnTimKiemDHActionPerformed

    private void btnBHLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBHLuuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBHLuuActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(fTrangChuNhanVienBanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(fTrangChuNhanVienBanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(fTrangChuNhanVienBanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(fTrangChuNhanVienBanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new fTrangChuNhanVienBanHang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBHHoaDonMoi;
    private javax.swing.JButton btnBHLamMoi;
    private javax.swing.JButton btnBHLuu;
    private javax.swing.JButton btnBHThem;
    private javax.swing.JButton btnBHXuatHoaDon;
    private javax.swing.JButton btnLamMoiKhachHang;
    private javax.swing.JButton btnLuuKhachHang;
    private javax.swing.JButton btnSanPhamTimKiem;
    private javax.swing.JButton btnSuaKhachHang;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThemKhachHang;
    private javax.swing.JButton btnTimKiemDH;
    private javax.swing.JButton btnTimKiemKhachHang;
    private javax.swing.JButton btnTimSachBan;
    private javax.swing.JButton btnXoaKhachHang;
    private javax.swing.JComboBox<String> cboGioiTinh;
    private javax.swing.JComboBox<Object> cboKHDH;
    private javax.swing.JSeparator dongke;
    private javax.swing.JLabel icon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JPanel jpBanHang;
    private javax.swing.JPanel jpHoaDon;
    private javax.swing.JPanel jpKhachHang;
    private javax.swing.JPanel jpSanPham;
    private javax.swing.JPanel jpTaiKhoan;
    private javax.swing.JPanel jpThongTinNhanVien;
    private javax.swing.JLabel lblDangXuat;
    private javax.swing.JLabel lblNgayBan;
    private javax.swing.JLabel lblTenNhanVien;
    private javax.swing.JPanel left;
    private javax.swing.JPanel right;
    private javax.swing.JPanel tabBanHang;
    private javax.swing.JPanel tabDangXuat;
    private javax.swing.JPanel tabHoaDon;
    private javax.swing.JPanel tabKhachHang;
    private javax.swing.JPanel tabSanPham;
    private javax.swing.JPanel tabTaiKhoan;
    private javax.swing.JTable tblBangDH;
    private javax.swing.JTable tblBangKhachHang;
    private javax.swing.JTable tblChiTietDH;
    private javax.swing.JTable tblDonHang;
    private javax.swing.JTable tblSachBH;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtDiaChiKhachHang;
    private javax.swing.JTextField txtDiaChiTK;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtEmailKhachHang;
    private javax.swing.JTextField txtEmailTK;
    private javax.swing.JTextField txtGioiTinhKhachHang;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaDon;
    private javax.swing.JTextField txtMaHoaDonCTDH;
    private javax.swing.JTextField txtMaKhachHang;
    private javax.swing.JTextField txtMaNhanVienDH;
    private javax.swing.JTextField txtMaSach;
    private javax.swing.JTextField txtMaSachCTDH;
    private javax.swing.JTextField txtNgayMuaDH;
    private javax.swing.JTextField txtNgaySinhKhachHang;
    private javax.swing.JTextField txtNgaySinhTK;
    private javax.swing.JTextField txtSanPhamDonGiaBan;
    private javax.swing.JTextField txtSanPhamMaSach;
    private javax.swing.JTextField txtSanPhamMoTa;
    private javax.swing.JTextField txtSanPhamNhaXuatBan;
    private javax.swing.JTextField txtSanPhamSoLuongTon;
    private javax.swing.JTextField txtSanPhamTacGia;
    private javax.swing.JTextField txtSanPhamTenSach;
    private javax.swing.JTextField txtSanPhamTheLoai;
    private javax.swing.JTextField txtSanPhamTimKiem;
    private javax.swing.JTextField txtSoCCCDKhachHang;
    private javax.swing.JTextField txtSoCCCDTK;
    private javax.swing.JTextField txtSoDienThoaiKhachHang;
    private javax.swing.JTextField txtSoDienThoaiTK;
    private javax.swing.JTextField txtSoLuongCTDH;
    private javax.swing.JTextField txtSoLuongSach;
    private javax.swing.JTextField txtTenKhachHang;
    private javax.swing.JTextField txtTenSach;
    private javax.swing.JTextField txtThanhTienDH;
    private javax.swing.JTextField txtTimKiemDH;
    private javax.swing.JTextField txtTimKiemKhachHang;
    private javax.swing.JTextField txtTimSachBan;
    private javax.swing.JTextField txtTongTienCTDH;
    private javax.swing.JTextField txtTrangThaiDH;
    private javax.swing.JTextField txtUsename;
    // End of variables declaration//GEN-END:variables
}
