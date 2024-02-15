package gdsc.nanuming.redis.repository;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import gdsc.nanuming.redis.entity.ReservationCache;

@Repository
public class ReservationCacheRepository {

	private final RedisTemplate<Long, Long> redisTemplate;

	private final Long reservationExpiration;

	public ReservationCacheRepository(RedisTemplate<Long, Long> redisTemplate,
		@Value("${redis.reservation.expiration}") Long reservationExpiration) {
		this.redisTemplate = redisTemplate;
		this.reservationExpiration = reservationExpiration;
	}

	public void save(final ReservationCache reservationCache) {
		ValueOperations<Long, Long> valueOperations = redisTemplate.opsForValue();
		valueOperations.set(reservationCache.getMemberId(), reservationCache.getLockerId());
		redisTemplate.expire(reservationCache.getMemberId(), reservationExpiration, TimeUnit.MILLISECONDS);
	}

}
