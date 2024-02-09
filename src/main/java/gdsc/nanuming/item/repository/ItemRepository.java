package gdsc.nanuming.item.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gdsc.nanuming.item.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

	List<Item> findByLockerId(Long lockerId);

}
