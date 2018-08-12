package bg.uni.sofia.fmi.aws.s3.management.deploy;

import static bg.uni.sofia.fmi.aws.s3.management.env.EnvironmentVariable.AWS_REGION;
import static bg.uni.sofia.fmi.aws.s3.management.env.EnvironmentVariableReader.readVariable;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import bg.uni.sofia.fmi.aws.s3.management.api.BucketManagementApi;
import bg.uni.sofia.fmi.aws.s3.management.api.ObjectManagementApi;
import bg.uni.sofia.fmi.aws.s3.management.env.EnvironmentVariable;

@ApplicationPath("services")
public class S3ManagementApplication extends ResourceConfig {

	public S3ManagementApplication() {
		ensureMandatoryEnvironmentVariables();

		register(MultiPartFeature.class);

		AmazonS3 client = buildAmazonClient();
		register(new ObjectManagementApi(client));
		register(new BucketManagementApi(client));
	}

	private AmazonS3 buildAmazonClient() {
		return AmazonS3ClientBuilder.standard().withRegion(readVariable(AWS_REGION)).build();
	}

	private static void ensureMandatoryEnvironmentVariables() {
		for (EnvironmentVariable variable : EnvironmentVariable.values()) {
			if (variable.isMandatory()) {
				readVariable(variable);
			}
		}
	}
}
