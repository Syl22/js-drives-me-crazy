package esir.jxs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Path("/commandes")
public class Commandes {

    @GET
    @Path("/files")
    @Produces(MediaType.TEXT_PLAIN)
    public Response listeFichier() throws Exception {


        String access_token="";
        access_token = getString(access_token);


        Client client = ClientBuilder.newClient();

        String s = "{\"path\": \"\",\"recursive\": false}";
        Response entity2 = client.target("https://api.dropboxapi.com/2/files/list_folder")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "Bearer "+access_token)
                .header("Content-Type", "application/json")
                .post(Entity.json(s), Response.class);

        return entity2;

    }

    @GET
    @Path("/createfile")
    @Produces(MediaType.TEXT_PLAIN)
    public Response creerFichier(@QueryParam("path") String path) throws Exception {
        String access_token="";
        access_token = getString(access_token);

        Client client = ClientBuilder.newClient();

        String s = "{\"path\": \"/"+path+"\",\"autorename\": false}";

        Response reponse = client.target("https://api.dropboxapi.com/2/files/create_folder_v2")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "Bearer "+access_token)
                .header("Content-Type", "application/json")
                .post(Entity.json(s), Response.class);

        return reponse;

    }


    @GET
    @Path("/deletefile")
    @Produces(MediaType.TEXT_PLAIN)
    public Response supprimerFichier(@QueryParam("path") String path) throws Exception {
        String access_token="";
        access_token = getString(access_token);

        Client client = ClientBuilder.newClient();

        String s = "{\"path\": \"/"+path+"\"}";
        Response reponse = client.target("https://api.dropboxapi.com/2/files/delete_v2")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "Bearer "+access_token)
                .header("Content-Type", "application/json")
                .post(Entity.json(s), Response.class);

        return reponse;

    }

    private String getString(String access_token) {
        try {
            FileReader reader = new FileReader("token.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);


            String line;

            while ((line = bufferedReader.readLine()) != null) {
                access_token+=line;
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return access_token;
    }

}
