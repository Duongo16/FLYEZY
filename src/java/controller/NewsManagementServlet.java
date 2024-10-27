/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountsDAO;
import dal.NewsCategoryDAO;
import dal.NewsManageDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.List;
import model.Accounts;
import model.NewCategory;
import model.News;

/**
 *
 * @author user
 */

public class NewsManagementServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewsManagementServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewsManagementServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        NewsManageDAO nd = new NewsManageDAO();
        AccountsDAO ad = new AccountsDAO();
        NewsCategoryDAO nc = new NewsCategoryDAO();

        Integer idd = (Integer) session.getAttribute("id");
        if (idd == null) {
            response.sendRedirect("login");
            return;
        } else {
            int i = (idd != null) ? idd : -1;
            Accounts acc = ad.getAccountsById(i);
            request.setAttribute("account", acc);

            List<NewCategory> newC = nc.getNewCategory();
            request.setAttribute("newC", newC);
  
            String action = request.getParameter("action");
            if (action == null) {
                List<News> lnew = nd.getNews();
                request.setAttribute("lnew", lnew);
                request.getRequestDispatcher("view/newsManagement.jsp").forward(request, response);
            } else if (action.equals("search")) {
                String title = request.getParameter("title").trim();
                String newsCategory = request.getParameter("newsCategory");
                List<News> lnew = nd.searchNews(title, newsCategory);
                request.setAttribute("lnew", lnew);
                request.getRequestDispatcher("view/newsManagement.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NewsManageDAO nd = new NewsManageDAO();
        NewsCategoryDAO nc = new NewsCategoryDAO();

        String action = request.getParameter("action");
        if (action.equals("create")) {
            String Title = request.getParameter("Title");
           String image = "img/" + request.getParameter("image");
            String Content = request.getParameter("Content");
            int newCategory = Integer.parseInt(request.getParameter("category"));
            int accountId = Integer.parseInt(request.getParameter("accountId"));
            News a = new News(Title, image, Content, newCategory, accountId);
            int n = nd.createNews(a);
            response.sendRedirect("newsManagement");

        } else if (action.equals("update")) {
            String Title = request.getParameter("title");
            int id = Integer.parseInt(request.getParameter("id"));
            String image = "img/" + request.getParameter("image"); 
            String Content = request.getParameter("content");
            int newCategory = Integer.parseInt(request.getParameter("category"));
            int accountId = Integer.parseInt(request.getParameter("accountId"));
            News a = new News(id, Title,image, Content, newCategory, accountId);
            nd.UpdateNews(a);
            response.sendRedirect("newsManagement");
        } else if (action.equals("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            nd.deleteNews(id);
            response.sendRedirect("newsManagement");
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}