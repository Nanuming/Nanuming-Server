package gdsc.nanuming.locker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gdsc.nanuming.item.entity.ItemStatus;
import gdsc.nanuming.locker.entity.Locker;
import gdsc.nanuming.locker.entity.LockerStatus;

public interface LockerRepository extends JpaRepository<Locker, Long> {

	List<Locker> findByLocationIdAndStatusAndItemItemStatus(Long locationId, LockerStatus status, ItemStatus itemStatus);

}
