package gdsc.nanuming.auth.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;

@Service
public class GoogleIdTokenVerifyService {

	private final GoogleIdTokenVerifier verifier;

	public GoogleIdTokenVerifyService(HttpTransport transport, GsonFactory gsonFactory,
		@Value("${IOS_CLIENT_ID}") String clientId) {
		this.verifier = new GoogleIdTokenVerifier.Builder(transport, gsonFactory)
			.setAudience(Collections.singletonList(clientId))
			.build();
	}

	public Payload verify(String idTokenString) {
		GoogleIdToken idToken = null;
		try {
			idToken = verifier.verify(idTokenString);
		} catch (GeneralSecurityException | IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		if (idToken != null) {
			return idToken.getPayload();
		} else {
			// TODO: need refactor here
			throw new IllegalArgumentException("Invalid ID Token.");
		}
	}
}
