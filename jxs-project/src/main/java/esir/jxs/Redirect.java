package esir.jxs;



import com.sun.jersey.api.container.filter.LoggingFilter;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;


@Path("/redirect")
public class Redirect {

	@Path("/dropbox")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String redirect_dropbox(@QueryParam("code") String code) throws URISyntaxException, IOException {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://api.dropboxapi.com/oauth2/token");
        Form form = new Form();
        form.param("grant_type", "authorization_code");
        form.param("code", code);
        form.param("client_id", "yzkdkdpqg1t8vio");
        form.param("client_secret", "tmuffan0nndwszf");
        form.param("redirect_uri", "http://localhost:8080/projet/redirect/dropbox");

        Response reponse = target.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED_TYPE),
                        Response.class);


        JSONObject rep = new JSONObject(reponse.readEntity(String.class));

        System.out.println(rep);
        String access_token = rep.getString("access_token");

        try {
            FileWriter writer = new FileWriter("token.txt", false);
            writer.write(access_token);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return access_token;

    	/* Requete pour google drive
    	OAuthClientRequest request = OAuthClientRequest
                .authorizationLocation("https://www.googleapis.com/oauth2/v3/tokeninfo")
                .setParameter("access_token", code)
                .buildQueryMessage();
        */

    }

    @Path("/onedrive")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String redirect_onedrive(@QueryParam("code") String code) throws URISyntaxException, IOException {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://login.live.com/oauth20_token.srf");
        Form form = new Form();
        form.param("grant_type", "authorization_code");
        form.param("code", code);
        form.param("client_id", "04060989-d1ab-4e39-a106-610eedc4c893");
        form.param("client_secret", "ytazTWIRH3246#^afzIR2#{");
        form.param("redirect_uri", "http://localhost:8080/projet/redirect/onedrive");

        Response reponse = target.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED_TYPE),
                        Response.class);

        JSONObject rep = new JSONObject(reponse.readEntity(String.class));

        String access_token = rep.getString("access_token");

        try {
            FileWriter writer = new FileWriter("token_onedrive.txt", false);
            writer.write(access_token);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return access_token;
    }


    @Path("/google")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String redirect_googledrive(@QueryParam("access_token") String access_token) throws URISyntaxException, IOException {
        System.out.println(access_token);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://www.googleapis.com/oauth2/v3/tokeninfo");


        Response reponse = target.queryParam("access_token", access_token)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(Response.class);

        System.out.println(reponse.readEntity(String.class));
        JSONObject rep = new JSONObject(reponse.readEntity(String.class));


        try {
            FileWriter writer = new FileWriter("token_google.txt", false);
            writer.write(access_token);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return access_token;
    }

}
