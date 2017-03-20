

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author gaunetc
 */
@WebServlet(urlPatterns = {"/add_player"})
public class AddPlayer extends HttpServlet {
    @Resource(name="jdbc/bibliotheque")
    private DataSource ds;
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
            out.println("<title>Servlet addCustomer</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addCustomer at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Inscriptions</title>"); 
            out.println("<meta charset=\"UTF-8\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<div> Bienvenue " + request.getParameter("Nom") + " " + request.getParameter("Prenom"));
            out.print(". Vous habitez");
            if(request.getParameter("residence").equals("Autre Ville")){
                out.print(" loin");
            }else{
                out.print(" " + request.getParameter("residence"));
            }
            out.print(" et vous vous êtes ");
            if(request.getParameter("Sexe").equals("M")){
                out.print("inscrit ");
            }else{
                out.print("inscrite ");
            }
            if(request.getParameterValues("choix").length==0){
                out.print("nulle part<br/><br/>");
            }else if(request.getParameterValues("choix").length==2){
                out.print("à la bibliothèque et à la discothèque.<br/><br/>");
            }else{
                out.print("à la " + request.getParameterValues("choix")[0]+ ".<br/><br/>");
            }
            try(Connection conn = ds.getConnection()){
                Statement st = conn.createStatement();
                st.executeQuery("INSERT INTO Users VALUES ('"+ request.getParameter("login")+ "' , '" +request.getParameter("password") +"', '"+ request.getParameter("residence") +"')");
                conn.close();
            }catch(SQLException e){
                out.println(e.getMessage());
            }
            out.println("retour vers <a href=\"index.html\">l'accueil</a>");
//            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
            
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
