package esir.jxs;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getIt() throws Exception {
        OAuthClientRequest request = OAuthClientRequest
                .authorizationLocation("https://www.dropbox.com/oauth2/authorize")
                .setClientId("yzkdkdpqg1t8vio")
                .setResponseType("code")
                .setRedirectURI("http://localhost:8080/projet/redirect")
                .buildQueryMessage();

        URI url = new URI(request.getLocationUri());
        return Response.status(Response.Status.TEMPORARY_REDIRECT).location(url).build();
    }
}
