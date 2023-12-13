package sewerlet;

import Ops.DbManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "logi", urlPatterns = {"/logi"})
public class logi extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta>");
            out.println("<link rel='stylesheet' href='Style/css/components.css'>");
            out.println("<link rel='stylesheet' href='Style/css/icons.css'>");
            out.println("<link rel='stylesheet' href='Style/css/responsee.css'>");
            out.println("</head>");
            out.println("<body>");

            // Pobieranie danych z bazy danych logów
            String logData = getDataFromLog();

            // Wyświetlanie danych z bazy danych logów
            out.println(logData);

            // Dodanie przycisku powrotu
            out.println("<a class='button rounded-full-btn reload-btn s-2 margin-bottom' href='"
                    + request.getHeader("referer") + "'><i class='icon-sli-arrow-left'>Powrót</i></a>");

            out.println("</body>");
            out.println("</html>");
        } catch (SQLException ex) {
            Logger.getLogger(logi.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private String getDataFromLog() throws ClassNotFoundException, SQLException {
        String data = "";
        boolean connection = DbManager.Connect();

        if (connection) {
            data = DbManager.getLogData();
            DbManager.Disconnect();
        }

        return data;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(logi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(logi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
