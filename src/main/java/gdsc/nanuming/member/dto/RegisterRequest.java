package gdsc.nanuming.member.dto;

import lombok.Getter;

@Getter
public class RegisterRequest {

	private String email;
	private String nickname;
	private String provider;
	private String providerId;
	private String picture;

}
