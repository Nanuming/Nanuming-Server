package gdsc.nanuming.redis.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import gdsc.nanuming.reservation.service.ReservationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RedisKeyExpiredListener extends KeyExpirationEventMessageListener {

	private final ReservationService reservationService;

	public RedisKeyExpiredListener(RedisMessageListenerContainer listenerContainer,
		ReservationService reservationService) {
		super(listenerContainer);
		this.reservationService = reservationService;
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		String expiredKey = message.toString();
		log.info(">>> expiredKey: {}", expiredKey);

		String[] splitted = expiredKey.split(":");
		Long memberId = Long.valueOf(splitted[0]);
		Long lockerId = Long.valueOf(splitted[1]);

		reservationService.changeStatusToExpired(memberId, lockerId);
	}
}
