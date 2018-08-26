package bg.uni.sofia.fmi.aws.s3.management.api;

import static bg.uni.sofia.fmi.aws.s3.management.api.Utils.buildAbsoluteBucket;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM;
import static javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA;
import static javax.ws.rs.core.Response.status;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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
		try {
			String absoluteBucket = buildExistingBucket(bucket);
			if (!isExistingObject(absoluteBucket, object)) {
				return status(NOT_FOUND).build();
			}

			S3Object s3Object = client.getObject(absoluteBucket, object);
			return status(OK).entity(s3Object.getObjectContent()).build();
		} catch (IllegalArgumentException e) {
			return status(BAD_REQUEST).build();
		} catch (RuntimeException e) {
			return status(INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("{bucket}")
	@Produces(APPLICATION_JSON)
	Response getFileList(@PathParam("bucket") String bucket) {
		try {
			String absoluteBucket = buildExistingBucket(bucket);

			List<S3ObjectSummary> summaries = client.listObjects(absoluteBucket) //
					.getObjectSummaries();
			List<String> objectNames = summaries.stream() //
					.map(S3ObjectSummary::getKey) //
					.collect(Collectors.toList());

			return status(OK).entity(objectNames).build();
		} catch (IllegalArgumentException e) {
			return status(BAD_REQUEST).build();
		} catch (RuntimeException e) {
			return status(INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Path("{bucket}")
	@Consumes(MULTIPART_FORM_DATA)
	Response uploadFile(@FormDataParam("object") InputStream objectData,
			@FormDataParam("object") FormDataContentDisposition objectDetails, @PathParam("bucket") String bucket) {
		try {
			String absoluteBucket = buildExistingBucket(bucket);

			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setUserMetadata(objectDetails.getParameters());
			client.putObject(absoluteBucket, objectDetails.getFileName(), objectData, metadata);

			return status(CREATED).build();
		} catch (IllegalArgumentException e) {
			return status(BAD_REQUEST).build();
		} catch (RuntimeException e) {
			return status(INTERNAL_SERVER_ERROR).build();
		}
	}

	@DELETE
	@Path("{bucket}/{object}")
	Response deleteFile(@PathParam("bucket") String bucket, @PathParam("object") String object) {
		try {
			String absoluteBucket = buildExistingBucket(bucket);
			isExistingObject(absoluteBucket, object);

			client.deleteObject(absoluteBucket, object);
			return status(OK).build();
		} catch (IllegalArgumentException e) {
			return status(BAD_REQUEST).build();
		} catch (RuntimeException e) {
			return status(INTERNAL_SERVER_ERROR).build();
		}
	}

	private String buildExistingBucket(String bucket) {
		String absoluteBucket = buildAbsoluteBucket(bucket);
		if (!client.doesBucketExistV2(absoluteBucket)) {
			throw new IllegalArgumentException();
		}

		return absoluteBucket;
	}

	private boolean isExistingObject(String bucket, String object) {
		return !client.doesObjectExist(bucket, object);
	}
}
