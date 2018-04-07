package bg.uni.sofia.fmi.aws.s3.management.api;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM;
import static javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import bg.uni.sofia.fmi.aws.s3.management.env.EnvironmentVariable;
import bg.uni.sofia.fmi.aws.s3.management.env.EnvironmentVariableReader;

@Path("objects")
public class ObjectManagementApi {

	private final AmazonS3 client;

	public ObjectManagementApi(AmazonS3 client) {
		this.client = client;
	}

	@GET
	@Path("{bucket}/{object}")
	@Produces(APPLICATION_OCTET_STREAM)
	Response download(@PathParam("bucket") String bucket, @PathParam("object") String object) {
		assertExistingObject(bucket, object);

		S3Object s3Object = client.getObject(bucket, object);
		return Response.ok().entity(s3Object.getObjectContent()).build();
	}

	@GET
	@Path("{bucket}")
	@Produces(APPLICATION_JSON)
	Response getFileList(@PathParam("bucket") String bucket) {
		EnvironmentVariableReader.getVariable(EnvironmentVariable.AWS_REGION);
		assertExistingBucket(bucket);

		List<S3ObjectSummary> summaries = client.listObjects(bucket) //
				.getObjectSummaries();
		List<String> objectNames = summaries.stream() //
				.map(S3ObjectSummary::getKey) //
				.collect(Collectors.toList());

		return Response.ok().entity(objectNames).build();
	}

	@POST
	@Path("{bucket}")
	@Consumes(MULTIPART_FORM_DATA)
	Response uploadFile(@FormDataParam("object") InputStream objectData,
			@FormDataParam("object") FormDataContentDisposition objectDetails, @PathParam("bucket") String bucket) {
		assertExistingBucket(bucket);

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setUserMetadata(objectDetails.getParameters());
		client.putObject(bucket, objectDetails.getFileName(), objectData, metadata);

		return Response.ok().build();
	}

	@DELETE
	@Path("{bucket}/{object}")
	Response deleteFile(@PathParam("bucket") String bucket, @PathParam("object") String object) {
		assertExistingObject(bucket, object);

		client.deleteObject(bucket, object);
		return Response.ok().build();
	}

	private void assertExistingBucket(String bucket) {
		if (!client.doesBucketExistV2(bucket)) {
			throw new NotFoundException();
		}
	}

	private void assertExistingObject(String bucket, String object) {
		if (!client.doesObjectExist(bucket, object)) {
			throw new NotFoundException();
		}
	}
}
