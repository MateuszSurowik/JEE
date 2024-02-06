package restful;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import jpa.Emotes;

@Named
@Path("/Emotes")
public class FileResource {

    @PersistenceContext(unitName = "Emote")
    private EntityManager entityManager;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional // Dodaj adnotację @Transactional, aby obsługiwać transakcje
    public Response uploadPPTFile(Emotes emotes) {
        try {
            // Przedstawiony kod może być również opakowany w blok try-with-resources, aby zarządzać automatycznie zamykaniem EntityManager
            entityManager.persist(emotes);
            // Zwróć odpowiedź z utworzonym obiektem
            return Response.status(Response.Status.CREATED).entity(emotes).build();
        } catch (Exception e) {
            e.printStackTrace();
            // W przypadku błędu, zwróć odpowiednią odpowiedź błędu
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error uploading file").build();
        }
    }
}
