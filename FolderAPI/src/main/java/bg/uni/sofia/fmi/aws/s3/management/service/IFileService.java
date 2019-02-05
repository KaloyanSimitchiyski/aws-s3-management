package bg.uni.sofia.fmi.aws.s3.management.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface IFileService {

    boolean isExistingFile(String folder, String file);

    InputStream getFileContent(String folder, String file);

    List<String> getAvailableFileNames(String folder);

    void deleteFile(String folder, String file);

    void uploadFile(String folder, String file, InputStream fileContent, Map<String, String> fileMetadata);
}
