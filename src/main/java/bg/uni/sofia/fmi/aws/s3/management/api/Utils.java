package bg.uni.sofia.fmi.aws.s3.management.api;

import static bg.uni.sofia.fmi.aws.s3.management.env.EnvironmentVariable.ROOT_BUCKET;
import static bg.uni.sofia.fmi.aws.s3.management.env.EnvironmentVariableReader.readVariable;
import static java.lang.String.format;

class Utils {

	public static String buildAbsolutePath(String bucket) {
		return format("%s/%s", readVariable(ROOT_BUCKET), bucket);
	}
}
