package bg.uni.sofia.fmi.aws.s3.management.api;

import static bg.uni.sofia.fmi.aws.s3.management.api.Utils.buildAbsolutePath;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.DELETE;
import javax.ws.rs.NotFoundException;
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

	@POST
	@Path("{bucket}")
	public Response createBucket(@PathParam("bucket") String bucket) {
		String absoluteBucket = buildAbsolutePath(bucket);
		if (client.doesBucketExistV2(bucket)) {
			throw new BadRequestException();
		}

		client.createBucket(absoluteBucket);
		return Response.ok().build();
	}

	@DELETE
	@Path("{bucket}")
	public Response deleteEmptyBucket(@PathParam("bucket") String bucket) {
		String absoluteBucket = buildAbsolutePath(bucket);
		if (!client.doesBucketExistV2(absoluteBucket)) {
			throw new NotFoundException();
		}

		client.deleteBucket(absoluteBucket);
		return Response.ok().build();
	}
}
