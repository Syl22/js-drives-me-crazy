package esir.jxs;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@Path("/commandes")
public class Commandes {

    @GET
    @Path("/gfiles")
    @Produces(MediaType.TEXT_PLAIN)
    public Response listeGFichier(@DefaultValue("") @QueryParam("path") String path) throws Exception {


        String access_token="";
        access_token = getGString(access_token);


        Client client = ClientBuilder.newClient();


        Response entity2 = client.target("https://www.googleapis.com/drive/v3/files")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "Bearer "+access_token)
                .get(Response.class);

        return entity2;

    }

    @GET
    @Path("/ofiles")
    @Produces(MediaType.TEXT_PLAIN)
    public Response listeOFichier(@DefaultValue("") @QueryParam("path") String path) throws Exception {


        String access_token="";
        access_token = getOString(access_token);


        Client client = ClientBuilder.newClient();


        Response entity2 = client.target("https://graph.microsoft.com/v1.0/me/drive/root:/Images:/children")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "Bearer "+access_token)
                .get(Response.class);

        return entity2;

    }

    @GET
    @Path("/files")
    @Produces(MediaType.TEXT_PLAIN)
    public Response listeFichier(@DefaultValue("") @QueryParam("path") String path) throws Exception {


        String access_token="";
        access_token = getString(access_token);


        Client client = ClientBuilder.newClient();

        String s = "{\"path\": \"";
        if(!path.equals("")){
            s+=("/");
        }
        s+=path+"\",\"recursive\": false}";
        Response entity2 = client.target("https://api.dropboxapi.com/2/files/list_folder")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "Bearer "+access_token)
                .header("Content-Type", "application/json")
                .post(Entity.json(s), Response.class);

        return entity2;

    }

    @GET
    @Path("/createfolder")
    @Produces(MediaType.TEXT_PLAIN)
    public Response creerDossier(@QueryParam("path") String path) throws Exception {
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
    @Path("/delete")
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

    @GET
    @Path("/move")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deplacerFichier(@QueryParam("frompath") String frompath, @QueryParam("topath") String topath) throws Exception {
        String access_token="";
        access_token = getString(access_token);

        Client client = ClientBuilder.newClient();

        String s = "{\"from_path\": \"/"+frompath+"\", \"to_path\": \"/"+topath+"\"}";
        Response reponse = client.target("https://api.dropboxapi.com/2/files/move_v2")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "Bearer "+access_token)
                .header("Content-Type", "application/json")
                .post(Entity.json(s), Response.class);

        return reponse;

    }

    @GET
    @Path("/rename")
    @Produces(MediaType.TEXT_PLAIN)
    public Response renommerFichier(@QueryParam("path") String frompath, @QueryParam("newname") String name) throws Exception {
        String access_token="";
        access_token = getString(access_token);

        Client client = ClientBuilder.newClient();

        String[] tmp = frompath.split("\\/");
        tmp[tmp.length - 1] = name;
        String topath = tmp[0];

        if(tmp.length>1){
            for(int i = 1;i<=(tmp.length-1); i++){
                topath+="/";
                topath+=tmp[i];
            }
        }


        String s = "{\"from_path\": \"/"+frompath+"\", \"to_path\": \"/"+topath+"\"}";
        Response reponse = client.target("https://api.dropboxapi.com/2/files/move_v2")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "Bearer "+access_token)
                .header("Content-Type", "application/json")
                .post(Entity.json(s), Response.class);

        return reponse;

    }

    @GET
    @Path("/upload")
    @Produces(MediaType.TEXT_PLAIN)
    public Response upload(@QueryParam("path") String path) throws Exception {
        String access_token="";
        access_token = getString(access_token);

        //FileDataBodyPart filePart = new FileDataBodyPart("test", new File("C:/Users/Clément/Desktop/Images/cyka.jpg"));
        //FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
        //FormDataMultiPart multipart = (FormDataMultiPart) formDataMultiPart.field("foo", "bar").bodyPart(filePart);


        File file = new File("C:/Users/Clément/Desktop/Images/cyka.jpg");

        Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();;

        String s = "{\"path\": \"/"+path+"\", \"mode\": \"add\", \"autorename\": true, \"mute\": false}";
        Response reponse = client.target("https://content.dropboxapi.com/2/files/upload")
                .request()
                .header("Authorization", "Bearer "+access_token)
                .header("Dropbox-API-Arg", s)
                .header("Content-Type", "application/octet-stream")
                .post(Entity.entity(file, MediaType.APPLICATION_OCTET_STREAM));

        return reponse;
    }

    @GET
    @Path("/download")
    @Produces(MediaType.TEXT_PLAIN)
    public Response download(@QueryParam("path") String path) throws Exception {
        String access_token="";
        access_token = getString(access_token);


        Client client = ClientBuilder.newClient();

        //File file = new File("C:\\\\Users\\Clément\\Desktop\\Images\\blyat.jpg");

        String s = "{\"path\": \"/"+path+"\"}";
        Response reponse = client.target("https://content.dropboxapi.com/2/files/download")
                .request()
                .header("Authorization", "Bearer "+access_token)
                .header("Dropbox-API-Arg", s)
                .post(null, Response.class);

        return reponse;
        //return reponse.ok((Object) file).header("Content-Disposition", "attachement; filename = test.jpg").build();
    }

    @GET
    @Path("/space")
    @Produces(MediaType.TEXT_PLAIN)
    public Response space() throws Exception {
        String access_token="";
        access_token = getString(access_token);

        Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();

        Response reponse = client.target("https://api.dropboxapi.com/2/users/get_space_usage")
                .request()
                .header("Authorization", "Bearer "+access_token)
                .post(null, Response.class);

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

    private String getGString(String access_token) {
        try {
            FileReader reader = new FileReader("token_google.txt");
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

    private String getOString(String access_token) {
        try {
            FileReader reader = new FileReader("token_onedrive.txt");
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


    private JSONObject parse(JSONObject dropbox, JSONObject google_drive, JSONObject onedrive){
        JSONObject parseRes = new JSONObject();
        boolean d = dropbox!=null;
        boolean g = google_drive!=null;
        boolean o = onedrive!=null;

        ArrayList<String> entryList = new ArrayList<String>();
        JSONArray resEntries = new JSONArray();

        if(d) {
            JSONArray entries = dropbox.getJSONArray("entries");
            for (int i = 0; i < entries.length(); i++) {
                JSONObject entry = entries.getJSONObject(i);
                JSONObject resEntry = newEntry();
                resEntry = parseEntry(entry, resEntry);
                entryList.add(resEntry.getString("name"));
                resEntries.put(resEntry);
            }
        }

        if (g) {
            JSONArray entries = dropbox.getJSONArray("files");
            for (int i = 0; i < entries.length(); i++) {
                JSONObject entry = entries.getJSONObject(i);
                JSONObject resEntry = newEntry();
                if(entryList.contains(entry.getString("name"))){
                    int index = getInArray(resEntries, entry.getString("name"));
                    resEntry = resEntries.getJSONObject(index);
                    resEntries.remove(index);
                    resEntry = parseFiles(entry, resEntry);
                }
                else{
                    resEntry = parseFiles(entry, resEntry);
                    entryList.add(resEntry.getString("name"));
                }
                resEntries.put(resEntry);
            }
        }

        if (o){
            JSONArray entries = dropbox.getJSONArray("value");
            for (int i = 0; i < entries.length(); i++) {
                JSONObject entry = entries.getJSONObject(i);
                JSONObject resEntry = newEntry();
                if(entryList.contains(entry.getString("name"))){
                    int index = getInArray(resEntries, entry.getString("name"));
                    resEntry = resEntries.getJSONObject(index);
                    resEntries.remove(index);
                    resEntry = parseValue(resEntry, resEntry);
                }
                else{
                    resEntry = parseValue(resEntry, resEntry);
                    entryList.add(resEntry.getString("name"));
                }
                resEntries.put(resEntry);
            }
        }

        parseRes.put("entries", resEntries);

        return parseRes;
    }

    private JSONObject parseEntry(JSONObject entry, JSONObject resEntry){
        resEntry.put("name", entry.getString("name"));
        resEntry.put("type", entry.getString(".tag"));
        resEntry.put("path", entry.getString("path_display"));
        resEntry.put("dropbox_id", entry.getString("id"));
        if(entry.getString(".tag").equals("file")){
            resEntry.put("size", entry.getString("size"));
        }

        return resEntry;
    }

    private JSONObject parseFiles(JSONObject entry, JSONObject resEntry){
        resEntry.put("name", entry.getString("name"));
        resEntry.put("googledrive_id", entry.getString("id"));

        return resEntry;
    }

    private JSONObject parseValue(JSONObject entry, JSONObject resEntry){
        resEntry.put("name", entry.getString("name"));
        resEntry.put("onedrive_id", entry.getString("id"));
        resEntry.put("size", entry.getString("size"));


        return resEntry;
    }

    private JSONObject newEntry(){
        JSONObject res = new JSONObject();
        res.put("name", "");
        res.put("type", "");
        res.put("path", "");
        res.put("size", "0");
        res.put("dropbox_id", "");
        res.put("onedrive_id", "");
        res.put("googledrive_id", "");
        return res;
    }

    private int getInArray(JSONArray entries, String name){
        for(int i = 0; i < entries.length();i++){
            if(entries.getJSONObject(i).getString("name").equals(name)){
                return i;
            }
        }
        return 0;
    }



}
