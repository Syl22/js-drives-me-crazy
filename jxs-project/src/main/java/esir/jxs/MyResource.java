package esir.jxs;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;


@Path("/auth")
public class MyResource {

	@Path("/dropbox")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response auth_dropbox() throws Exception {
        OAuthClientRequest request = OAuthClientRequest
                .authorizationLocation("https://www.dropbox.com/oauth2/authorize")
                .setClientId("yzkdkdpqg1t8vio")
                .setResponseType("code")
                .setRedirectURI("http://localhost:8080/projet/redirect/dropbox")
                .buildQueryMessage();


        URI url = new URI(request.getLocationUri());
        return Response.status(Response.Status.TEMPORARY_REDIRECT).location(url).build();
    }

    @Path("/onedrive")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response auth_onedrive() throws Exception {
        OAuthClientRequest request = OAuthClientRequest
                .authorizationLocation("https://login.live.com/oauth20_authorize.srf")
                .setClientId("04060989-d1ab-4e39-a106-610eedc4c893")
                .setScope("files.readwrite")
                .setResponseType("code")
                .setRedirectURI("http://localhost:8080/projet/redirect/onedrive")
                .buildQueryMessage();

        URI url = new URI(request.getLocationUri());
        return Response.status(Response.Status.TEMPORARY_REDIRECT).location(url).build();
    }


    @Path("/google")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response auth_googledrive() throws Exception {
        OAuthClientRequest request = OAuthClientRequest
                .authorizationLocation("https://accounts.google.com/o/oauth2/v2/auth")
                .setClientId("173933358409-cusgbsfge58miicjqagem1ifcas0do8j.apps.googleusercontent.com")
                .setScope("https://www.googleapis.com/auth/drive")
                .setResponseType("code")
                .setRedirectURI("http://localhost:8080/projet/redirect/google")
                .buildQueryMessage();

        URI url = new URI(request.getLocationUri());
        return Response.status(Response.Status.TEMPORARY_REDIRECT).location(url).build();
    }
    
    @Path("/deco")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public void deconnexion() throws Exception {
        
    	String tokenDropbox=getString("token.txt");
    	String tokenGoogle=getString("token_google.txt");
    	String tokenOneDrive=getString("token_onedrive.txt");

    	if (tokenDropbox != "") {
	    	Client client = ClientBuilder.newClient();
	
	        Response entity = client.target("https://api.dropboxapi.com/2/auth/token/revoke")
	                .request(MediaType.APPLICATION_JSON_TYPE)
	                .header("Authorization", "Bearer "+tokenDropbox)
	                .post(null, Response.class);
	        
	        File fichier = new File("token.txt");
	        fichier.delete();
    	}
    	
    	if (tokenGoogle != "") {
    		Client client = ClientBuilder.newClient();
    		
	        Response entity = client.target("https://accounts.google.com/o/oauth2/revoke?token="+tokenGoogle)
	                .request(MediaType.APPLICATION_JSON_TYPE)
	                .header("Content-Type", "application/x-www-form-urlencoded")
	                .get();
	        
	        File fichier = new File("token_google.txt");
	        fichier.delete();
    	}
    	
    	if (tokenOneDrive != "") {
	    	Client client = ClientBuilder.newClient();
	    	
	        Response entity = client.target("https://login.microsoftonline.com/common/oauth2/v2.0/logout?post_logout_redirect_uri=http://localhost:8080/projet/redirect")
	                .request(MediaType.APPLICATION_JSON_TYPE)
	                .get();
	        
	        File fichier = new File("token_onedrive.txt");
	        fichier.delete();
    	}	
        
        
    }
    
    private String getString(String fichier) {
    	String access_token="";
    	try {
            FileReader reader = new FileReader(fichier);
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
