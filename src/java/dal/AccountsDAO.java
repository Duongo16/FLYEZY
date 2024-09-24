/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.Accounts;

/**
 *
 * @author Admin
 */
public class AccountsDAO extends DBConnect {

    private final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public List<Accounts> getAllAccounts() {
        List<Accounts> ls = new ArrayList<>();
        String sql = "Select * from Accounts";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Accounts a = new Accounts(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
                        rs.getString("password"), rs.getString("phoneNumber"),
                        rs.getString("address"), rs.getString("image"), rs.getDate("dob"), rs.getInt("Rolesid"), rs.getInt("Airlineid"),
                        rs.getTimestamp("created_at"), rs.getTimestamp("updated_at"));
                ls.add(a);
            }
            return ls;

        } catch (Exception e) {
        }
        return null;
    }

    public int getIdByEmailOrPhoneNumber(String emailOrPhoneNumber) {
        String sql = "SELECT id FROM Accounts WHERE email = ? OR phoneNumber = ?";
        int userId = -1;

        try (PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, emailOrPhoneNumber);
            st.setString(2, emailOrPhoneNumber);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userId;
    }

    public Accounts getAccountsById(int id) {
        String sql = "SELECT * FROM Accounts WHERE id = ?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Accounts a = new Accounts(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
                        rs.getString("password"), rs.getString("phoneNumber"),
                        rs.getString("address"), rs.getString("image"), rs.getDate("dob"), rs.getInt("Rolesid"), rs.getInt("Airlineid"),
                        rs.getTimestamp("created_at"), rs.getTimestamp("updated_at"));
                return a;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void updateAccount(Accounts account) {
        String sql = "UPDATE Accounts\n"
                + "   SET name = ?\n"
                + "      ,email = ?\n"
                + "      ,password = ?\n"
                + "      ,phoneNumber = ?\n"
                + "      ,address = ?\n"
                + "      ,Rolesid = ?\n"
                + "      ,Airlineid = ?\n"
                + "      ,image = ?\n"
                + "      ,dob = ?\n"
                + "      ,updated_at = ?\n"
                + " WHERE id=?";

        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, account.getName());
            pre.setString(2, account.getEmail());
            String encode = encryptAES(account.getPassword(), SECRET_KEY);
            pre.setString(3, encode);
            pre.setString(4, account.getPhoneNumber());
            pre.setString(5, account.getAddress());
            pre.setInt(6, account.getRoleId());
            pre.setInt(7, account.getAirlineId());
            pre.setString(8, account.getImage());
            pre.setDate(9, account.getDob());
            pre.setTimestamp(10, account.getUpdated_at());
            pre.setInt(11, account.getId());

            pre.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void removeAccount(int id) {
        String sql = "delete from Accounts where id = " + id;

        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Accounts> searchAccounts(String role, String name, String phoneNumber) {
        List<Accounts> ls = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Accounts WHERE 1=1");

        if (role != null && !role.isEmpty()) {
            sql.append(" AND Rolesid = ?");
        }
        if (name != null && !name.isEmpty()) {
            sql.append(" AND name LIKE ?");
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            sql.append(" AND phoneNumber LIKE ?");
        }

        try {
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            int i = 1;
            if (role != null && !role.isEmpty()) {
                ps.setString(i++, role);
            }
            if (name != null && !name.isEmpty()) {
                ps.setString(i++, "%" + name + "%");
            }
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                String vp = phoneNumber;
                if (vp.startsWith("0")) {
                    vp = vp.substring(1);
                }
                ps.setString(i++, "%" + vp + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Accounts a = new Accounts(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
                        rs.getString("password"), rs.getString("phoneNumber"),
                        rs.getString("address"), rs.getString("image"), rs.getDate("dob"), rs.getInt("Rolesid"), rs.getInt("Airlineid"),
                        rs.getTimestamp("created_at"), rs.getTimestamp("updated_at"));
                ls.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    public boolean checkAccount(Accounts accounts) {
        String sql = "Select * from Accounts where email='" + accounts.getEmail() + "'";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return false;
            }
            sql = "Select * from Accounts where phoneNumber='" + accounts.getPhoneNumber() + "'";
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            if (rs.next()) {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }

    public int createAccount(Accounts accounts) {
        int n = 0;
        String sql = """
                 INSERT INTO Accounts 
                     (name, email, password, phoneNumber, address, image, dob, Rolesid,created_at,Airlineid) 
                 VALUES 
                 (?,?,?,?,?,?,?,?,?,?)""";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            // Set các giá trị vào PreparedStatement
            ps.setString(1, accounts.getName());
            ps.setString(2, accounts.getEmail());

            String encode = encryptAES(accounts.getPassword(), SECRET_KEY);
            ps.setString(3, encode);

            ps.setString(4, accounts.getPhoneNumber());
            ps.setString(5, accounts.getAddress());
            ps.setString(6, accounts.getImage());
            ps.setDate(7, accounts.getDob());
            ps.setInt(8, accounts.getRoleId());
            ps.setTimestamp(9, accounts.getCreated_at());
            ps.setInt(10, accounts.getAirlineId());
            n = ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public void changePassword(String idAccount, String newPassword) {
        String sqlupdate = "UPDATE `flyezy`.`Accounts`\n"
                + "SET\n"
                + "`password` = ?\n"
                + "WHERE `id` = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sqlupdate);
            String encode = encryptAES(newPassword, SECRET_KEY);
            pre.setString(1, encode);
            pre.setString(2, idAccount);

            pre.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void infoUpdate(Accounts account) {
        String sqlUpdate = "UPDATE `flyezy`.`Accounts`\n"
                + "SET\n"
                + "`name` = ?,\n"
                + "`dob` = ?,\n"
                + "`email` = ?,\n"
                + "`phoneNumber` = ?,\n"
                + "`address` = ?,\n"
                + "`image` = ?\n"
                + "WHERE `id` = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sqlUpdate);
            pre.setString(1, account.getName());
            pre.setDate(2, account.getDob());
            pre.setString(3, account.getEmail());
            pre.setString(4, account.getPhoneNumber());
            pre.setString(5, account.getAddress());
            pre.setString(6, account.getImage());
            pre.setInt(7, account.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteAllAccountByAirline(int airlineId) {
        String sql = "DELETE FROM `flyezy`.`Accounts`\n"
                + "WHERE airlineid = " + airlineId;
        try {

            PreparedStatement pre = conn.prepareStatement(sql);
            pre.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
       
    public int findIdByEmail(String email){
        String sql = "select id from accounts where email = ?";
        int userId = -1;
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, email);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("id");
            }
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        return userId;
    }
    
    public boolean checkEmailExist(String email) {
        String sql = "select * from accounts where email = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public String generateRandomString() {
        StringBuilder sb = new StringBuilder(8);
        Random r = new Random();
        for (int i = 0; i < 8; i++) {
            int index = r.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        AccountsDAO dao = new AccountsDAO();
        dao.updateAccount(new Accounts(23,"Ngo Tung Duong222", "abccccc@gmail.com", "1", "0123456789", null, null, new Date(2000, 12, 11), 1, 1, null, null));
    }
}
