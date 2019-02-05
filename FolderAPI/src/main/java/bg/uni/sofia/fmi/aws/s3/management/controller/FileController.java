package bg.uni.sofia.fmi.aws.s3.management.controller;

import bg.uni.sofia.fmi.aws.s3.management.service.IFileService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static java.util.Collections.emptyMap;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
public class FileController {

    private final IFileService fileService;

    @Autowired
    public FileController(IFileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value = "folders/{folder}/files/{file}", produces = APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity download(@PathVariable("folder") String folder, @PathVariable("file") String file,
                                   HttpServletResponse response) {
        if (!fileService.isExistingFile(folder, file)) {
            return notFound().build();
        }

        try {
            InputStream fileContent = fileService.getFileContent(folder, file);
            IOUtils.copy(fileContent, response.getOutputStream());
            response.flushBuffer();
            return ok().build();
        } catch (IOException e) {
            return status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "folders/{folder}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getFileList(@PathVariable("folder") String folder) {
        return ok(fileService.getAvailableFileNames(folder));
    }

    @PostMapping("folders/{folder}")
    public ResponseEntity uploadFile(@PathVariable("folder") String folder, @RequestParam("file") MultipartFile file) {
        try {
            fileService.uploadFile(folder, file.getOriginalFilename(), file.getInputStream(), emptyMap());
            return status(CREATED).build();
        } catch (IOException e) {
            return status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("folders/{folder}/files/{file}")
    public ResponseEntity deleteFile(@PathVariable("folder") String folder, @PathVariable("file") String file) {
        if (fileService.isExistingFile(folder, file)) {
            fileService.deleteFile(folder, file);
        }

        return ok().build();
    }
}
