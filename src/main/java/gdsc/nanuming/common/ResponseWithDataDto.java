package gdsc.nanuming.common;

public class ResponseWithDataDto<T> {

	private final boolean success;
	private final int status;
	private final String message;
	private final T data;
}
