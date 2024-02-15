package gdsc.nanuming.redis.repository;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import gdsc.nanuming.redis.entity.ReservationCache;

@Repository
public class ReservationCacheRepository {

	private final RedisTemplate<String, String> redisTemplate;

	private final Long reservationExpiration;

	public ReservationCacheRepository(RedisTemplate<String, String> redisTemplate,
		@Value("${redis.reservation.expiration}") Long reservationExpiration) {
		this.redisTemplate = redisTemplate;
		this.reservationExpiration = reservationExpiration;
	}

	public void save(final ReservationCache reservationCache) {
		String compositeKey = reservationCache.getMemberId() + ":" + reservationCache.getLockerId();
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		valueOperations.set(compositeKey, "reserved");
		redisTemplate.expire(compositeKey, reservationExpiration, TimeUnit.SECONDS);
	}

	public boolean existsByMemberIdAndLockerId(Long memberId, Long lockerId) {
		String compositeKey = memberId + ":" + lockerId;
		return Boolean.TRUE.equals(redisTemplate.hasKey(compositeKey));
	}

	public void deleteByMemberIdAndLockerId(Long memberId, Long lockerId) {
		String compositeKey = memberId + ":" + lockerId;
		redisTemplate.delete(compositeKey);
	}

}
