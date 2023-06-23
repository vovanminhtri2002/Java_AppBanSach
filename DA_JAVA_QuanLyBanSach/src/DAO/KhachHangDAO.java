/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.KhachHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author HP
 */
public class KhachHangDAO {
    public ArrayList<KhachHang> getDanhSachKH()
        {
        ArrayList<KhachHang> dsKH = new ArrayList<>();
        ConnectionDB conet = new ConnectionDB();
        String query = "SELECT *FROM KHACHHANG;";

        try (Connection connection = conet.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            //preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int maKH = resultSet.getInt("MaKH");
                String tenKH = resultSet.getString("TenKH").trim();
                LocalDate ngaySinh = resultSet.getDate("NgaySinh").toLocalDate();
                String gioiTinh = resultSet.getString("GioiTinh").trim();
                String email = resultSet.getString("Email").trim();
                String diaChi = resultSet.getString("DiaChi").trim();
                String soDienThoai = resultSet.getString("SODIENTHOAI").trim();
                String soCCCD = resultSet.getString("SoCCCD").trim();
                KhachHang employee = new KhachHang(maKH,tenKH,ngaySinh,gioiTinh,email,diaChi,soDienThoai,soCCCD);
                dsKH.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsKH;
    }
    public ArrayList<KhachHang> getDanhSachKHSearch(String txtSearch) {

        ArrayList<KhachHang> dsKH = new ArrayList<>();
        ConnectionDB conet = new ConnectionDB();
        String query = "SELECT * FROM KHACHHANG WHERE TENKH LIKE N'%"+txtSearch+"%' "+ "OR SODIENTHOAI LIKE '%"+txtSearch+"%' \n" +
                "OR SOCCCD LIKE '%"+txtSearch+"%' \n" + "OR USERNAME LIKE '%"+txtSearch+"%' \n" +
                "OR EMAIL LIKE '%"+txtSearch+"%';";

        try (Connection connection = conet.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            //preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int maKH = resultSet.getInt("MAKH");
                String tenKH = resultSet.getString("TENKH").trim();
                LocalDate ngaySinh = resultSet.getDate("NGAYSINH").toLocalDate();
                String gioiTinh = resultSet.getString("GIOITINH").trim();
                String email = resultSet.getString("EMAIL").trim();
                String diaChi = resultSet.getString("DIACHI").trim();
                String soDienThoai = resultSet.getString("SODIENTHOAI").trim();
                String soCCCD = resultSet.getString("SOCCCD").trim();
                KhachHang employee = new KhachHang(maKH,tenKH,ngaySinh,gioiTinh,email,diaChi,soDienThoai,soCCCD);
                dsKH.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsKH;
    }
    public KhachHang KiemTraUsername(String username) {
        KhachHang employee = null;
        ConnectionDB conet = new ConnectionDB();
        String query = "SELECT * FROM KHACHHANG nv WHERE nv.USERNAME LIKE ? ";

        try (Connection connection = conet.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Lấy khách hàng thành công. Xin chào " + username);
                int maKH = resultSet.getInt("MaKH");
                String tenKH = resultSet.getString("TenKH").trim();
                LocalDate ngaySinh = resultSet.getDate("NgaySinh").toLocalDate();
                String gioiTinh = resultSet.getString("GioiTinh").trim();
                String email = resultSet.getString("Email").trim();
                String diaChi = resultSet.getString("DiaChi").trim();
                String soDienThoai = resultSet.getString("SODIENTHOAI").trim();
                String soCCCD = resultSet.getString("SoCCCD").trim();
                employee = new KhachHang(maKH,tenKH,ngaySinh,gioiTinh,email,diaChi,soDienThoai,soCCCD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }
    public boolean KiemTraEmail(int maKH, String email) {
        // Nếu true đúng
        // nếu false sai cần nhập lại
        ConnectionDB conet = new ConnectionDB();
        String query = "SELECT COUNT(*) AS DEM FROM KHACHHANG WHERE EMAIL = '" + email + "' AND MAKH <> " + maKH;

        try (Connection connection = conet.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Lay KQ Mail thanh cong ");
                int i = resultSet.getInt("DEM");
                if (i == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean KiemTraCCCD(int maKH, String cccd) {
        // Nếu true đúng
        // nếu false sai cần nhập lại
        ConnectionDB conet = new ConnectionDB();
        String query = "SELECT COUNT(*) AS DEM FROM KHACHHANG WHERE SOCCCD = '" + cccd + "' AND MAKH <> " + maKH;

        try (Connection connection = conet.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                int i = resultSet.getInt("DEM");
                System.out.println("Lay KQ CCCD thanh cong " + i);
                if (i == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean KiemTraSDT(int maKH, String sdt) {
        // Nếu true đúng
        // nếu false sai cần nhập lại
        ConnectionDB conet = new ConnectionDB();
        String query = "SELECT COUNT(*) AS DEM FROM KHACHHANG WHERE SODIENTHOAI = '" + sdt + "' AND MAKH <> " + maKH;

        try (Connection connection = conet.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                int i = resultSet.getInt("DEM");
                System.out.println("Lay KQ SDT thanh cong ");
                System.out.print(i);
                if (i == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean SuaKhachHang(KhachHang employee) {
    ConnectionDB conet = new ConnectionDB();
    String query = "UPDATE KHACHHANG SET TenKH = ?, NgaySinh = ?, GioiTinh = ?, Email = ?, DiaChi = ?, SODIENTHOAI = ?, SoCCCD = ? WHERE MaKH = ?";
    try (Connection connection = conet.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, employee.getTenKH());
        preparedStatement.setString(2, employee.getNgaySinh().toString());
        preparedStatement.setString(3, employee.getGioiTinh());
        preparedStatement.setString(4, employee.getEmail());
        preparedStatement.setString(5, employee.getDiaChi());
        preparedStatement.setString(6, employee.getSoDienThoai());
        preparedStatement.setString(7, employee.getSoCCCD());
        preparedStatement.setInt(8, employee.getMaKH());
        // Thực thi câu truy vấn UPDATE
        preparedStatement.executeUpdate();
        System.out.println("Cập nhật khách hàng thành công.");
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Cập nhật khách hàng không thành công.");
    }
    return false;

}

    public boolean XoaKhachHang(int maKH, String username) {
        ConnectionDB conet = new ConnectionDB();
        String xoaKhachHangQuery = "DELETE FROM KHACHHANG WHERE MaKH = ?";

        try (Connection connection = conet.getConnection(); PreparedStatement xoaKhachHangStatement = connection.prepareStatement(xoaKhachHangQuery);) {
            // Xóa nhân viên
            xoaKhachHangStatement.setInt(1, maKH);
            int khachHangRowsDeleted = xoaKhachHangStatement.executeUpdate();
            if (khachHangRowsDeleted > 0) {
                System.out.println("Xóa khách hàng thành công");
                // Xóa tài khoản
                return true;
            } else {
                System.out.println("Không tìm thấy khách hàng cần xóa");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi xóa khách hàng: " + e.getMessage());
            return false;
        }
    }

    public boolean ThemKhachHang(KhachHang employee) {
    ConnectionDB conet = new ConnectionDB();
    String query = "INSERT INTO KHACHHANG(TENKH, NGAYSINH, GIOITINH, EMAIL, DIACHI, SODIENTHOAI, SOCCCD) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (Connection connection = conet.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, employee.getTenKH());
        preparedStatement.setString(2, employee.getNgaySinh().toString());
        preparedStatement.setString(3, employee.getGioiTinh());
        preparedStatement.setString(4, employee.getEmail());
        preparedStatement.setString(5, employee.getDiaChi());
        preparedStatement.setString(6, employee.getSoDienThoai());
        preparedStatement.setString(7, employee.getSoCCCD());
        // Thực thi câu truy vấn INSERT
        preparedStatement.executeUpdate();
        System.out.println("Thêm khách hàng thành công.");
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Thêm khách hàng không thành công.");
        System.out.println(e);
        return false;
    }
}
}