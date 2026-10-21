package ma.ws.jaxrs.controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ma.ws.jaxrs.entities.Compte;
import ma.ws.jaxrs.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Path("/banque")
public class CompteRestJaxRSAPI {

    @Autowired
    private CompteRepository compteRepository;

    // READ : tous les comptes
    @Path("/comptes")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Compte> getComptes() {
        return compteRepository.findAll();
    }

    // READ : un compte par id
    @Path("/comptes/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Compte getCompte(@PathParam("id") Long id) {
        return compteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Compte " + id + " introuvable"));
    }

    // CREATE
    @Path("/comptes")
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Compte addCompte(Compte compte) {
        compte.setId(null);
        return compteRepository.save(compte);
    }

    // UPDATE
    @Path("/comptes/{id}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Compte updateCompte(@PathParam("id") Long id, Compte compte) {
        Compte existing = compteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Compte " + id + " introuvable"));
        existing.setSolde(compte.getSolde());
        existing.setDateCreation(compte.getDateCreation());
        existing.setType(compte.getType());
        return compteRepository.save(existing);
    }

    // DELETE
    @Path("/comptes/{id}")
    @DELETE
    public Response deleteCompte(@PathParam("id") Long id) {
        if (!compteRepository.existsById(id)) {
            throw new NotFoundException("Compte " + id + " introuvable");
        }
        compteRepository.deleteById(id);
        return Response.noContent().build();
    }
}