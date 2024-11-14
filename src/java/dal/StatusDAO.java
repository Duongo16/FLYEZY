/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import model.Status;

/**
 *
 * @author PMQUANG
 */
public class StatusDAO extends DBConnect {

    public List<Status> getAllStatus() {
        List<Status> list = new ArrayList<>();
        String sql = "select * from Status";
        try {
            PreparedStatement prepare = conn.prepareStatement(sql);
            ResultSet resultSet = prepare.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                list.add(new Status(id, name));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public String getStatusNameById(int id) {
        String sql = "SELECT name FROM Status WHERE id = ?";
        String name = null;
        try {
            PreparedStatement prepare = conn.prepareStatement(sql);
            prepare.setInt(1, id);  // Đặt giá trị id vào câu lệnh SQL
            ResultSet resultSet = prepare.executeQuery();
            if (resultSet.next()) {
                name = resultSet.getString("name");  // Lấy tên từ kết quả truy vấn
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return name;  // Trả về tên, nếu không có giá trị sẽ trả về null
    }

    public List<Status> getStatusOfFlight() {
        List<Status> list = new ArrayList<>();
        String sql = "select * from Status where id = 1 or id = 2 ";

        try {
            PreparedStatement prepare = conn.prepareStatement(sql);
            ResultSet resultSet = prepare.executeQuery();
            prepare = conn.prepareStatement(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                list.add(new Status(id, name));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public List<Status> getStatusOfOrder() {
        List<Status> list = new ArrayList<>();
        String sql = "select * from Status where id = 10 or id = 12 or id = 7";

        try {
            PreparedStatement prepare = conn.prepareStatement(sql);
            ResultSet resultSet = prepare.executeQuery();
            prepare = conn.prepareStatement(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                list.add(new Status(id, name));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public List<Status> getStatusOfFeedback() {
        List<Status> list = new ArrayList<>();
        String sql = "select * from Status where id = 4 or id = 5 or id = 3 ";

        try {
            PreparedStatement prepare = conn.prepareStatement(sql);
            ResultSet resultSet = prepare.executeQuery();
            prepare = conn.prepareStatement(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                list.add(new Status(id, name));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public List<Status> getStatusOfTicket() {
        List<Status> list = new ArrayList<>();
        String sql = "SELECT * \n"
                + "FROM Status\n"
                + "Where id=13 Or id=14 Or id =8 Or id =6 Or id=7 OR id =12 Or id=10";

        try {
            PreparedStatement prepare = conn.prepareStatement(sql);
            ResultSet resultSet = prepare.executeQuery();
            prepare = conn.prepareStatement(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                list.add(new Status(id, name));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public void changeStatusTicket(int id, int status) {
        String sql = "UPDATE Ticket\n"
                + "   SET Statusid=?"
                + " WHERE id=?";

        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, status);
            pre.setInt(2, id);

            pre.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void changeStatusRefund(int id, int status,int price) {
        String sql = "UPDATE Refund\n"
                + "   SET Statusid=?,refundPrice=?, refundDate = ?"
                + " WHERE id=?";

        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, status);
            pre.setInt(2, price);
            if (status == 8) {
                pre.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            } else {
                pre.setTimestamp(3, null);
            }
            pre.setInt(4, id);

            pre.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void changeStatusFeedback(int id, int status) {
        String sql = "UPDATE Feedbacks\n"
                + "   SET Statusid=?"
                + " WHERE id=?";

        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, status);
            pre.setInt(2, id);

            pre.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public List<Status> getStatusOfFlightDetaisl() {
        List<Status> ls = new ArrayList<>();
        try {
            String sql = "SELECT * FROM flyezy.Status WHERE id = 1 OR id = 2";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                ls.add(new Status(id, name));
            }
        } catch (SQLException e) {

        }
        return ls;
    }

    public static void main(String[] args) {
//        System.out.println(dao.getAllStatus());
        StatusDAO sd = new StatusDAO();
        List<Status> statuses = (List<Status>) sd.getStatusOfFlightDetaisl();
        for (Status status : statuses) {
            System.out.println(status.getId());
        }
    }

    public List<Status> getStatusOfRefund() {
        List<Status> list = new ArrayList<>();
        String sql = "select * from Status where id = 13 or id = 8 or id = 14 ";

        try {
            PreparedStatement prepare = conn.prepareStatement(sql);
            ResultSet resultSet = prepare.executeQuery();
            prepare = conn.prepareStatement(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                list.add(new Status(id, name));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
