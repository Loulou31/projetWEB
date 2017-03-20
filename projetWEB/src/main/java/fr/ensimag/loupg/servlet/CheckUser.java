/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.* ;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author nicolasl
 */
@WebServlet(urlPatterns = {"/checkuser"})

public class CheckUser extends HttpServlet {
    
    @Resource(name="jdbc/bibliotheque")
    private DataSource ds ;
    
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
            HttpSession session = request.getSession(true);
            try {
                if (isLoginValid(request.getParameter("login"), request.getParameter("password"))){
                    session.setAttribute("utilisateur", request.getParameter("login"));
                    String name = (String) session.getAttribute("utilisateur" );
                    /* TODO output your page here. You may use following sample code. */
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Servlet CheckUser</title>");            
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>Servlet CheckUser at " + request.getContextPath() + "</h1>");
                    out.println("<p>Retour vers <a href=\"index.html\">l’accueil</a></p>");
                    out.println("</body>");
                    out.println("</html>");
                } else {
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Servlet CheckUser</title>");            
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<p>Nom d'utilisateur ou mot de passe invalide </p>");
                    session.invalidate() ;
                    out.println("</body>");
                    out.println("</html>");
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            
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
        /* creation d'un cookie */
        /*
        Cookie cookie = new Cookie("utilisateur",request.getParameter("login"));
        response.addCookie(cookie);*/
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
        processRequest(request, response);
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
    
    public boolean isLoginValid(String login, String pwd) throws SQLException{
        try(Connection c = ds.getConnection()){
            System.out.println(login + " " + pwd) ; 
            PreparedStatement s = c.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ? ");
            s.setString(1, login);
            s.setString(2, pwd);
            ResultSet r = s.executeQuery();
            return r.next() ;
        } catch (SQLException e){
            System.out.println(e.toString());
            return true ;
        }
    }
}
