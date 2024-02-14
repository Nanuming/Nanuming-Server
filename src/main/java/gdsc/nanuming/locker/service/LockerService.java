package gdsc.nanuming.locker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import gdsc.nanuming.locker.entity.Locker;
import gdsc.nanuming.locker.entity.LockerStatus;
import gdsc.nanuming.locker.repository.LockerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LockerService {

	private final LockerRepository lockerRepository;

	public List<Locker> occupiedLockerList(Long locationId) {

		return lockerRepository.findByLocationIdAndStatus(locationId,
			LockerStatus.OCCUPIED);
	}

}
