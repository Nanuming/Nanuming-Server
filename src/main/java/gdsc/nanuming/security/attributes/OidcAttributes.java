package gdsc.nanuming.security.attributes;

import java.util.HashMap;
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
	private static final String UNDERSCORE = "_";

	@Builder
	private OidcAttributes(Map<String, Object> attributes, String attributeKey, String providerId, String email,
		String provider) {
		this.attributes = attributes;
		this.attributeKey = attributeKey;
		this.providerId = providerId;
		this.email = email;
		this.provider = provider;
	}

	public static OidcAttributes of(String provider, String attributeKey, Map<String, Object> attributes) {
		switch (provider) {
			case GOOGLE:
				return ofGoogle(provider, attributeKey, attributes);
			default:
				throw new RuntimeException();
				// TODO: create custom exception here
		}
	}

	private static OidcAttributes ofGoogle(String provider, String attributeKey, Map<String, Object> attributes) {

		// create UUID
		String providerId = provider + UNDERSCORE + attributes.get(attributeKey);

		return OidcAttributes.builder()
			.providerId(providerId)
			.email((String)attributes.get(EMAIL))
			.provider(provider)
			.attributes(attributes)
			.attributeKey(attributeKey)
			.build();
	}

	// return values as Map to put into OidcUser
	public Map<String, Object> convertToMap() {
		Map<String, Object> map = new HashMap<>();
		map.put(UUID, providerId);
		map.put(EMAIL, email);

		return map;
	}
}
