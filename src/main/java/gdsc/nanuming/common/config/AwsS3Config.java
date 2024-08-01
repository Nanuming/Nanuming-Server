package gdsc.nanuming.common.config;

@Configuration
public class AwsS3Config {

	@Value("${AWS_REGION}")
	private String awsRegion;

}
