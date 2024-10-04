/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountsDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import dal.TicketDAO;
import dal.FlightTypeDAO;
import dal.PassengerTypeDAO;
import dal.StatusDAO;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import model.Accounts;
import model.Ticket;
import model.FlightType;
import model.PassengerType;
import model.Status;

/**
 *
 * @author Fantasy
 */
@WebServlet(name = "TicketManagementServlet", urlPatterns = {"/TicketController"})
public class TicketManagementServlet extends HttpServlet {

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
            out.println("<title>Servlet TicketManagementServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TicketManagementServlet at " + request.getContextPath() + "</h1>");
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
        TicketDAO td = new TicketDAO();
        AccountsDAO ad = new AccountsDAO();
        FlightTypeDAO ftd = new FlightTypeDAO();
        PassengerTypeDAO ptd = new PassengerTypeDAO();
        StatusDAO sd = new StatusDAO();
        HttpSession session = request.getSession();

        Integer idd = (Integer) session.getAttribute("id");
        int i = (idd != null) ? idd : -1;
        Accounts acc = ad.getAccountsById(i);
        request.setAttribute("account", acc);

        List<Ticket> ticketList = td.getAllTickets();
        request.setAttribute("ticketList", ticketList);

        List<FlightType> flightTypeList = ftd.getAllFlightType();
        request.setAttribute("flightTypeList", flightTypeList);

        List<PassengerType> passengerTypeList = ptd.getAllPassengerType();
        request.setAttribute("passengerTypeList", passengerTypeList);

        List<Status> statusTicketList = sd.getStatusOfTicket();
        request.setAttribute("statusTicketList", statusTicketList);

        String action = request.getParameter("action");
        if (action == null) {
            request.getRequestDispatcher("view/ticketManagement.jsp").forward(request, response);
        } else if (action.equals("search")) {
            String flightType = request.getParameter("flightType");
            String passengerType = request.getParameter("passengerType");
            String statusTicket = request.getParameter("statusTicket");
            String fName = request.getParameter("fName").trim();
            String fPhoneNumber = request.getParameter("fPhoneNumber").trim();
//            List<Accounts> accountList = ad.searchAccounts(fRole, fName, fPhoneNumber);
//            request.setAttribute("accountList", accountList);
            List<Ticket> ticketSearchList = td.searchTickets(flightType, passengerType, statusTicket, fName, fPhoneNumber);
            request.setAttribute("ticketList", ticketSearchList);
            request.getRequestDispatcher("view/ticketManagement.jsp").forward(request, response);
        }
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
        TicketDAO td = new TicketDAO();
        AccountsDAO ad = new AccountsDAO();
        FlightTypeDAO ftd = new FlightTypeDAO();
        PassengerTypeDAO ptd = new PassengerTypeDAO();
        StatusDAO sd = new StatusDAO();
        HttpSession session = request.getSession();

        Integer idd = (Integer) session.getAttribute("id");
        int i = (idd != null) ? idd : -1;
        Accounts acc = ad.getAccountsById(i);
        request.setAttribute("account", acc);

        List<Ticket> ticketList = td.getAllTickets();
        request.setAttribute("ticketList", ticketList);

        List<FlightType> flightTypeList = ftd.getAllFlightType();
        request.setAttribute("flightTypeList", flightTypeList);

        List<PassengerType> passengerTypeList = ptd.getAllPassengerType();
        request.setAttribute("passengerTypeList", passengerTypeList);

        List<Status> statusTicketList = sd.getStatusOfTicket();
        request.setAttribute("statusTicketList", statusTicketList);

        String action = request.getParameter("action");
        if (action == null) {
            request.getRequestDispatcher("view/ticketManagement.jsp").forward(request, response);
        } else if (action.equals("changeStatus")) {
            int status = Integer.parseInt(request.getParameter("statusID"));
            int id = Integer.parseInt(request.getParameter("id"));
            sd.changeStatusTicket(id, status);
            if (status == 7) {
               int n = ticketList.size()+1;
               Ticket ticket = td.getTicketById(id);
               int a = td.createTicketWhenChangeStatus(n, ticket);
            }
            response.sendRedirect("TicketController");
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