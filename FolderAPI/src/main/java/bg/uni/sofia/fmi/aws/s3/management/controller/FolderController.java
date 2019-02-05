package bg.uni.sofia.fmi.aws.s3.management.controller;

import bg.uni.sofia.fmi.aws.s3.management.service.IFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.*;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

@RestController
@RequestMapping("folders")
public class FolderController {

    private final IFolderService folderService;

    @Autowired
    public FolderController(IFolderService folderService) {
        this.folderService = folderService;
    }

    @RequestMapping(path = "{folder}", method = HEAD)
    public ResponseEntity isExistingFolder(@PathVariable("folder") String folder) {
        if (!folderService.isExistingFolder(folder)) {
            return notFound().build();
        }

        return ok().build();
    }

    @PutMapping("{folder}")
    public ResponseEntity createFolder(@PathVariable("folder") String folder) {
        if (folderService.isExistingFolder(folder)) {
            return notFound().build();
        }

        folderService.createFolder(folder);
        return status(CREATED).build();
    }

    @DeleteMapping("{folder}")
    public ResponseEntity deleteFolder(@PathVariable("folder") String folder) {
        if (folderService.isExistingFolder(folder)) {
            folderService.deleteEmptyFolder(folder);
        }
        return ok().build();
    }
}
