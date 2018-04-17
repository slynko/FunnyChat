package com.slynko.web.rest;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.slynko.web.endpoint.ChatEndpoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/users")
public class UsersRestService {

  @GET
  @Path("/{room}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findAll(@PathParam("room") String room) {
    return new Gson().toJson(ImmutableMap.of("users", ChatEndpoint.getLoggedInUsersByRoom(room)));
  }
}
