/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountsDAO;
import dal.AirlineManageDAO;
import dal.AirportDAO;
import dal.CountryDAO;
import dal.FlightManageDAO;
import dal.LocationDAO;
import dal.StatusDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.util.List;
import model.Accounts;
import model.Flights;

/**
 *
 * @author user
 */
public class FlightManagementServlet extends HttpServlet {

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
            out.println("<title>Servlet FlightManagementServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FlightManagementServlet at " + request.getContextPath() + "</h1>");
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
        FlightManageDAO fmd = new FlightManageDAO();
        AirportDAO ad = new AirportDAO();
        StatusDAO sd = new StatusDAO();
        ResultSet rsFlightManage;
        AccountsDAO accd = new AccountsDAO();
        LocationDAO ld = new LocationDAO();
        CountryDAO cd = new CountryDAO();
        AirlineManageDAO amd = new AirlineManageDAO();
        HttpSession session = request.getSession();

        Integer idd = (Integer) session.getAttribute("id");
        int i = (idd != null) ? idd : -1;
        Accounts acc = accd.getAccountsById(i);
        request.setAttribute("account", acc);
        String action = request.getParameter("action");
        if (action == null) {

            String sql = "select f.id,f.minutes,a1.name as departureAirport,l1.name as departureLocation,c1.name as departureCountry,\n"
                    + "a2.name as destinationAirport,l2.name as destinationLocation, c2.name as destinationCountry,  s.name as status, f.departureAirportid, f.destinationAirportid, f.Status_id  from flyezy.Flight as f\n"
                    + "inner join flyezy.Airport as a1 on a1.id = f.departureAirportid\n"
                    + "inner join flyezy.Airport as a2 on a2.id = f.destinationAirportid\n"
                    + "inner join Location as l1 on l1.id = a1.locationid\n"
                    + "inner join Country as c1 on c1.id = l1.country_id\n"
                    + "inner join Location as l2 on l2.id = a2.locationid\n"
                    + "inner join Country as c2 on c2.id = l2.country_id\n"
                    + "inner join Status as s on s.id = f.Status_id\n"
                    + "inner join Accounts as acc on acc.Airlineid = f.Airline_id\n"
                    + "where acc.id = " + idd;
            rsFlightManage = fmd.getData(sql);
        } else {
            String departureCountry = request.getParameter("departureCountry");
            String destinationCountry = request.getParameter("destinationCountry");
            String departureLocation = request.getParameter("departureLocation");
            String departureAirport = request.getParameter("departureAirport");
            String destinationLocation = request.getParameter("destinationLocation");
            String destinationAirport = request.getParameter("destinationAirport");

            String sql = "SELECT f.id,\n"
                    + "       f.minutes,\n"
                    + "       a1.name AS departureAirport,\n"
                    + "       l1.name AS departureLocation,\n"
                    + "       c1.name AS departureCountry,\n"
                    + "       a2.name AS destinationAirport,\n"
                    + "       l2.name AS destinationLocation,\n"
                    + "       c2.name AS destinationCountry,\n"
                    + "       s.name AS status,\n"
                    + "       f.departureAirportid,\n"
                    + "       f.destinationAirportid,\n"
                    + "       f.Status_id\n"
                    + "FROM flyezy.Flight AS f\n"
                    + "INNER JOIN flyezy.Airport AS a1 ON a1.id = f.departureAirportid\n"
                    + "INNER JOIN flyezy.Airport AS a2 ON a2.id = f.destinationAirportid\n"
                    + "INNER JOIN Location AS l1 ON l1.id = a1.locationid\n"
                    + "INNER JOIN Country AS c1 ON c1.id = l1.country_id\n"
                    + "INNER JOIN Location AS l2 ON l2.id = a2.locationid\n"
                    + "INNER JOIN Country AS c2 ON c2.id = l2.country_id\n"
                    + "INNER JOIN Status AS s ON s.id = f.Status_id\n"
                    + "inner join Accounts as acc on acc.Airlineid = f.Airline_id\n"
                    + "WHERE 1=1 and acc.id = " + idd;
            if (departureAirport != null && !departureAirport.isEmpty()) {
                sql += " AND a1.name LIKE '%" + departureAirport + "%'";
            }
            if (destinationAirport != null && !destinationAirport.isEmpty()) {
                sql += " AND a2.name LIKE '%" + destinationAirport + "%'";
            }
            if (departureLocation != null && !departureLocation.isEmpty()) {
                sql += " AND l1.name LIKE '%" + departureLocation + "%'";
            }
            if (destinationLocation != null && !destinationLocation.isEmpty()) {
                sql += " AND l2.name LIKE '%" + destinationLocation + "%'";
            }
            if (departureCountry != null && !departureCountry.isEmpty()) {
                sql += " AND c1.name LIKE '%" + departureCountry + "%'";
            }
            if (destinationCountry != null && !destinationCountry.isEmpty()) {
                sql += " AND c2.name LIKE '%" + destinationCountry + "%'";
            }

            rsFlightManage = fmd.getData(sql);
        }
        request.setAttribute("rsFlightManage", rsFlightManage);
        request.setAttribute("listA", ad.getAllAirport());
        request.setAttribute("listL", ld.getAllLocation());
        request.setAttribute("listC", cd.getAllCountry());

