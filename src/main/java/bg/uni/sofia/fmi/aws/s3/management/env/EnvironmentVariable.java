package bg.uni.sofia.fmi.aws.s3.management.env;

public enum EnvironmentVariable {

	ROOT_BUCKET("AWS_S3_ROOT_BUCKET", true), //
	AWS_REGION("AWS_REGION", true);

	private final String key;
	private boolean isMandatory;

	private EnvironmentVariable(String key, boolean isMandatory) {
		this.key = key;
		this.isMandatory = isMandatory;
	}

	public String getKey() {
		return key;
	}

	public boolean isMandatory() {
		return isMandatory;
	}
}
