import { Component } from '@angular/core';
import { FolderService } from '../shared/services/folder.service';

@Component({
  selector: 'app-folder',
  templateUrl: './folder.component.html'
})
export class FolderComponent {
  public message: string;
  public errors: string;

  constructor(private folderService: FolderService) {
  }

  public searchFolder() {
    this.errors = '';

    this.folderService.folderExists()
      .subscribe(result => {
        if (result) {
          this.message = 'The folder already exists!';
        }
      }, errors => this.errors = errors);
  }

  public createFolder() {
    this.errors = '';

    this.folderService.createFolder()
      .subscribe(result => {
        if (result) {
          this.message = 'The folder was created successfully!';
        }
      }, errors => this.errors = errors);
  }

  public deleteFolder() {
    this.errors = '';

    this.folderService.deleteFolder()
      .subscribe(result => {
        if (result) {
          this.message = 'The folder was deleted successfully!';
        }
      }, errors => this.errors = errors);
  }
}
