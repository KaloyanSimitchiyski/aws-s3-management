import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-file',
  templateUrl: './file.component.html'
})
export class FileComponent implements OnInit {
  public files: string[] = [];
  public fileToUpload: File = null;

  ngOnInit() {
  }

  public handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }

  public downloadFile(fileName: string) {
  }

  public UploadFile() {
  }

  public deleteFile(fileName: string) {
  }
}
