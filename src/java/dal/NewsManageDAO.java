/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.News;

public class NewsManageDAO extends DBConnect {

    public List<News> getNews() {
        List<News> list = new ArrayList<News>();
        String sql = "select * from News";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                News a = new News(rs.getInt("id"), rs.getString("title"), rs.getString("image"), rs.getString("content"), rs.getInt("News_Categoryid"), rs.getInt("Accountsid"));
                list.add(a);
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
     public List<News> getNewsById(int id) {
        List<News> list = new ArrayList<News>();
        String sql = "select * from News where Accountsid = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                News a = new News(rs.getInt("id"), rs.getString("title"), rs.getString("content"), rs.getInt("News_Categoryid"), rs.getInt("Accountsid"));
                list.add(a);
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int createNews(News news) {
        int n = 0;
        String sql = "INSERT INTO `flyezy`.`news` (title, content, image, News_Categoryid, Accountsid) "
                + "VALUES (?, ?, ?, ?, ?);";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            // Set the values into PreparedStatement
            ps.setString(1, news.getTitle());
            ps.setString(2, news.getContent());
            ps.setString(3, news.getImage());
            ps.setInt(4, news.getNewCategory());
            ps.setInt(5, news.getAccountId());
            n = ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public void deleteNewsById(int id) {
        String sql = "DELETE FROM `flyezy`.`news`\n"
                + "WHERE id = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
   

    public void UpdateNews(News news) {
        String sql = "UPDATE `flyezy`.`news`\n"
                + "SET\n"
                + "title = ?,\n"
                +"image = ?,\n"
                + "content = ?,\n"
                + "News_Categoryid = ?,\n"
                + "Accountsid = ?\n"
                + "WHERE id = ?;";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, news.getTitle());
            pre.setString(3, news.getContent());
            pre.setString(2, news.getImage());
            pre.setInt(4, news.getNewCategory());
            pre.setInt(5, news.getAccountId());
            pre.setInt(6, news.getId());
            pre.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void deleteNews(int id) {
        String sql = "DELETE FROM `flyezy`.`news`\n"
                + "WHERE id = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    public List<News> searchNews(String ftitle, String newsCategoryid) {
        List<News> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM News WHERE 1=1");

        if (newsCategoryid != null && !newsCategoryid.isEmpty()) {
            sql.append(" AND News_Categoryid = ?  and accounts.Airlineid = ?");
        }
        if (ftitle != null && !ftitle.isEmpty()) {
            sql.append(" AND title LIKE ?");
        }
        try {
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            int i = 1;
            if (newsCategoryid != null && !newsCategoryid.isEmpty()) {
                ps.setString(i++, newsCategoryid);
            }
            if (ftitle != null && !ftitle.isEmpty()) {
                ps.setString(i++, "%" + ftitle + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                News a = new News(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("image"),
                        rs.getString("content"),
                        rs.getInt("News_Categoryid"),
                        rs.getInt("AccountsId"));
                list.add(a);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        NewsManageDAO d = new NewsManageDAO();
        
        
    }
}