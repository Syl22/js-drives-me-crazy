package esir.jxs;



import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileWriter;
import java.io.IOException;


@Path("/redirect")
public class Redirect {

	@Path("/dropbox")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String redirect_dropbox(@QueryParam("code") String code) {

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

        String access_token = rep.getString("access_token");

        try {
            FileWriter writer = new FileWriter("token.txt", false);
            writer.write(access_token);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return access_token;



    }

    @Path("/onedrive")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String redirect_onedrive(@QueryParam("code") String code) {

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
    public String redirect_googledrive(@QueryParam("code") String code) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://www.googleapis.com/oauth2/v4/token");
        Form form = new Form();
        form.param("grant_type", "authorization_code");
        form.param("code", code);
        form.param("client_id", "173933358409-cusgbsfge58miicjqagem1ifcas0do8j.apps.googleusercontent.com");
        form.param("client_secret", "vlKKMm6YRWHukWjmgKKgMYoF");
        form.param("redirect_uri", "http://localhost:8080/projet/redirect/google");

        Response reponse = target.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED_TYPE),
                        Response.class);

        JSONObject rep = new JSONObject(reponse.readEntity(String.class));

        String access_token = rep.getString("access_token");


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
