package gdsc.nanuming.security.attributes;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OidcAttributes {

	private final Map<String, Object> attributes; // User attributes information
	private final String attributeKey; // User attributes key
	private final String providerId; // Provider + User unique number
	private final String email; // User email
	private final String provider; // IdP

	private static final String GOOGLE = "google";

	private static final String KEY = "key";
	private static final String UUID = "providerId";
	private static final String EMAIL = "email";
	private static final String PROVIDER = "provider";

	@Builder
	private OidcAttributes(Map<String, Object> attributes, String attributeKey, String providerId, String email,
		String provider) {
		this.attributes = attributes;
		this.attributeKey = attributeKey;
		this.providerId = providerId;
		this.email = email;
		this.provider = provider;
	}

	private static OidcAttributes ofGoogle(String provider, String attributeKey, Map<String, Object> attributes) {

		// create UUID
		String providerId = provider + attributes.get(attributeKey);

		return OidcAttributes.builder()
			.providerId(providerId)
			.email((String)attributes.get(EMAIL))
			.provider(provider)
			.attributes(attributes)
			.attributeKey(attributeKey)
			.build();
	}
}
