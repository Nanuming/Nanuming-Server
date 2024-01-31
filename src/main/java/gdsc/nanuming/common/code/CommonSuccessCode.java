package gdsc.nanuming.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonSuccessCode implements Code {

	RESPONSE_SUCCESS(true, 200, "요청이 완료되었습니다."),
	private final boolean success;
	private final int status;
	private final String message;

}
