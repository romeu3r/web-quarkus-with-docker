import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestauranteResource extends PanacheEntityBase {
    @GET
    public List<Restaurante> hello() {
        return Restaurante.listAll();
    }
}