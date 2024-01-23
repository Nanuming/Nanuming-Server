package gdsc.nanuming.common.code;

public interface Code {

	boolean isSuccess();

	int getStatus();

	int getCode();

	String getMessage();

}
