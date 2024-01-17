package lab4.war;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lab4.ebj.DataAccessBean;

@WebServlet(name = "TestClient", urlPatterns = {"/TestClient"})
public class TestClient extends HttpServlet {

    @EJB
    private DataAccessBean dataAccessBean;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TestClient</title>");
            out.println("</head>");
            out.println("<body>");

            // Existing code to display last access data
            out.println("<b>Ostatni dostęp: " + dataAccessBean.getOstatnieDane() + "</b><br/>");

            // New code to invoke the additional business method
            out.println("<b>Additional Data: " + dataAccessBean.getAdditionalData() + "</b><br/>");

            // Capture user agent and current date
            String userAgent = request.getHeader("user-agent");
            java.util.Date currentDate = new java.util.Date();

            // Call the business method to set the captured data
            dataAccessBean.setOstatnieDane(userAgent, currentDate);

            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "TestClient Servlet";
    }
}
