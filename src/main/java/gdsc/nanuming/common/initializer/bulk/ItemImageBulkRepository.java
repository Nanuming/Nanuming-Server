package gdsc.nanuming.common.initializer.bulk;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ItemImageBulkRepository {

	private final JdbcTemplate template;

	@Value("${S3_BASE_URL}")
	private String s3BaseUrl;

	@Value("${MAIN_IMAGE_PATH}")
	private String mainImagePath;

	@Value("${CONFIRM_IMAGE_PATH}")
	private String confirmImagePath;

	public void bulkInsertItemImage(long start, long end) {
		log.info("bulkInsertItemImage {} ~ {} start", start, end - 1);

		final String sql = "INSERT INTO item_image("
			+ "is_confirm, "
			+ "is_main, "
			+ "created_at, "
			+ "item_id, "
			+ "updated_at, "
			+ "image_url) "
			+ "VALUES (?,?,?,?,?,?)";

		List<Object[]> batchArgs = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String currentTime = LocalDateTime.now().format(formatter);

		for (long i = start; i < end; i++) {
			Object[] mainImageArgs = {false, true, currentTime, i, currentTime,
				s3BaseUrl + mainImagePath};
			Object[] confirmImageArgs = {true, false, currentTime, i, currentTime,
				s3BaseUrl + confirmImagePath};

			batchArgs.add(mainImageArgs);
			batchArgs.add(confirmImageArgs);
		}

		template.batchUpdate(sql, batchArgs);
		log.info("bulkInsertItemImage {} ~ {} finished", start, end - 1);
	}

}
