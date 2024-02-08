package gdsc.nanuming.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gdsc.nanuming.location.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
