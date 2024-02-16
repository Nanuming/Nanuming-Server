package gdsc.nanuming.common.initializer;

import static gdsc.nanuming.category.entity.CategoryName.*;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import gdsc.nanuming.category.entity.Category;
import gdsc.nanuming.category.repository.CategoryRepository;
import gdsc.nanuming.location.openapi.event.OpenApiLoadedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryDataInitializer {

	private final CategoryRepository categoryRepository;

	@Order(1)
	@EventListener
	@Transactional
	public void categoryDataInsert(OpenApiLoadedEvent event) {

		log.info("categoryDataInsert start");
		categoryRepository.save(Category.from(TOY));
		categoryRepository.save(Category.from(BOOK));
		categoryRepository.save(Category.from(CLOTHES));
		categoryRepository.save(Category.from(NURTURE_PRODUCT));
		categoryRepository.save(Category.from(ETC));
		log.info("categoryDataInsert finished");

	}

}
