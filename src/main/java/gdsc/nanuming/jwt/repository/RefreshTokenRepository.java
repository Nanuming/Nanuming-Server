package gdsc.nanuming.jwt.repository;

import org.springframework.data.repository.CrudRepository;

import gdsc.nanuming.jwt.entity.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
