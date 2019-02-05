package bg.uni.sofia.fmi.aws.s3.management.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static bg.uni.sofia.fmi.aws.s3.management.service.Utils.toFolder;

@Service
public class FolderService implements IFolderService {

    private static final InputStream EMPTY_STREAM = new ByteArrayInputStream(new byte[0]);
    private static final ObjectMetadata EMPTY_METADATA = new ObjectMetadata();

    static {
        EMPTY_METADATA.setContentLength(0);
    }

    private final AmazonS3 s3Client;
    private final String rootBucket;

    @Autowired
    public FolderService(AmazonS3 s3Client, @Value("${aws.s3.root.bucket}") String rootBucket) {
        this.s3Client = s3Client;
        this.rootBucket = rootBucket;
    }

    @Override
    public boolean isExistingFolder(String folder) {
        return s3Client.doesObjectExist(rootBucket, toFolder(folder));
    }

    @Override
    public void createFolder(String folder) {
        s3Client.putObject(rootBucket, toFolder(folder), EMPTY_STREAM, EMPTY_METADATA);
    }

    @Override
    public void deleteEmptyFolder(String folder) {
        s3Client.deleteObject(rootBucket, toFolder(folder));
    }
}
