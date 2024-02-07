package gdsc.nanuming.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gdsc.nanuming.image.entity.ItemImage;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
}
