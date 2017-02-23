package org.fusio.sample.javacli;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.fusio.sample.javacli.model.Collection;
import org.fusio.sample.javacli.model.Message;
import org.fusio.sample.javacli.model.Todo;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

public class Main {

    private static final java.io.File DATA_STORE_DIR = new java.io.File("fusio_store");
    private static final String SCOPE = "todo,authorization";
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();

    private static final String API_ENDPOINT = "http://demo.fusio-project.org";
    private static final String TOKEN_SERVER_URL = API_ENDPOINT + "/authorization/token";
    private static final String AUTHORIZATION_SERVER_URL = "http://demo.fusio-project.org/developer/auth";

    private static final String API_KEY = "7f42461e-592c-4cb7-b224-4e22a664b3ad";
    private static final String API_SECRET = "834beb6235e6c47606da44eeb1366a52a3f5a7da448a07846a89cd361718c843";
    private static final int PORT = 8080;
    private static final String DOMAIN = "127.0.0.1";

    private static Credential credential;

    public static void main(String[] args) {
	try {
	    if (args.length > 0) {
		Todo todo = new Todo();
		todo.setTitle(args[0]);

		postTodo(todo);
	    } else {
		getTodo();
	    }

	    return;
	} catch (IOException e) {
	    System.err.println(e.getMessage());
	} catch (Throwable t) {
	    t.printStackTrace();
	}
	System.exit(1);
    }

    /**
     * Authorizes the installed application to access user's protected data.
     */
    private static void authorize() throws IOException {
	AuthorizationCodeFlow.Builder flowBuilder = new AuthorizationCodeFlow.Builder(
		BearerToken.authorizationHeaderAccessMethod(), HTTP_TRANSPORT,
		JSON_FACTORY, new GenericUrl(TOKEN_SERVER_URL),
		new ClientParametersAuthentication(API_KEY, API_SECRET),
		API_KEY, AUTHORIZATION_SERVER_URL);

	flowBuilder.setScopes(Arrays.asList(SCOPE));
	flowBuilder
		.setDataStoreFactory(new FileDataStoreFactory(DATA_STORE_DIR));

	LocalServerReceiver.Builder receiverBuilder = new LocalServerReceiver.Builder();
	receiverBuilder.setHost(DOMAIN);
	receiverBuilder.setPort(PORT);

	AuthorizationCodeInstalledApp app = new AuthorizationCodeInstalledApp(
		flowBuilder.build(), receiverBuilder.build());

	credential = app.authorize("user");
    }

    private static void getTodo() throws IOException {
	Collection collection = sendGetRequest(API_ENDPOINT + "/todo",
		Collection.class);
	List<Todo> entries = collection.getEntry();

	System.out.println("Total count: " + collection.getTotalResults());

	for (int i = 0; i < entries.size(); i++) {
	    System.out.println("Title: " + entries.get(i).getTitle());
	    System.out
		    .println("Insert date: " + entries.get(i).getInsertDate());
	}
    }

    private static void postTodo(Todo todo) throws IOException {
	authorize();

	Message message = sendPostRequest(API_ENDPOINT + "/todo", todo,
		Message.class);

	System.out.println(message.getMessage());
    }

    private static <T> T sendGetRequest(String url, Class<T> responseClass)
	    throws IOException {
	return sendRequest(url, null, responseClass, false);
    }

    private static <T> T sendPostRequest(String url, Object body,
	    Class<T> responseClass) throws IOException {
	return sendRequest(url, body, responseClass, true);
    }

    private static <T> T sendRequest(String url, Object body,
	    Class<T> responseClass, final boolean authorized)
	    throws IOException {
	HttpRequestFactory requestFactory = HTTP_TRANSPORT
		.createRequestFactory(new HttpRequestInitializer() {
		    public void initialize(HttpRequest request)
			    throws IOException {
			if (authorized) {
			    credential.initialize(request);
			}

			request.setParser(new JsonObjectParser(JSON_FACTORY));
		    }
		});

	HttpHeaders headers = new HttpHeaders();
	headers.setAccept("application/json");
	HttpRequest request;

	if (body == null) {
	    request = requestFactory.buildGetRequest(new GenericUrl(url));
	} else {
	    headers.setContentType("application/json");
	    request = requestFactory.buildPostRequest(new GenericUrl(url),
		    new JsonHttpContent(JSON_FACTORY, body));
	}

	request.setHeaders(headers);

	return request.execute().parseAs(responseClass);
    }
}