        request.getRequestDispatcher("view/flightManagement.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FlightManageDAO fmd = new FlightManageDAO();
        AirportDAO ad = new AirportDAO();
        StatusDAO sd = new StatusDAO();
        ResultSet rsFlightManage;
        HttpSession session = request.getSession();
        AccountsDAO accd = new AccountsDAO();
        LocationDAO ld = new LocationDAO();
        CountryDAO cd = new CountryDAO();
        AirlineManageDAO amd = new AirlineManageDAO();
        String action = request.getParameter("action");

        Flights flight = fmd.getAllFlight();

        //minutes, departureAirport, destinationAirport, statusId
        if (action.equals("create")) {
            int minutes = Integer.parseInt(request.getParameter("minutes"));
            int departureAirportId = ad.getAirportIdByName(request.getParameter("departureAirport"));
            int destinationAirportId = ad.getAirportIdByName(request.getParameter("destinationAirport"));
            int airlineId = Integer.parseInt(request.getParameter("airlineId"));

            if (departureAirportId == destinationAirportId) {

                String errorCreate = "Cannot be duplicated, please enter again!";
                request.setAttribute("error", errorCreate);

                Integer idd = (Integer) session.getAttribute("id");
                int i = (idd != null) ? idd : -1;
                Accounts acc = accd.getAccountsById(i);
                request.setAttribute("account", acc);

                String sql = "select f.id,f.minutes,a1.name as departureAirport,l1.name as departureLocation,c1.name as departureCountry,\n"
                        + "a2.name as destinationAirport,l2.name as destinationLocation, c2.name as destinationCountry,  s.name as status, f.departureAirportid, f.destinationAirportid, f.Status_id  from flyezy.flight as f\n"
                        + "inner join flyezy.airport as a1 on a1.id = f.departureAirportid\n"
                        + "inner join flyezy.airport as a2 on a2.id = f.destinationAirportid\n"
                        + "inner join location as l1 on l1.id = a1.locationid\n"
                        + "inner join country as c1 on c1.id = l1.country_id\n"
                        + "inner join location as l2 on l2.id = a2.locationid\n"
                        + "inner join country as c2 on c2.id = l2.country_id\n"
                        + "inner join status as s on s.id = f.Status_id\n"
                        + "inner join accounts as acc on acc.Airlineid = f.Airline_id\n"
                        + "where acc.id = " + idd;
                rsFlightManage = fmd.getData(sql);

                request.setAttribute("rsFlightManage", rsFlightManage);
                request.setAttribute("listL", ld.getAllLocation());
                request.setAttribute("listC", cd.getAllCountry());
                request.setAttribute("listA", ad.getAllAirport());
                request.getRequestDispatcher("view/flightManagement.jsp").forward(request, response);
            } else {
                Flights newFlight = new Flights(minutes, departureAirportId, destinationAirportId, airlineId);
                boolean check = fmd.checkDuplicated(newFlight);
                if (check == true) {
                    int n = fmd.createFlight(newFlight);
                    response.sendRedirect("flightManagement");
                } else {
                    String errorCreate = "Departure Airport and Destination Airport already exists, please enter again !";
                    request.setAttribute("error", errorCreate);

                    Integer idd = (Integer) session.getAttribute("id");
                    int i = (idd != null) ? idd : -1;
                    Accounts acc = accd.getAccountsById(i);
                    request.setAttribute("account", acc);

                    String sql = "select f.id,f.minutes,a1.name as departureAirport,l1.name as departureLocation,c1.name as departureCountry,\n"
                            + "a2.name as destinationAirport,l2.name as destinationLocation, c2.name as destinationCountry,  s.name as status, f.departureAirportid, f.destinationAirportid, f.Status_id  from flyezy.flight as f\n"
                            + "inner join flyezy.airport as a1 on a1.id = f.departureAirportid\n"
                            + "inner join flyezy.airport as a2 on a2.id = f.destinationAirportid\n"
                            + "inner join location as l1 on l1.id = a1.locationid\n"
                            + "inner join country as c1 on c1.id = l1.country_id\n"
                            + "inner join location as l2 on l2.id = a2.locationid\n"
                            + "inner join country as c2 on c2.id = l2.country_id\n"
                            + "inner join status as s on s.id = f.Status_id\n"
                            + "inner join accounts as acc on acc.Airlineid = f.Airline_id\n"
                            + "where acc.id = " + idd;
                    rsFlightManage = fmd.getData(sql);

                    request.setAttribute("rsFlightManage", rsFlightManage);
                    request.setAttribute("listL", ld.getAllLocation());
                    request.setAttribute("listC", cd.getAllCountry());
                    request.setAttribute("listA", ad.getAllAirport());
                    request.getRequestDispatcher("view/flightManagement.jsp").forward(request, response);
                }
            }

        } else if (action.equals("update")) {
            int minutes = Integer.parseInt(request.getParameter("minutes"));
            int id = Integer.parseInt(request.getParameter("id"));
            int departureAirportId = ad.getAirportIdByName(request.getParameter("departureAirport"));
            int destinationAirportId = ad.getAirportIdByName(request.getParameter("destinationAirport"));
//            int departureAirportId = Integer.parseInt(request.getParameter("departureAirport"));
//             int destinationAirportId = Integer.parseInt(request.getParameter("destinationAirportId"));
            int airlineid = Integer.parseInt(request.getParameter("airlineId"));

            if (departureAirportId != destinationAirportId) {
                Flights newFlight = new Flights(id, minutes, departureAirportId, destinationAirportId, airlineid);
                boolean check = fmd.checkDuplicated(newFlight);
                if (check == true) {
                    fmd.updateFlight(newFlight);
                    response.sendRedirect("flightManagement");
                } else {
                    request.setAttribute("id", id);
                    String errorUpdate = "Departure Airport and Destination Airport already exists, please enter again !";
                    request.setAttribute("errorUpdate", errorUpdate);

                    Integer idd = (Integer) session.getAttribute("id");
                    int i = (idd != null) ? idd : -1;
                    Accounts acc = accd.getAccountsById(i);
                    request.setAttribute("account", acc);

                    String sql = "select f.id,f.minutes,a1.name as departureAirport,l1.name as departureLocation,c1.name as departureCountry,\n"
                            + "a2.name as destinationAirport,l2.name as destinationLocation, c2.name as destinationCountry,  s.name as status, f.departureAirportid, f.destinationAirportid, f.Status_id  from flyezy.flight as f\n"
                            + "inner join flyezy.airport as a1 on a1.id = f.departureAirportid\n"
                            + "inner join flyezy.airport as a2 on a2.id = f.destinationAirportid\n"
                            + "inner join location as l1 on l1.id = a1.locationid\n"
                            + "inner join country as c1 on c1.id = l1.country_id\n"
                            + "inner join location as l2 on l2.id = a2.locationid\n"
                            + "inner join country as c2 on c2.id = l2.country_id\n"
                            + "inner join status as s on s.id = f.Status_id\n"
                            + "inner join accounts as acc on acc.Airlineid = f.Airline_id\n"
                            + "where acc.id = " + idd;
                    rsFlightManage = fmd.getData(sql);

                    request.setAttribute("rsFlightManage", rsFlightManage);
                    request.setAttribute("listL", ld.getAllLocation());
                    request.setAttribute("listC", cd.getAllCountry());
                    request.setAttribute("listA", ad.getAllAirport());
                    request.getRequestDispatcher("view/flightManagement.jsp").forward(request, response);
                }

            } else {
                String errorUpdate = "Cannot be duplicated, please enter again!";
                request.setAttribute("errorUpdate", errorUpdate);

                request.setAttribute("idd", id);
                Integer idd = (Integer) session.getAttribute("id");
                int i = (idd != null) ? idd : -1;
                Accounts acc = accd.getAccountsById(i);
                request.setAttribute("account", acc);

                String sql = "select f.id,f.minutes,a1.name as departureAirport,l1.name as departureLocation,c1.name as departureCountry,\n"
                        + "a2.name as destinationAirport,l2.name as destinationLocation, c2.name as destinationCountry,  s.name as status, f.departureAirportid, f.destinationAirportid, f.Status_id  from flyezy.flight as f\n"
                        + "inner join flyezy.airport as a1 on a1.id = f.departureAirportid\n"
                        + "inner join flyezy.airport as a2 on a2.id = f.destinationAirportid\n"
                        + "inner join location as l1 on l1.id = a1.locationid\n"
                        + "inner join country as c1 on c1.id = l1.country_id\n"
                        + "inner join location as l2 on l2.id = a2.locationid\n"
                        + "inner join country as c2 on c2.id = l2.country_id\n"
                        + "inner join status as s on s.id = f.Status_id\n"
                        + "inner join accounts as acc on acc.Airlineid = f.Airline_id\n"
                        + "where acc.id = " + idd;
                rsFlightManage = fmd.getData(sql);

                request.setAttribute("rsFlightManage", rsFlightManage);
                request.setAttribute("listL", ld.getAllLocation());
                request.setAttribute("listC", cd.getAllCountry());
                request.setAttribute("listA", ad.getAllAirport());
                request.getRequestDispatcher("view/flightManagement.jsp").forward(request, response);
            }

        } else if (action.equals("changeStatus")) {
            int flightId = Integer.parseInt(request.getParameter("flightId"));
            int flightStatus = Integer.parseInt(request.getParameter("flightStatus"));
            int newStatus = 1;
            if (flightStatus == 1) {
                newStatus = 2;
            } else if (flightStatus == 2) {
                newStatus = 1;
            }
            fmd.changeStatus(flightId, newStatus);
            response.sendRedirect("flightManagement");
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
