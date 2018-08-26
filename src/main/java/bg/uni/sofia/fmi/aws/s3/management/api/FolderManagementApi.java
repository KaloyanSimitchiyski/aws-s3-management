package bg.uni.sofia.fmi.aws.s3.management.api;

import static bg.uni.sofia.fmi.aws.s3.management.api.Utils.toFolder;
import static java.util.Objects.requireNonNull;
import static javax.ws.rs.core.Response.status;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.ws.rs.DELETE;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Path("folders")
public class FolderManagementApi {

	private static final InputStream EMPTY_STREAM = new ByteArrayInputStream(new byte[0]);
	private static final ObjectMetadata EMPTY_METADATA = new ObjectMetadata();
	static {
		EMPTY_METADATA.setContentLength(0);
	}

	private final AmazonS3 client;
	private final String rootBucket;

	public FolderManagementApi(AmazonS3 client, String rootBucket) {
		this.client = requireNonNull(client);
		this.rootBucket = requireNonNull(rootBucket);
	}

	@HEAD
	@Path("{folder}")
	public Response isExistingFolder(@PathParam("folder") String folder) {
		if (!client.doesObjectExist(rootBucket, toFolder(folder))) {
			return status(NOT_FOUND).build();
		}

		return status(OK).build();
	}

	@POST
	@Path("{folder}")
	public Response createFolder(@PathParam("folder") String folder) {
		if (client.doesObjectExist(rootBucket, toFolder(folder))) {
			return status(BAD_REQUEST).build();
		}

		client.putObject(rootBucket, toFolder(folder), EMPTY_STREAM, EMPTY_METADATA);
		return status(CREATED).build();
	}

	@DELETE
	@Path("{folder}")
	public Response deleteFolder(@PathParam("folder") String folder) {
		if (!client.doesObjectExist(rootBucket, toFolder(folder))) {
			return status(NOT_FOUND).build();
		}

		client.deleteObject(rootBucket, toFolder(folder));
		return status(OK).build();
	}
}
