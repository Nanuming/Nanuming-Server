package gdsc.nanuming.redis.repository;

import java.util.Objects;
import java.util.Optional;
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

	public Optional<BlacklistToken> findByAccessToken(final String accessToken) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		String status = valueOperations.get(accessToken);

		if (Objects.isNull(status)) {
			return Optional.empty();
		}
		return Optional.of(BlacklistToken.of(accessToken, status));
	}
}
