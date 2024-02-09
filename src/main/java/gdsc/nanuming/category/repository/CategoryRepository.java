package gdsc.nanuming.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gdsc.nanuming.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
