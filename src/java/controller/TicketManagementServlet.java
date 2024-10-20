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
import dal.AirportDAO;
import dal.FlightDetailDAO;
import dal.FlightManageDAO;
import dal.PlaneCategoryDAO;
import dal.LocationDAO;
import dal.CountryDAO;
import dal.SeatCategoryDAO;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import model.Accounts;
import model.Ticket;
import model.FlightType;
import model.PlaneCategory;
import model.FlightDetails;
import model.Flights;
import model.PassengerType;
import model.Status;
import model.Airport;
import model.Location;
import model.Country;
import model.SeatCategory;

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
        FlightDetailDAO fdd = new FlightDetailDAO();
        FlightManageDAO fmd = new FlightManageDAO();
        AirportDAO aid = new AirportDAO();
        LocationDAO ld = new LocationDAO();
        StatusDAO sd = new StatusDAO();
        CountryDAO cd = new CountryDAO();
        SeatCategoryDAO scd = new SeatCategoryDAO();
        PlaneCategoryDAO pcd = new PlaneCategoryDAO();

        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        if (session.getAttribute("flightDetailID") == null && action == null) {
            int flightDetailID = Integer.parseInt(request.getParameter("flightDetailID"));
            session.setAttribute("flightDetailID", flightDetailID);
        }
        int flightDetailID = (int) session.getAttribute("flightDetailID");

        Flights flight = fdd.getFlightByFlightDetailId(flightDetailID);
        request.setAttribute("flight", flight);

        int airlineId = fdd.getAirlineIdByFlightDetailId(flightDetailID);
        request.setAttribute("airlineId", airlineId);

        Airport airportDep = aid.getAirportById(flight.getDepartureAirportId());
        request.setAttribute("airportDep", airportDep);
        Location locationDep = ld.getLocationById(airportDep.getId());
        request.setAttribute("locationDep", locationDep);
        Country countryDep = cd.getCountryById(locationDep.getCountryId());
        request.setAttribute("countryDep", countryDep);

        Airport airportDes = aid.getAirportById(flight.getDestinationAirportId());
        request.setAttribute("airportDes", airportDes);
        Location locationDes = ld.getLocationById(airportDes.getId());
        request.setAttribute("locationDes", locationDes);
        Country countryDes = cd.getCountryById(locationDes.getCountryId());
        request.setAttribute("countryDes", countryDes);

        FlightDetails flightDetail = fdd.getFlightDetailsByID(flightDetailID);
        request.setAttribute("flightDetail", flightDetail);
        PlaneCategory planeCatrgory = pcd.getPlaneCategoryById(flightDetail.getPlaneCategoryId());
        request.setAttribute("planeCatrgory", planeCatrgory);

        Integer idd = (Integer) session.getAttribute("id");
        int i = (idd != null) ? idd : -1;
        Accounts acc = ad.getAccountsById(i);
        request.setAttribute("account", acc);

        List<Ticket> ticketList = td.getAllTicketsById(flightDetailID);
        request.setAttribute("ticketList", ticketList);

        List<FlightType> flightTypeList = ftd.getAllFlightType();
        request.setAttribute("flightTypeList", flightTypeList);

        List<PassengerType> passengerTypeList = ptd.getAllPassengerType();
        request.setAttribute("passengerTypeList", passengerTypeList);

        List<Status> statusTicketList = sd.getStatusOfTicket();
        request.setAttribute("statusTicketList", statusTicketList);

        int planeCategoryID = fdd.getPlaneCategoryIdFromFlightDetail(flightDetailID);
        List<SeatCategory> seatList = scd.getNameAndNumberOfSeat(planeCategoryID);
        request.setAttribute("seatList", seatList);

        if (action == null) {
            request.getRequestDispatcher("view/ticketManagement.jsp").forward(request, response);
        } else if (action.equals("search")) {
            String flightType = request.getParameter("flightType");
            String passengerType = request.getParameter("passengerType");
            String statusTicket = request.getParameter("statusTicket");
            String fName = request.getParameter("fName").trim();
            String fPhoneNumber = request.getParameter("fPhoneNumber").trim();
            String orderIdStr = request.getParameter("orderId");
            int orderId = -1;

            if (orderIdStr != null && !orderIdStr.isEmpty()) {
                try {
                    orderId = Integer.parseInt(orderIdStr);
                } catch (NumberFormatException e) {
                    
                    System.err.println("Invalid order ID format: " + orderIdStr);
                    orderId = -1; 
                }
            }
//            List<Accounts> accountList = ad.searchAccounts(fRole, fName, fPhoneNumber);
//            request.setAttribute("accountList", accountList);
            List<Ticket> ticketSearchList = td.searchTickets(passengerType, statusTicket, fName, fPhoneNumber, flightDetailID, flightType, orderId);
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
        FlightDetailDAO fdd = new FlightDetailDAO();
        FlightManageDAO fmd = new FlightManageDAO();
        AirportDAO aid = new AirportDAO();
        LocationDAO ld = new LocationDAO();
        StatusDAO sd = new StatusDAO();
        CountryDAO cd = new CountryDAO();
        PlaneCategoryDAO pcd = new PlaneCategoryDAO();
        SeatCategoryDAO scd = new SeatCategoryDAO();
        HttpSession session = request.getSession();
        int flightDetailID = (int) session.getAttribute("flightDetailID");

        Flights flight = fdd.getFlightByFlightDetailId(flightDetailID);
        request.setAttribute("flight", flight);
        Airport airportDep = aid.getAirportById(flight.getDepartureAirportId());
        request.setAttribute("airportDep", airportDep);
        Location locationDep = ld.getLocationById(airportDep.getId());
        request.setAttribute("locationDep", locationDep);
        Country countryDep = cd.getCountryById(locationDep.getCountryId());
        request.setAttribute("countryDep", countryDep);

        Airport airportDes = aid.getAirportById(flight.getDestinationAirportId());
        request.setAttribute("airportDes", airportDes);
        Location locationDes = ld.getLocationById(airportDes.getId());
        request.setAttribute("locationDes", locationDes);
        Country countryDes = cd.getCountryById(locationDes.getCountryId());
        request.setAttribute("countryDes", countryDes);

        FlightDetails flightDetail = fdd.getFlightDetailsByID(flightDetailID);
        request.setAttribute("flightDetail", flightDetail);
        PlaneCategory planeCatrgory = pcd.getPlaneCategoryById(flightDetail.getPlaneCategoryId());
        request.setAttribute("planeCatrgory", planeCatrgory);

        Integer idd = (Integer) session.getAttribute("id");
        int i = (idd != null) ? idd : -1;
        Accounts acc = ad.getAccountsById(i);
        request.setAttribute("account", acc);

        List<Ticket> ticketList = td.getAllTicketsById(flightDetailID);
        request.setAttribute("ticketList", ticketList);

        List<Ticket> allTicketList = td.getAllTickets();
        request.setAttribute("ticketList", ticketList);

        List<FlightType> flightTypeList = ftd.getAllFlightType();
        request.setAttribute("flightTypeList", flightTypeList);

        List<PassengerType> passengerTypeList = ptd.getAllPassengerType();
        request.setAttribute("passengerTypeList", passengerTypeList);

        List<Status> statusTicketList = sd.getStatusOfTicket();
        request.setAttribute("statusTicketList", statusTicketList);

        int planeCategoryID = fdd.getPlaneCategoryIdFromFlightDetail(flightDetailID);
        List<SeatCategory> seatList = scd.getNameAndNumberOfSeat(planeCategoryID);
        request.setAttribute("seatList", seatList);

        String action = request.getParameter("action");
        if (action == null) {
            request.getRequestDispatcher("view/ticketManagement.jsp").forward(request, response);
        } else if (action.equals("changeStatus")) {
            int status = Integer.parseInt(request.getParameter("statusID"));
            int id = Integer.parseInt(request.getParameter("id"));
            sd.changeStatusTicket(id, status);
            if (status == 7) {
                int n = allTicketList.size() + 1;
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
