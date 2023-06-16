package beatalbumshop.dao;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * The SpotifyApi class provides methods for interacting with the Spotify API.
 */
public class SpotifyApi {
    
    /**
     * Retrieves the access token from the Spotify API using the provided client ID and client secret.
     *
     * @param clientId     the client ID for authentication
     * @param clientSecret the client secret for authentication
     * @return the access token
     * @throws IOException if an I/O error occurs while making the API request
     */
    public static String getAccessToken(String clientId, String clientSecret) throws IOException {
        String authEndpoint = "https://accounts.spotify.com/api/token";
        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(authEndpoint);
        request.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodedCredentials);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
        request.setEntity(new StringEntity("grant_type=client_credentials"));

        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        String responseBody = EntityUtils.toString(entity);
        JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
        return jsonResponse.get("access_token").getAsString();
    }

    /**
     * Makes a request to the Spotify API endpoint using the provided access token.
     *
     * @param endpoint    the endpoint URL
     * @param accessToken the access token for authentication
     * @return the JSON response from the API
     * @throws IOException if an I/O error occurs while making the API request
     */
    public static JsonObject makeRequest(String endpoint, String accessToken) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpUriRequest request = new HttpGet(endpoint);
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        String responseBody = EntityUtils.toString(entity);
        return JsonParser.parseString(responseBody).getAsJsonObject();
    }
}
