package gdsc.nanuming.common.initializer.bulk;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ItemBulkRepository {

	private final JdbcTemplate template;
	private final Random random = new Random();

	public void bulkInsertItem(long start, long end) {
		log.info("bulkInsertItem {} ~ {} start", start, end - 1);

		final String sql = "INSERT INTO item("
			+ "category_id, "
			+ "created_at, "
			+ "locker_id, "
			+ "sharer_id, "
			+ "updated_at, "
			+ "description, "
			+ "item_status, "
			+ "title) "
			+ "VALUES (?,?,?,?,?,?,?,?)";

		List<Object[]> batchArgs = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String currentTime = LocalDateTime.now().format(formatter);

		for (long i = start; i < end; i++) {
			long categoryId = 1 + random.nextInt(5);
			long lockerId = (i - 1) * 6 + 1;
			long sharerId = 1 + random.nextInt(3);
			String description = i + "번 아이템 설명입니다.";
			String itemStatus = "AVAILABLE";
			String title = i + "번 아이템 제목입니다.";

			batchArgs.add(
				new Object[] {categoryId,
					currentTime,
					lockerId,
					sharerId,
					currentTime,
					description,
					itemStatus,
					title}
			);

		}

		template.batchUpdate(sql, batchArgs);
		log.info("bulkInsertItem {} ~ {} finished", start, end - 1);

	}

}
