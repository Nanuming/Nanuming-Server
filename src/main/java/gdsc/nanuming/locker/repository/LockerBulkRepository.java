package gdsc.nanuming.locker.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import gdsc.nanuming.locker.entity.LockerSize;
import gdsc.nanuming.locker.entity.LockerStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LockerBulkRepository {

	private final JdbcTemplate template;

	private static final int SMALL_COUNT = 3;
	private static final int MIDDLE_COUNT = 2;
	private static final int BIG_COUNT = 1;

	public void bulkInsertLocker(long start, long end) {
		log.info("{} ~ {} start", start, end);

		final String sql = "INSERT INTO locker (location_id, size, status) VALUES (?, ?, ?)";

		List<Object[]> batchArgs = new ArrayList<>();

		for (long i = start; i <= end; i++) {
			for (int j = 0; j < SMALL_COUNT; j++) {
				batchArgs.add(
					new Object[] {i, LockerSize.SMALL.toString(), LockerStatus.EMPTY.toString()});
			}
			for (int j = 0; j < MIDDLE_COUNT; j++) {
				batchArgs.add(
					new Object[] {i, LockerSize.MIDDLE.toString(), LockerStatus.EMPTY.toString()});
			}
			for (int j = 0; j < BIG_COUNT; j++) {
				batchArgs.add(
					new Object[] {i, LockerSize.BIG.toString(), LockerStatus.EMPTY.toString()});
			}
		}

		template.batchUpdate(sql, batchArgs);
		log.info("{} ~ {} finished", start, end);
	}

}
