package gdsc.nanuming.category.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gdsc.nanuming.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	Optional<Category> findByCategoryName(String categoryName);

}
