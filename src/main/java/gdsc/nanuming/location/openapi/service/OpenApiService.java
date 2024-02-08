package gdsc.nanuming.location.openapi.service;

import static gdsc.nanuming.location.openapi.OpenApiConstant.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

import gdsc.nanuming.location.entity.Location;
import gdsc.nanuming.location.openapi.dto.LocationApiDto;
import gdsc.nanuming.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenApiService {

	private final LocationRepository locationRepository;

	@Value("${sm://CHILD_CARE_INFO_API_URL}")
	private String childCareInfoApiUrl;

	public void callChildCareInfoApi() {
		log.info(">>> OpenApiService callChildCareInfoApi()");
		try {
			int totalCount = getTotalCount();
			log.info(">>> OpenApiService callChildCareInfoApi() totalCount: {}", totalCount);
			for (int i = START_INDEX; i <= totalCount; i += STEP) {
				int endIndex = i + STEP - 1;
				if (endIndex > totalCount) {
					endIndex = totalCount;
				}
				JSONArray jsonArray = fetchData(i, endIndex);
				saveData(jsonArray);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private JSONArray fetchData(int startIndex, int endIndex) throws Exception {
		log.info(">>> OpenApiService fetchData()");
		String request = sendRequest(startIndex, endIndex);
		JSONObject childCareInfoObject = getChildCareInfoObject(request);
		return (JSONArray)childCareInfoObject.get(ROW);
	}

	private void saveData(JSONArray jsonArray) {
		log.info(">>> OpenApiService saveData()");
		for (Object object : jsonArray) {
			JSONObject row = (JSONObject)object;

			// filter data that does not include latitude and longitude values
			if (row.getAsString(LA).isEmpty() || row.getAsString(LO).isEmpty()) {
				continue;
			}

			LocationApiDto locationApiDto = convertIntoLocationApiDto(row);
			log.info(">>> OpenApiService saveData() locationApiDto: {}", locationApiDto);

			Location location = locationApiDto.toEntity();

			locationRepository.save(location);
		}
	}

	private String sendRequest(int startIndex, int endIndex) throws Exception {
		URL url = new URL(childCareInfoApiUrl + SLASH + startIndex + SLASH + endIndex);
		log.info(">>> OpenApiService sendRequest() url: {}", url);
		HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
		httpURLConnection.setRequestMethod(GET);
		httpURLConnection.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
		BufferedReader bufferedReader = new BufferedReader(
			new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
		return bufferedReader.readLine();
	}

	private int getTotalCount() throws Exception {
		log.info(">>> OpenApiService getTotalCount()");
		String initialRequest = sendRequest(START_INDEX, START_INDEX);
		log.info(">>> OpenApiService getTotalCount() initialRequest: {}", initialRequest);
		JSONObject childCareInfoObject = getChildCareInfoObject(initialRequest);
		return Integer.parseInt(childCareInfoObject.getAsString(LIST_TOTAL_COUNT));
	}

	private JSONObject getChildCareInfoObject(String request) throws Exception {
		JSONParser jsonParser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
		JSONObject jsonObject = (JSONObject)jsonParser.parse(request);
		return (JSONObject)jsonObject.get(CHILD_CARE_INFO);
	}

	private LocationApiDto convertIntoLocationApiDto(JSONObject row) {
		return LocationApiDto.of(
			row.getAsNumber(STCODE).longValue(),
			row.getAsNumber(ZIPCODE).longValue(),
			row.getAsString(CRNAME),
			row.getAsString(CRADDR) + WHITE_SPACE,
			Double.parseDouble(row.getAsString(LA)),
			Double.parseDouble(row.getAsString(LO))
		);
	}
}
