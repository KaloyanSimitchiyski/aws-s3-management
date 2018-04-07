package bg.uni.sofia.fmi.aws.s3.management.rest.impl;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import bg.uni.sofia.fmi.aws.s3.management.rest.IObjectManagementApi;

public class ObjectManagementApi implements IObjectManagementApi {

	private final AmazonS3 client;

	public ObjectManagementApi(AmazonS3 client) {
		this.client = client;
	}

	@Override
	public Response download(String bucket, String object) {
		assertExistingObject(bucket, object);

		S3Object s3Object = client.getObject(bucket, object);
		return Response.ok().entity(s3Object.getObjectContent()).build();
	}

	@Override
	public Response getFileList(String bucket) {
		assertExistingBucket(bucket);

		List<S3ObjectSummary> summaries = client.listObjects(bucket) //
				.getObjectSummaries();
		List<String> objectNames = summaries.stream() //
				.map(S3ObjectSummary::getKey) //
				.collect(Collectors.toList());

		return Response.ok().entity(objectNames).build();
	}

	@Override
	public Response uploadFile(InputStream objectData, FormDataContentDisposition objectDetails, String bucket) {
		assertExistingBucket(bucket);

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setUserMetadata(objectDetails.getParameters());
		client.putObject(bucket, objectDetails.getFileName(), objectData, metadata);

		return Response.ok().build();
	}

	@Override
	public Response deleteFile(String bucket, String object) {
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
