/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountsDAO;
import dal.OrderDAO;
import dal.PlaneCategoryDAO;
import dal.SeatCategoryDAO;
import dal.TicketDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import model.Accounts;
import model.Order;
import model.Ticket;

/**
 *
 * @author Admin
 */
@WebServlet(name = "BookingFlightTicketsServlet", urlPatterns = {"/bookingFlightTickets"})
public class BookingFlightTicketsServlet extends HttpServlet {

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
            out.println("<title>Servlet BookingFlightTicketsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BookingFlightTicketsServlet at " + request.getContextPath() + "</h1>");
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
        AccountsDAO ad = new AccountsDAO();
        HttpSession session = request.getSession();

        Integer idd = (Integer) session.getAttribute("id");
        if (idd != null) {
            Accounts acc = ad.getAccountsById(idd);
            request.setAttribute("account", acc);
        }
        request.getRequestDispatcher("view/bookingFlightTickets.jsp").forward(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OrderDAO od = new OrderDAO();
        TicketDAO td = new TicketDAO();
        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute("id");
        try {
            String pContactName = request.getParameter("pContactName");
            String pContactPhoneNumber = request.getParameter("pContactPhoneNumber");
            String pContactEmail = request.getParameter("pContactEmail");
            int flightDetailId = Integer.parseInt(request.getParameter("flightDetailId"));
            int seatCategoryId = Integer.parseInt(request.getParameter("seatCategoryId"));
            int adultTicket = Integer.parseInt(request.getParameter("adultTicket"));
            int childTicket = Integer.parseInt(request.getParameter("childTicket"));
            int infantTicket = Integer.parseInt(request.getParameter("infantTicket"));
            float totalPriceDouble = Float.parseFloat(request.getParameter("totalPrice"));
            int totalPrice = (int) Math.round(totalPriceDouble);

            String orderCode = od.createOrder(flightDetailId, pContactName, pContactPhoneNumber, pContactEmail, totalPrice, id);
            Order o = od.getOrderByCode(orderCode);

            for (int i = 1; i <= adultTicket; i++) {
                int pSex = Integer.parseInt(request.getParameter("pSex"+i));
                String pName = request.getParameter("pName"+i);
                Date pDob = Date.valueOf(request.getParameter("pDob"+i));
                String pPhoneNumber = request.getParameter("pPhoneNumber"+i);
                int pBaggages = Integer.parseInt(request.getParameter("pBaggages"+i));  
                td.createTicket(seatCategoryId, 1, pName, pSex, pPhoneNumber, pDob, pBaggages, o.getId(), 1);
            }
            for (int i = adultTicket+1; i <= adultTicket+childTicket; i++) {
                int pSex = Integer.parseInt(request.getParameter("pSex"+i));
                String pName = request.getParameter("pName"+i);
                Date pDob = Date.valueOf(request.getParameter("pDob"+i));
                Ticket t = new Ticket(seatCategoryId, 2, "NULL", pName, pSex, pDob, o.getId(), 12, 1);
                td.createTicket(seatCategoryId, 2, pName, pSex, "000", pDob, 4, o.getId(), 1);
            }
            for (int i = adultTicket+childTicket+1; i <= adultTicket+childTicket+infantTicket; i++) {
                int pSex = Integer.parseInt(request.getParameter("pSex"+i));
                String pName = request.getParameter("pName"+i);
                Date pDob = Date.valueOf(request.getParameter("pDob"+i));
                td.createTicket(seatCategoryId, 3, pName, pSex, "000", pDob, 4, o.getId(), 1);
            }
            response.sendRedirect("home");
        } catch (Exception e) {
            e.printStackTrace(); // in lỗi ra console cho mục đích debug
            request.setAttribute("errorMessage", "Đã xảy ra lỗi: " + e.getMessage());
            request.getRequestDispatcher("view/error.jsp").forward(request, response); // chuyển hướng đến trang error.jsp
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
