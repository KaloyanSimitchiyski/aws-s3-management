package bg.uni.sofia.fmi.aws.s3.management.deploy;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import bg.uni.sofia.fmi.aws.s3.management.api.ObjectManagementApi;

@ApplicationPath("services")
public class S3ManagementApplication extends ResourceConfig {

	public S3ManagementApplication() {
		register(MultiPartFeature.class);

		AmazonS3 client = AmazonS3ClientBuilder.standard().withRegion("eu-central-1").build();
		register(new ObjectManagementApi(client));
	}
}
