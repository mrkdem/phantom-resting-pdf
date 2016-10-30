package com.intive.rest;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.intive.User;

import java.io.FileNotFoundException;
import java.io.InputStream;

@Path("/get")
public class RestService {

	@GET
	@Path("/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, List<User>> getJSON() {
		Map<String, List<User>> modelMap = new HashMap<String, List<User>>();
	
		List<User> employees = new ArrayList<User>();
		employees.add(new User("Jan", "Kowalski"));
		employees.add(new User("Bob", "Smith"));
		employees.add(new User("Robert", "Nowak"));

		modelMap.put("employees", employees);
		return modelMap;
	}

	@GET
	@Path("/html2")
	@Produces(MediaType.TEXT_HTML)
	public String getHtml2() {
		return "<html lang=\"en\"><body><h1>Hello, World!!!</body></h1></html>";
	}

	@GET
	@Path("/html")
	@Produces(MediaType.TEXT_HTML)
	public Response getHtml() {
		String result = "<html lang=\"en\"><body><h1>Hello, World!!</body></h1></html>";
		return Response.status(201).entity(result).build();
	}

	@GET
	@Path("/htmlfromfile")
	@Produces(MediaType.TEXT_HTML)
	public InputStream getHtmlFromFile() throws FileNotFoundException {
		return getClass().getResourceAsStream("/templates/115.html");
	}

	@GET
	@Path("/pdffromfile")
	@Produces("application/pdf")
	public InputStream getPdfFromFile() throws FileNotFoundException {
		return getClass().getResourceAsStream("/templates/_pdf_115.pdf");
	}

/*
	public Response getFile() {

		File file = new File(FILE_PATH);

		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
				"attachment; filename=new-android-book.pdf");
		return response.build();
*/
}