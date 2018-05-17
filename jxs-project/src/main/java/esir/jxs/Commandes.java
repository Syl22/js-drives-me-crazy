package esir.jxs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/redirect")
public class Commandes {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response listeFichier() throws Exception {


        String s = "{\"path\": \"\",\"recursive\": false}";
        String entity2 = client.target("https://api.dropboxapi.com/2/files/list_folder")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "Bearer "+access_token)
                .header("Content-Type", "application/json")
                .post(Entity.json(s), String.class);
    }

}
