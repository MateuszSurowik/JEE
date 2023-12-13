package sewerlet;

import Ops.DbManager;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "wypisywanie", urlPatterns = {"/wypisywanie"})
public class wypisywanie extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Lab2soloPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Trwałość.Data> query =
em.createNamedQuery("Data.findAll", Trwałość.Data.class);
        List<Trwałość.Data> results = query.getResultList();
        out.println(new Trwałość.Data().getListaHTML(results));
        response.setContentType("text/html;charset=UTF-8");
        String inst = request.getParameter("inst");
        PrintWriter out = response.getWriter();

        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<body>" + "Lista pracowników instytucji " + inst + "<br />");
            out.println("<html>");
out.println("<head><meta><link rel='stylesheet' href='Style/css/components.css'>");
out.println("<link rel='stylesheet' href='Style/css/icons.css'>");
out.println("<link rel='stylesheet' href='Style/css/responsee.css'>");
out.println(getDataFromDb());
out.println("</body></html>");
out.println("<a class=\'button rounded-full-btn reload-btn s-2 margin-bottom\' href=");
out.println(request.getHeader("referer"));
out.println("><i class='icon-sli-arrow-left'>Powrót</i></a>");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(wypisywanie.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private String getDataFromDb() throws ClassNotFoundException, SQLException {
        String data = "";
        boolean polaczenie = DbManager.Connect();

        if (polaczenie) {
            data = DbManager.getData();
            DbManager.Disconnect();
        }

        return data;
    }
    public boolean addDataToDb(String name, String surname) throws ClassNotFoundException, SQLException {
    boolean addedSuccessfully = false;
    
    boolean connection = DbManager.Connect();
    
    if (connection) {
        addedSuccessfully = DbManager.addData(name,surname);
        DbManager.Disconnect();
    }
    
    return addedSuccessfully;
}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    
@Override

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String method = request.getParameter("_method");

    if (method != null) {
        switch (method) {
            case "PUT":
                // Obsługa metody PUT (edycja danych)
                handleEditRequest(request, response);
                break;
            case "DELETE":
                // Obsługa metody DELETE (usuwanie danych)
                handleDeleteRequest(request, response);
                break;
            default:
                // Obsługa innych metod
                handleOtherMethods(request, response);
                break;
        }
    } else {
        // Obsługa zwykłego dodawania danych (metoda POST)
        handleAddRequest(request, response);
    }
}

private void handleAddRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Pobierz parametry z formularza dodawania
    String name = request.getParameter("name");
    String surname = request.getParameter("surname");

    // Logika dodawania danych do bazy
    try {
        boolean addedSuccessfully = DbManager.addData(name, surname);

        // Wysłanie odpowiedzi do klienta
        String message = addedSuccessfully ? "Dane zostały dodane pomyślnie!" : "Dodanie danych zakończyło się niepowodzeniem.";
        sendResponse(response, message);
    } catch (SQLException ex) {
        ex.printStackTrace();
        sendResponse(response, "Wystąpił błąd podczas dodawania danych.");
    }
}

private void handleEditRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Pobierz parametry z formularza edycji
    String name = request.getParameter("Ename");
    String surname = request.getParameter("Esurname");
    String id = request.getParameter("Eid");

    // Logika edycji danych w bazie
    try {
        int intId = Integer.parseInt(id);
        boolean editedSuccessfully = DbManager.editData(intId, name, surname);

        // Wysłanie odpowiedzi do klienta
        String message = editedSuccessfully ? "Dane zostały zmodyfikowane pomyślnie!" : "Modyfikacja danych zakończyła się niepowodzeniem.";
        sendResponse(response, message);
    } catch (NumberFormatException | SQLException ex) {
        ex.printStackTrace();
        sendResponse(response, "Wystąpił błąd podczas modyfikowania danych.");
    }
}

private void handleDeleteRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Pobierz parametr z formularza usuwania
    String id = request.getParameter("Did");

    // Logika usuwania danych z bazy
    try {
        int intId = Integer.parseInt(id);
        boolean deletedSuccessfully = DbManager.deleteData(intId);

        // Wysłanie odpowiedzi do klienta
        String message = deletedSuccessfully ? "Dane zostały usunięte pomyślnie!" : "Usuwanie danych zakończyło się niepowodzeniem.";
        sendResponse(response, message);
    } catch (NumberFormatException | SQLException ex) {
        ex.printStackTrace();
        sendResponse(response, "Wystąpił błąd podczas usuwania danych.");
    }
}

private void handleOtherMethods(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Obsługa innych metod lub nieprawidłowych żądań
    sendResponse(response, "Nieprawidłowe żądanie.");
}

private void sendResponse(HttpServletResponse response, String message) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.println("<html><body>");
    out.println("<h3>" + message + "</h3>");
    out.println("</body></html>");
}


    

 
    

}

