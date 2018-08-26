package bg.uni.sofia.fmi.aws.s3.management.api;

import static bg.uni.sofia.fmi.aws.s3.management.api.Utils.buildAbsoluteBucket;
import static javax.ws.rs.core.Response.status;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;

import javax.ws.rs.DELETE;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.amazonaws.services.s3.AmazonS3;

@Path("buckets")
public class BucketManagementApi {

	private final AmazonS3 client;

	public BucketManagementApi(AmazonS3 client) {
		this.client = client;
	}

	@HEAD
	@Path("bucket")
	public Response isExistingBucket(@PathParam("bucket") String bucket) {
		String absoluteBucket = buildAbsoluteBucket(bucket);
		if (!client.doesBucketExistV2(absoluteBucket)) {
			return status(NOT_FOUND).build();
		}

		return status(OK).build();
	}

	@POST
	@Path("{bucket}")
	public Response createBucket(@PathParam("bucket") String bucket) {
		String absoluteBucket = buildAbsoluteBucket(bucket);
		if (client.doesBucketExistV2(bucket)) {
			return status(BAD_REQUEST).build();
		}

		client.createBucket(absoluteBucket);
		return status(OK).build();
	}

	@DELETE
	@Path("{bucket}")
	public Response deleteEmptyBucket(@PathParam("bucket") String bucket) {
		String absoluteBucket = buildAbsoluteBucket(bucket);
		if (!client.doesBucketExistV2(absoluteBucket)) {
			return status(NOT_FOUND).build();
		}

		client.deleteBucket(absoluteBucket);
		return status(OK).build();
	}
}
