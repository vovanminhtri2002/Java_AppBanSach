/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.NhanVien;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author Maitr
 */
public class NhanVienDAO {

    public ArrayList<NhanVien> getDanhSachNV() {

        ArrayList<NhanVien> dsNV = new ArrayList<>();
        ConnectionDB conet = new ConnectionDB();
        String query = "SELECT *FROM NhanVien;";

        try (Connection connection = conet.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            //preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int maNV = resultSet.getInt("MaNV");
                String tenNV = resultSet.getString("TenNV").trim();
                String diaChi = resultSet.getString("DiaChi").trim();
                String soDienThoai = resultSet.getString("SODIENTHOAI").trim();
                String email = resultSet.getString("Email").trim();
                String gioiTinh = resultSet.getString("GioiTinh").trim();
                LocalDate ngaySinh = resultSet.getDate("NgaySinh").toLocalDate();
                int maCV = resultSet.getInt("MaCV");
                String soCCCD = resultSet.getString("SoCCCD").trim();
                String tentk = resultSet.getString("username");
                NhanVien employee = new NhanVien(maNV, tenNV, diaChi, soDienThoai, email, gioiTinh, ngaySinh, maCV, soCCCD, tentk);
                dsNV.add(employee);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsNV;
    }

    public NhanVien getNhanVienQuaUsename(String username) {
        NhanVien employee = null;
        ConnectionDB conet = new ConnectionDB();
        String query = "SELECT * FROM NHANVIEN nv WHERE nv.USERNAME LIKE ? ";

        try (Connection connection = conet.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Lấy nhân viên thành công. Xin chào " + username);
                int maNV = resultSet.getInt("MaNV");
                String tenNV = resultSet.getString("TenNV").trim();
                String diaChi = resultSet.getString("DiaChi").trim();
                String soDienThoai = resultSet.getString("SODIENTHOAI").trim();
                String email = resultSet.getString("Email").trim();
                String gioiTinh = resultSet.getString("GioiTinh").trim();
                LocalDate ngaySinh = resultSet.getDate("NgaySinh").toLocalDate();
                int maCV = resultSet.getInt("MaCV");
                String soCCCD = resultSet.getString("SoCCCD").trim();
                String tentk = resultSet.getString("username").trim();
                employee = new NhanVien(maNV, tenNV, diaChi, soDienThoai, email, gioiTinh, ngaySinh, maCV, soCCCD, tentk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public boolean checkDuplicateEmail(int maKH, String email) {
        // Nếu true đúng
        // nếu false sai cần nhập lại
        ConnectionDB conet = new ConnectionDB();
        String query = "SELECT COUNT(*) AS DEM FROM NHANVIEN WHERE EMAIL = '" + email + "' AND MANV <> " + maKH;

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

    public boolean checkDuplicateCCCD(int maKH, String cccd) {
        // Nếu true đúng
        // nếu false sai cần nhập lại
        ConnectionDB conet = new ConnectionDB();
        String query = "SELECT COUNT(*) AS DEM FROM NHANVIEN WHERE SOCCCD = '" + cccd + "' AND MANV <> " + maKH;

        try (Connection connection = conet.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Lay KQ CCCD thanh cong ");
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

    public boolean checkDuplicateSDT(int maKH, String sdt) {
        // Nếu true đúng
        // nếu false sai cần nhập lại
        ConnectionDB conet = new ConnectionDB();
        String query = "SELECT COUNT(*) AS DEM FROM NHANVIEN WHERE SODIENTHOAI = '" + sdt + "' AND MANV <> " + maKH;

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

    public boolean checkDuplicateUsername(int maKH, String username) {
        ConnectionDB conet = new ConnectionDB();
        String query = "SELECT COUNT(*) AS DEM FROM NHANVIEN WHERE USERNAME = '" + username + "' AND MANV <> " + maKH;

        try (Connection connection = conet.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int i = resultSet.getInt("DEM");
                System.out.println("Lấy kết quả kiểm tra username thành công");
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
    
    public boolean updateNhanVien(NhanVien employee) {
        ConnectionDB conet = new ConnectionDB();
        String query = "UPDATE NHANVIEN SET TenNV = ?, DiaChi = ?, SODIENTHOAI = ?, Email = ?, GioiTinh = ?, NgaySinh = ?, MaCV = ?, SoCCCD = ?, username = ? WHERE MaNV = ?";

        try (Connection connection = conet.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, employee.getTenNV());
            preparedStatement.setString(2, employee.getDiaChi());
            preparedStatement.setString(3, employee.getSoDienThoai());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setString(5, employee.getGioiTinh());
            LocalDate ngaySinh = employee.getNgaySinh();
            preparedStatement.setString(6, ngaySinh.toString());
            preparedStatement.setInt(7, employee.getMaCV());
            preparedStatement.setString(8, employee.getSoCCCD());
            preparedStatement.setString(9, employee.getUsername());
            preparedStatement.setInt(10, employee.getMaNV());
            preparedStatement.executeUpdate();
            System.out.println("Cập nhật nhân viên thành công.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteNhanVien(int maNV, String username) {
        ConnectionDB conet = new ConnectionDB();
        String deleteNhanVienQuery = "DELETE FROM NHANVIEN WHERE MaNV = ?";
        String deleteTaiKhoanQuery = "DELETE FROM ACCOUNT WHERE USERNAME = ?";

        try (Connection connection = conet.getConnection(); 
                PreparedStatement deleteNhanVienStatement = connection.prepareStatement(deleteNhanVienQuery);
                PreparedStatement deleteTaiKhoanStatement = connection.prepareStatement(deleteTaiKhoanQuery)) {
            // Xóa nhân viên
            deleteNhanVienStatement.setInt(1, maNV);
            int nhanVienRowsDeleted = deleteNhanVienStatement.executeUpdate();
            if (nhanVienRowsDeleted > 0) {
                System.out.println("Xóa nhân viên thành công");
                // Xóa tài khoản
                deleteTaiKhoanStatement.setString(1, username);
                int taiKhoanRowsDeleted = deleteTaiKhoanStatement.executeUpdate();
                if (taiKhoanRowsDeleted > 0) {
                    System.out.println("Xóa tài khoản thành công");
                } else {
                    System.out.println("Không tìm thấy tài khoản cần xóa");
                }
                return true;
            } else {
                System.out.println("Không tìm thấy nhân viên cần xóa");
                 return false;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi xóa nhân viên và tài khoản: " + e.getMessage());
             return false;
        }
    }
}
