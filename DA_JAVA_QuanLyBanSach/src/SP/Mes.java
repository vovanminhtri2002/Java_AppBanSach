/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SP;

import GUI.DangNhap;
import GUI.fTrangChuNhanVienBanHang;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Maitr
 */
public class Mes {

    public static void ThongBao(String tileBar, String info) {
        JOptionPane.showMessageDialog(null, info, tileBar, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void BaoLoi(String tileBar, String info) {
        JOptionPane.showMessageDialog(null, info, tileBar, JOptionPane.WARNING_MESSAGE);
    }

    public static void Exit() {
        JFrame frame = new JFrame();
        if (JOptionPane.showConfirmDialog(frame, "Bạn có muốn thoát?", "Thông báo", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    public static void DangXuat(fTrangChuNhanVienBanHang banhang) {
        JFrame frame = new JFrame();
        if (JOptionPane.showConfirmDialog(frame, "Bạn có muốn đăng xuất?", "Thông báo", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
        banhang.setVisible(false);
        new DangNhap().setVisible(true);
        }
    }

    public static void CloseFrom(JFrame j) {
        j.setVisible(false);
    }

    public static void OpenFrom(JFrame j) {
        j.setVisible(true);
        j.setLocationRelativeTo(null);
    }

    public static void XetChiNhapSoDouble(KeyEvent keyEvent) {
        JTextField textField = (JTextField) keyEvent.getComponent();
        String text = textField.getText();
        String newText = text + keyEvent.getKeyChar();
        if (!newText.matches("\\d*\\.?\\d*")) {
            keyEvent.consume();
        }
    }

    public static void XetChiNhapSoDoubleCoAm(KeyEvent keyEvent) {
        JTextField textField = (JTextField) keyEvent.getComponent();
        String text = textField.getText();
        String newText = text + keyEvent.getKeyChar();
        if (!newText.matches("-?\\d*\\.?\\d*")) {
            keyEvent.consume();
        }
    }

    public static void XetChiNhapSoInt(KeyEvent keyEvent) {
        // Chỉ sử dụng trong hàm keyTyped
        JTextField textField = (JTextField) keyEvent.getComponent();
        String text = textField.getText();
        String newText = text + keyEvent.getKeyChar();
        if (!newText.matches("\\d*")) {
            keyEvent.consume();
        }
    }

    // Chỉ sử dụng trong hàm keyTyped
    public static void XetChiNhapSoIntCoAm(KeyEvent keyEvent) {
        //   -? đại diện cho ký tự - (nếu có) để cho phép nhập số âm.
        //   \\d* đại diện cho một chuỗi bao gồm các ký tự số.
        JTextField textField = (JTextField) keyEvent.getComponent();
        String text = textField.getText();
        String newText = text + keyEvent.getKeyChar();
        if (!newText.matches("-?\\d*")) {
            keyEvent.consume();
        }
    }

    public static boolean boolJTextField(JTextField jTF) {
        return jTF.getText().isEmpty(); // null -> true  
    }

    public static boolean boolBangO(JTextField jTF) {
        double d = Double.parseDouble(jTF.getText());
        return d == 0;
    }

    // Jtable
    public static void SetDuLieuTable(Vector title, Vector dltable, JTable table) {
        table.setModel(new DefaultTableModel(dltable, title));
    }

    public static void FormatTable(Vector dlTable) {
        dlTable.removeAllElements();

    }

    public static void SetEnibleJtextPanel(boolean KQ, JPanel jp) {
        // true để mở    false để đóng
        Component[] components = jp.getComponents();
        for (Component component : components) {
            if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                textField.setEnabled(KQ);
            }
        }
        
    }

    public static void SetEditableJtextJPanel(boolean enable, JPanel panel) {
        // false vô hiệu hoá, true có thể chỉnh suả
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                textField.setEditable(enable);
            }
        }
    }
    public static void SetDLJtextPanel(String DuLieu, JPanel jp) {
        // true để mở    false để đóng
        Component[] components = jp.getComponents();
        for (Component component : components) {
            if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                textField.setText(DuLieu);
            }
        }  
    }
    public static boolean boolInputDate(String inputDate, String dateFormat){

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        try {
            sdf.parse(inputDate);
            System.out.println("Dữ liệu hợp lệ");
            return true;
        } catch (ParseException e) {
            System.out.println("Dữ liệu không hợp lệ");
            return false;
        }
    }

    public static boolean KiemTraEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex); // True -> Hợp lệ
    }

    public static boolean KiemTraPhoneNumber(String phoneNumber) {
        // Kiểm tra số điện thoại có 10-11 chữ số và chỉ chứa các ký tự số
        String phoneNumberRegex = "^[0-9]{10,11}$";
        return phoneNumber.matches(phoneNumberRegex);// True -> Hợp lệ
    }
        public static boolean KiemTraCCCDNumber(String cccd) {
        // Kiểm tra số điện thoại có 10-11 chữ số và chỉ chứa các ký tự số
        String ccNumberRegex = "^[0-9]{12}$";
        return cccd.matches(ccNumberRegex);// True -> Hợp lệ
    }
}
