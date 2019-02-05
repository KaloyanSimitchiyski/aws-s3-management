package bg.uni.sofia.fmi.aws.s3.management.service;

class Utils {

    static final String SUFFIX = "/";

    static String toFolder(String keyName) {
        return keyName + SUFFIX;
    }

    static String toAbsolutePath(String folder, String file) {
        return toFolder(folder) + file;
    }
}
