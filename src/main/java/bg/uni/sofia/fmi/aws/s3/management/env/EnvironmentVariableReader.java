package bg.uni.sofia.fmi.aws.s3.management.env;

import static java.lang.String.format;

import java.util.Optional;

public class EnvironmentVariableReader {

	private static final String NO_VALUE_FOUND_MESSAGE = "No value found for environment variable '%s'.";

	public static String getVariable(EnvironmentVariable variable) {
		return Optional.ofNullable(System.getenv(variable.getKey()))
				.orElseThrow(() -> new IllegalArgumentException(format(NO_VALUE_FOUND_MESSAGE, variable.getKey())));
	}
}
