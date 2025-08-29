// File: src/main/java/com/flipfit/resources/AuthorResource.java
package com.flipfit.resources;

import com.flipfit.bean.Author;
import com.flipfit.business.AuthorBusiness;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
public class AuthorResource {

    private final AuthorBusiness authorBusiness;

    public AuthorResource(AuthorBusiness authorBusiness) {
        this.authorBusiness = authorBusiness;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAuthor(Author author) {
        authorBusiness.createAuthor(author);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public List<Author> getAuthors(@QueryParam("page") @DefaultValue("0") int page, @QueryParam("size") @DefaultValue("10") int size, @QueryParam("search") String search) {
        if (search != null && !search.isEmpty()) {
            return authorBusiness.searchAuthors(search);
        }
        return authorBusiness.getAllAuthors(page, size);
    }

    @GET
    @Path("/{id}")
    public Response getAuthorById(@PathParam("id") int id) {
        Optional<Author> author = authorBusiness.getAuthorById(id);
        if (author.isPresent()) {
            return Response.ok(author.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAuthor(@PathParam("id") int id, Author author) {
        author.setId(id);
        if (authorBusiness.updateAuthor(author)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        if (authorBusiness.deleteAuthor(id)) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}