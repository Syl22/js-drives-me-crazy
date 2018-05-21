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
}
