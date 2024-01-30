package gdsc.nanuming.security.attributes;

import java.util.Map;

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

}
