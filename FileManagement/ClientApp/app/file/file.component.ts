import { Component, OnInit } from '@angular/core';
import { FileService } from '../shared/services/file.service';

@Component({
  selector: 'app-file',
  templateUrl: './file.component.html'
})
export class FileComponent implements OnInit {
  public errors: string;
  public files: string[] = [];
  public fileToUpload: File = null;

  constructor(private fileService: FileService) {
  }

  ngOnInit() {
    this.errors = '';

    this.fileService.getAvailableFiles()
      .subscribe(files => {
        if (files) {
          this.files = files;
        }
      }, errors => this.errors = errors);
  }

  public downloadFile(fileName: string) {
    this.errors = '';

    this.fileService.downloadFile(fileName)
      .subscribe(blob => {
        if (blob) {
          const element = document.createElement('a');

          element.href = URL.createObjectURL(blob);
          element.download = fileName;
          document.body.appendChild(element);

          element.click();
        }
      }, errors => this.errors = errors);
  }

  public uploadFile(files: FileList) {
    this.errors = '';
    this.fileToUpload = files.item(0);
    const fileName = this.fileToUpload.name;

    this.fileService.uploadFile(this.fileToUpload)
      .subscribe(result => {
        if (result) {
          this.fileToUpload = null;
          this.files.push(fileName);
        }
      }, errors => this.errors = errors);
  }

  public deleteFile(fileName: string) {
    this.errors = '';

    this.fileService.deleteFile(fileName)
      .subscribe(result => {
        if (result) {
          const fileNameIndex = this.files.indexOf(fileName);
          if (fileNameIndex > -1) {
            this.files.splice(fileNameIndex, 1);
          }
        }
      }, errors => this.errors = errors);
  }
}
