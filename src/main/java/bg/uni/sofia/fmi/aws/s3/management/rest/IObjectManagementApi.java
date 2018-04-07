package bg.uni.sofia.fmi.aws.s3.management.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM;
import static javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA;

import java.io.InputStream;

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

@Path("objects")
public interface IObjectManagementApi {

	@GET
	@Path("{bucket}/{object}")
	@Produces(APPLICATION_OCTET_STREAM)
	Response download(@PathParam("bucket") String bucket, @PathParam("object") String object);

	@GET
	@Path("{bucket}")
	@Produces(APPLICATION_JSON)
	Response getFileList(@PathParam("bucket") String bucket);

	@POST
	@Path("{bucket}")
	@Consumes(MULTIPART_FORM_DATA)
	Response uploadFile(@FormDataParam("object") InputStream objectData,
			@FormDataParam("object") FormDataContentDisposition objectDetails, @PathParam("bucket") String bucket);

	@DELETE
	@Path("{bucket}/{object}")
	Response deleteFile(@PathParam("bucket") String bucket, @PathParam("object") String object);
}
