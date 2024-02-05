package gdsc.nanuming.redis.repository;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import gdsc.nanuming.redis.entity.BlacklistToken;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BlacklistTokenRepository {

	private final RedisTemplate<String, String> redisTemplate;

	public void save(final BlacklistToken blacklistToken, long remainingExpirationTime) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		valueOperations.set(blacklistToken.getAccessToken(), blacklistToken.getStatus());
		redisTemplate.expire(blacklistToken.getAccessToken(), remainingExpirationTime, TimeUnit.MILLISECONDS);
	}

}
