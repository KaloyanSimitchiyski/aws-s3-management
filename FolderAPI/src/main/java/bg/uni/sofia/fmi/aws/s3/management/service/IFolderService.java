package bg.uni.sofia.fmi.aws.s3.management.service;

public interface IFolderService {

    boolean isExistingFolder(String folder);

    void createFolder(String folder);

    void deleteEmptyFolder(String folder);
}
