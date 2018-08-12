package bg.uni.sofia.fmi.aws.s3.management.env;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

public class EnvironmentVariableReader {

	private static final String NO_VALUE_FOUND_MESSAGE = "No value found for environment variable '%s'.";

	public static String readVariable(EnvironmentVariable variable) {
		return ofNullable(System.getenv(variable.getKey()))
				.orElseThrow(() -> new IllegalArgumentException(format(NO_VALUE_FOUND_MESSAGE, variable.getKey())));
	}
}
