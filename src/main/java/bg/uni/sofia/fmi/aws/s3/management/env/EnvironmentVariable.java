package bg.uni.sofia.fmi.aws.s3.management.env;

public enum EnvironmentVariable {

	ROOT_BUCKET("AWS_S3_ROOT_BUCKET"), //
	AWS_REGION("AWS_REGION");

	private final String key;

	private EnvironmentVariable(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
