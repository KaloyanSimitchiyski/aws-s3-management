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
      .subscribe(resp => {
          const xhr = new XMLHttpRequest();
          xhr.responseType = 'blob';
          const a: any = document.createElement('a');
          a.style = 'display: none';
          document.body.appendChild(a);
          const url = window.URL.createObjectURL(resp.blob());
          a.href = url;
          a.download = fileName;
          a.click();
          window.URL.revokeObjectURL(url);

          window.open(url);
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
