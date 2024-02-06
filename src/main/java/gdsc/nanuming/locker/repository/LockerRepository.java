package gdsc.nanuming.locker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gdsc.nanuming.locker.entity.Locker;

public interface LockerRepository extends JpaRepository<Locker, Long> {
}
