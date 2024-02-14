package gdsc.nanuming.common.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Configuration
public class GoogleCloudStorageConfig {

	@Value("${sm://IMAGE_BUCKET_KEY}")
	private String base64Key;

	@Bean
	public Storage googleCloudStorage() throws IOException {
		byte[] decodedKey = Base64.getDecoder().decode(base64Key);
		GoogleCredentials credentials = GoogleCredentials.fromStream(new ByteArrayInputStream(decodedKey));
		return StorageOptions.newBuilder().setCredentials(credentials).build().getService();
	}

}
