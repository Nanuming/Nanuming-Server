package gdsc.nanuming.location.openapi.event;

import org.springframework.context.ApplicationEvent;

public class OpenApiLoadedEvent extends ApplicationEvent {
	public OpenApiLoadedEvent(Object source) {
		super(source);
	}
}
