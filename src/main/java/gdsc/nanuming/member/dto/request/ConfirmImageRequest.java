package gdsc.nanuming.member.dto.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ConfirmImageRequest {

	private MultipartFile confirmImage;

	@Builder
	private ConfirmImageRequest(MultipartFile confirmImage) {
		this.confirmImage = confirmImage;
	}

	public static ConfirmImageRequest from(MultipartFile confirmImage) {
		return ConfirmImageRequest.builder()
			.confirmImage(confirmImage)
			.build();
	}
}
