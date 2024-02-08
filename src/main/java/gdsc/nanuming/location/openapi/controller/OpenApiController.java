package gdsc.nanuming.location.openapi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/openapi")
public class OpenApiController {

	@Value("${sm://CHILD_CARE_INFO_API_KEY}")
	private String childCareInfoApiKey;

	@Value("${sm://CHILD_CARE_INFO_API_URL}")
	private String childCareInfoApiUrl;

	@Value("${sm://CHILD_CARE_INFO_API_DATA_TYPE}")
	private String childCareInfoApiDataType;

}
