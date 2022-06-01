import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Restaurante Resource")
public class RestauranteResource extends PanacheEntityBase {

    @GET
    public List<Restaurante> buscarRestaurante() {
        return Restaurante.listAll();
    }

    @POST
    @Transactional
    public Response adicionarRestaurante(Restaurante dto) {
        dto.persist();
        return Response.status(Response.Status.CREATED).build();
    }


    @PUT
    @Transactional
    @Path("{id}")
    public void editarRestaurante(@PathParam("id") Long id, Restaurante dto) {
        Optional<Restaurante> restauranteOptional = findByIdOptional(id);
        if (restauranteOptional.isEmpty()) {
            throw new NotFoundException();
        }
        Restaurante restaurante = restauranteOptional.get();
        restaurante.nome = dto.nome;
        restaurante.persist();
    }

    @DELETE
    @Transactional
    @Path("{id}")
    public void deletarRestaurante(@PathParam("id") Long id) {
        Optional<Restaurante> restauranteOptional = findByIdOptional(id);
        restauranteOptional.ifPresentOrElse(Restaurante::delete, () -> {
            throw new NotFoundException();
        });
    }

    @GET
    @Path("{idRestaurante}/pratos")
    @Tag(name = "Prato Resource")
    public List<Restaurante> buscarPratos(@PathParam("idRestaurante") Long idRestaurante) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
        if (restauranteOp.isEmpty()) {
            throw new NotFoundException("Restaurante não existe");
        }
        return Prato.list("restaurante", restauranteOp);
    }

    @POST
    @Path("{idRestaurante}/pratos")
    @Transactional
    @Tag(name = "Prato Resource")
    public Response adicionarPrato(@PathParam("idRestaurante") Long idRestaurante, Prato dto) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
        if (restauranteOp.isEmpty()) {
            throw new NotFoundException("Restaurante não existe");
        }
        Prato prato = new Prato();
        prato.nome = dto.nome;
        prato.descricao = dto.descricao;
        prato.preco = dto.preco;

        prato.restaurante = restauranteOp.get();
        prato.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{idRestaurante}/pratos/{id}")
    @Transactional
    @Tag(name = "Prato Resource")
    public void atualizarPrato(@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id, Prato dto) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
        if (restauranteOp.isEmpty()) {
            throw new NotFoundException("Restaurante não existe");
        }
        Optional<Prato> pratoOp = Prato.findByIdOptional(id);
        if (pratoOp.isEmpty()) {
            throw new NotFoundException("Prato não existe");
        }
        Prato prato = pratoOp.get();
        prato.preco = dto.preco;
        prato.persist();
    }

    @DELETE
    @Path("{idRestaurante}/pratos/{id}")
    @Transactional
    @Tag(name = "Prato Resource")
    public void deletarPrato(@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
        if (restauranteOp.isEmpty()) {
            throw new NotFoundException("Restaurante não existe");
        }
        Optional<Prato> pratoOp = Prato.findByIdOptional(id);
        pratoOp.ifPresentOrElse(Prato::delete, () -> {
            throw new NotFoundException();
        });
    }
}