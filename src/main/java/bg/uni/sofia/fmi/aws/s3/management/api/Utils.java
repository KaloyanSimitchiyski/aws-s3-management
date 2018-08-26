package bg.uni.sofia.fmi.aws.s3.management.api;

class Utils {

	static final String SUFFIX = "/";

	public static String toFolder(String keyName) {
		return keyName + SUFFIX;
	}

	public static String toAbsolutePath(String folder, String file) {
		return toFolder(folder) + file;
	}
}
