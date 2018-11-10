package bg.uni.sofia.fmi.aws.s3.management.api;

import static bg.uni.sofia.fmi.aws.s3.management.api.Utils.toAbsolutePath;
import static bg.uni.sofia.fmi.aws.s3.management.api.Utils.toFolder;
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
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@Path("files")
public class FileManagementApi {

	private final AmazonS3 client;
	private final String rootBucket;

	public FileManagementApi(AmazonS3 client, String rootBucket) {
		this.client = client;
		this.rootBucket = rootBucket;
	}

	@GET
	@Path("{folder}/{file}")
	@Produces(APPLICATION_OCTET_STREAM)
	public Response download(@PathParam("folder") String folder, @PathParam("file") String file) {
		try {
			String absolutePath = toAbsolutePath(folder, file);
			if (!client.doesObjectExist(rootBucket, toAbsolutePath(folder, file))) {
				return status(NOT_FOUND).build();
			}

			S3Object s3Object = client.getObject(rootBucket, absolutePath);
			return status(OK).entity(s3Object.getObjectContent()).build();
		} catch (IllegalArgumentException e) {
			return status(BAD_REQUEST).build();
		} catch (RuntimeException e) {
			return status(INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("{folder}")
	@Produces(APPLICATION_JSON)
	public Response getFileList(@PathParam("folder") String folder) {
		try {
			if (!client.doesObjectExist(rootBucket, toFolder(folder))) {
				return status(NOT_FOUND).build();
			}

			ListObjectsV2Request request = new ListObjectsV2Request() //
					.withBucketName(rootBucket) //
					.withPrefix(toFolder(folder));

			ListObjectsV2Result result = client.listObjectsV2(request);
			List<String> fileNames = result.getObjectSummaries().stream() //
					.map(S3ObjectSummary::getKey) //
					.collect(Collectors.toList());

			return status(OK).entity(fileNames).build();
		} catch (IllegalArgumentException e) {
			return status(NOT_FOUND).build();
		} catch (RuntimeException e) {
			return status(INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Path("{folder}")
	@Consumes(MULTIPART_FORM_DATA)
	public Response uploadFile(@FormDataParam("file") InputStream fileData,
			@FormDataParam("file") FormDataContentDisposition fileDetails, @PathParam("folder") String folder) {
		if (!client.doesObjectExist(rootBucket, toFolder(folder))) {
			return status(BAD_REQUEST).build();
		}

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setUserMetadata(fileDetails.getParameters());
		client.putObject(rootBucket, toAbsolutePath(folder, fileDetails.getFileName()), fileData, metadata);

		return status(CREATED).build();
	}

	@DELETE
	@Path("{folder}/{file}")
	public Response deleteFile(@PathParam("folder") String folder, @PathParam("file") String file) {
		String absolutePath = toAbsolutePath(folder, file);
		if (!client.doesObjectExist(rootBucket, absolutePath)) {
			return status(NOT_FOUND).build();
		}

		client.deleteObject(rootBucket, absolutePath);
		return status(OK).build();
	}
}
