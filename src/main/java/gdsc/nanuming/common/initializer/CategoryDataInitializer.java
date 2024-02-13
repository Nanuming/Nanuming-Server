package gdsc.nanuming.common.initializer;

import static gdsc.nanuming.category.entity.CategoryName.*;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import gdsc.nanuming.category.entity.Category;
import gdsc.nanuming.category.repository.CategoryRepository;
import gdsc.nanuming.location.openapi.event.OpenApiLoadedEvent;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategoryDataInitializer {

	private final CategoryRepository categoryRepository;

	@EventListener
	private void categoryDataInsert(OpenApiLoadedEvent event) {

		categoryRepository.save(Category.from(TOY));
		categoryRepository.save(Category.from(BOOK));
		categoryRepository.save(Category.from(CLOTHES));
		categoryRepository.save(Category.from(NURTURE_PRODUCT));
		categoryRepository.save(Category.from(ETC));

	}

}
