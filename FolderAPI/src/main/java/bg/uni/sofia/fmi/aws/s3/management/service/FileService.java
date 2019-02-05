package bg.uni.sofia.fmi.aws.s3.management.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static bg.uni.sofia.fmi.aws.s3.management.service.Utils.toAbsolutePath;
import static bg.uni.sofia.fmi.aws.s3.management.service.Utils.toFolder;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.isEmpty;

@Service
public class FileService implements IFileService {

    private final AmazonS3 s3Client;
    private final String rootBucket;

    @Autowired
    public FileService(AmazonS3 s3Client, @Value("${aws.s3.root.bucket}") String rootBucket) {
        this.s3Client = s3Client;
        this.rootBucket = rootBucket;
    }

    @Override
    public boolean isExistingFile(String folder, String file) {
        return s3Client.doesObjectExist(rootBucket, toAbsolutePath(folder, file));
    }

    @Override
    public InputStream getFileContent(String folder, String file) {
        String absolutePath = toAbsolutePath(folder, file);
        return s3Client.getObject(rootBucket, absolutePath) //
                .getObjectContent();
    }

    @Override
    public List<String> getAvailableFileNames(String folder) {
        ListObjectsV2Request request = new ListObjectsV2Request() //
                .withBucketName(rootBucket) //
                .withPrefix(toFolder(folder));

        ListObjectsV2Result result = s3Client.listObjectsV2(request);
        return result.getObjectSummaries().stream() //
                .map(S3ObjectSummary::getKey) //
                .map(file -> file.replace(toFolder(folder), "")) //
                .filter(file -> !isEmpty(file)) //
                .collect(toList());
    }

    @Override
    public void deleteFile(String folder, String file) {
        String absolutePath = toAbsolutePath(folder, file);
        s3Client.deleteObject(rootBucket, absolutePath);
    }

    @Override
    public void uploadFile(String folder, String file, InputStream fileContent, Map<String, String> fileMetadata) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setUserMetadata(fileMetadata);
        s3Client.putObject(rootBucket, toAbsolutePath(folder, file), fileContent, metadata);
    }
}
