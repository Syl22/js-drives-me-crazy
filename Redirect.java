package com.example;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/redirect")
public class Redirect {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String redirect(@QueryParam("code") String code) throws Exception {

        OAuthClientRequest request = OAuthClientRequest
                .tokenLocation("https://api.dropboxapi.com/oauth2/token")
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setClientId("yzkdkdpqg1t8vio")
                .setClientSecret("tmuffan0nndwszf")
                .setCode(code)
                .setRedirectURI("http://localhost:8080/myapp/redirect")
                .buildQueryMessage();

        Response response = ClientBuilder.newClient().target(request.getLocationUri()).request().post(Entity.entity(null, MediaType.APPLICATION_FORM_URLENCODED));
        String access_token = response.getEntity()

        return "Redirection effectuée. Token obtenu : \n" + access_token;
    }

}