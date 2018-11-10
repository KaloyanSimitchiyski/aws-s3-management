package bg.uni.sofia.fmi.aws.s3.management.env;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

public class EnvironmentVariableReader {

	private static final String NOT_DEFINED_VARIABLE_MESSAGE = "Environment variable '%s' is not defined.";

	public static String readVariable(EnvironmentVariable variable) {
		return ofNullable(System.getenv(variable.getKey()))
				.orElseThrow(() -> new IllegalStateException(format(NOT_DEFINED_VARIABLE_MESSAGE, variable.getKey())));
	}
}
