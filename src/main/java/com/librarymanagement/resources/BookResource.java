// File: src/main/java/com/flipfit/resources/BookResource.java
package com.librarymanagement.resources;

import com.librarymanagement.models.Book;
import com.librarymanagement.business.BookBusiness;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {

    private final BookBusiness bookBusiness;

    public BookResource(BookBusiness bookBusiness) {
        this.bookBusiness = bookBusiness;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) {
        bookBusiness.createBook(book);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public List<Book> getBooks(@QueryParam("page") @DefaultValue("0") int page, @QueryParam("size") @DefaultValue("10") int size, @QueryParam("search") String search) {
        if (search != null && !search.isEmpty()) {
            return bookBusiness.searchBooks(search);
        }
        return bookBusiness.getAllBooks(page, size);
    }

    @GET
    @Path("/{id}")
    public Response getBookById(@PathParam("id") int id) {
        Optional<Book> book = bookBusiness.getBookById(id);
        if (book.isPresent()) {
            return Response.ok(book.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("id") int id, Book book) {
        book.setId(id);
        if (bookBusiness.updateBook(book)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        if (bookBusiness.deleteBook(id)) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}