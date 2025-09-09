package io.permasoft.archihexa.pretdebd.infrastructure.rest;

import io.permasoft.archihexa.pretdebd.application.BdService;
import io.permasoft.archihexa.pretdebd.domain.BookNotFoundExeption;
import io.permasoft.archihexa.pretdebd.domain.Isbn;
import io.permasoft.archihexa.pretdebd.domain.Proprietaire;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.Getter;
import org.jboss.resteasy.reactive.RestQuery;

import java.util.UUID;

@Path( "/")
public class EmprunteBdController {
    BdService service;
    public EmprunteBdController(BdService service) {
        this.service = service;
    }

    @POST
    public void enregistrer(@RestQuery String isbn, @RestQuery String name) {
        service.enregistrer(UUID.randomUUID(),
                new Isbn(isbn),
                new Proprietaire(name));
    }

    @POST
    @Path("/emprunter")
    public void emprunter(@RestQuery String bdid, @RestQuery String name) throws BookNotFoundExeption {
        service.emprunte(UUID.fromString(bdid), name);
    }

}
