package com.slynko.web.rest;

import com.google.gson.Gson;
import com.slynko.web.endpoint.ChatEndpoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/users")
public class UsersRestService {

  @GET
  @Path("/{room}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findAll(@PathParam("room") String room) {
    Map<String, List<String>> loggedInUsers = new HashMap<>();
    loggedInUsers.put("users", ChatEndpoint.getLoggedInUsersByRoom(room));
    return new Gson().toJson(loggedInUsers);
  }

}
