import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpModule } from '@angular/http';

import { NavigationComponent } from './navigation/navigation.component';

import { UserService } from './services/user.service';
import { FolderService } from './services/folder.service';
import { FileService } from './services/file.service';

@NgModule({
  imports: [ CommonModule, HttpModule ],
  declarations: [ NavigationComponent ],
  providers: [ UserService, FolderService, FileService ],
  exports : [ NavigationComponent ]
})
export class SharedModule { }
