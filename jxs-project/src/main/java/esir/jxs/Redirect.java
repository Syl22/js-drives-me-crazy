package esir.jxs;



import org.json.*;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;



@Path("/redirect")
public class Redirect {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response redirect(@QueryParam("code") String code) throws URISyntaxException {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://api.dropboxapi.com/oauth2/token");
        Form form = new Form();
        form.param("grant_type", "authorization_code");
        form.param("code", code);
        form.param("client_id", "yzkdkdpqg1t8vio");
        form.param("client_secret", "tmuffan0nndwszf");
        form.param("redirect_uri", "http://localhost:8080/projet/redirect");

        String reponse = target.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED_TYPE),
                        String.class);

        JSONObject rep = new JSONObject(reponse);

        String access_token = rep.getString("access_token");

        java.nio.file.Path file = Paths.get("access_token .txt");
        Files.write(file, lines, Charset.forName("UTF-8"));

        URI url = new URI("http://localhost:8080/projet/commandes");
        return Response.status(Response.Status.TEMPORARY_REDIRECT).location(url).build();

    	/* Requete pour google drive
    	OAuthClientRequest request = OAuthClientRequest
                .authorizationLocation("https://www.googleapis.com/oauth2/v3/tokeninfo")
                .setParameter("access_token", code)
                .buildQueryMessage();
        */


        String entity = client.target("https://api.dropboxapi.com/2/users/get_current_account")
                .request(MediaType.TEXT_PLAIN)
                .header("Authorization", "Bearer "+access_token)
                .post(null, String.class);

        //JSONObject info = new JSONObject(entity);

        String s = "{\"path\": \"/TESTEUH\"}";
        String entity3 = client.target("https://api.dropboxapi.com/2/files/delete_v2")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "Bearer "+access_token)
                .header("Content-Type", "application/json")
                .post(Entity.json(s), String.class);

        s = "{\"path\": \"\",\"recursive\": false}";
        String entity2 = client.target("https://api.dropboxapi.com/2/files/list_folder")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "Bearer "+access_token)
                .header("Content-Type", "application/json")
                .post(Entity.json(s), String.class);
/*
        s = "{\"path\": \"/TESTEUH\",\"autorename\": false}";
        String entity3 = client.target("https://api.dropboxapi.com/2/files/create_folder_v2")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "Bearer "+access_token)
                .header("Content-Type", "application/json")
                .post(Entity.json(s), String.class);
*/


        return entity2;
    }

}
