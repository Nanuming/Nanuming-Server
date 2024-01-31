package gdsc.nanuming.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonSuccessCode implements Code {
	private final boolean success;
	private final int status;
	private final String message;

}
