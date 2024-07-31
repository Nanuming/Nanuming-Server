package gdsc.nanuming.redis.repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import gdsc.nanuming.redis.entity.RefreshToken;

@Repository
public class RefreshTokenRepository {

	private final RedisTemplate<String, String> redisTemplate;

	private final Long refreshTokenPeriod;

	public RefreshTokenRepository(RedisTemplate<String, String> redisTemplate,
		@Value("${JWT_REFRESH_TOKEN_PERIOD}") String refreshTokenPeriod) {
		this.redisTemplate = redisTemplate;
		this.refreshTokenPeriod = Long.parseLong(refreshTokenPeriod);
	}

	public void save(final RefreshToken refreshToken) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		valueOperations.set(refreshToken.getProviderId(), refreshToken.getRefreshToken());
		redisTemplate.expire(refreshToken.getProviderId(), refreshTokenPeriod, TimeUnit.MILLISECONDS);
	}

	public Optional<RefreshToken> findByProviderId(final String providerId) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		String refreshToken = valueOperations.get(providerId);

		if (Objects.isNull(refreshToken)) {
			return Optional.empty();
		}

		return Optional.of(RefreshToken.of(providerId, refreshToken));
	}

	public void delete(final String providerId) {
		redisTemplate.delete(providerId);
	}

}
