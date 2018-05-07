package com.example.simple_service;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthClientResponse;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ResponseType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     * @throws OAuthSystemException
     * @throws URISyntaxException
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getIt() throws OAuthSystemException, URISyntaxException {

        OAuthClientRequest request = OAuthClientRequest
                   .authorizationLocation("https://www.dropbox.com/oauth2/authorize")
                   .setResponseType("code")
                   .setClientId("yzkdkdpqg1t8vio")
                   .setRedirectURI("http://localhost:8080/myapp/redirect")
                   .buildQueryMessage();
        URI url = new URI(request.getLocationUri());
        //OAuthAuthzResponse oar = OAuthAuthzResponse.oauthCodeAuthzResponse(request);
        //String code = oar.getCode();
        //final OAuthClientResponse response = builder.location(redirectURI).buildQueryMessage();
        return Response.status(Status.MOVED_PERMANENTLY).location(url).build();

    }
    /*
    @GET
    public Response authorize(@Context HttpServletRequest request)
            throws URISyntaxException, OAuthSystemException {
        try {
            OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
            OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
            //build response according to response_type
            String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
            OAuthASResponse.OAuthAuthorizationResponseBuilder builder =
                    OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);

            //Authz code
            if (responseType.equals(ResponseType.CODE.toString())) {
                final String authorizationCode = oauthIssuerImpl.authorizationCode();
                database.addAuthCode(authorizationCode);
                builder.setCode(authorizationCode);
            }

            //Build response and redirect to given in request URI
            String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
            final OAuthResponse response = builder.location(redirectURI).buildQueryMessage();
            URI url = new URI(response.getLocationUri());

            //Send reponse to given URI
            return Response.status(response.getResponseStatus()).location(url).build();
    }
    //... handle errors and build response
    */

}
