import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { FolderComponent } from './folder/folder.component';
import { FileComponent } from './file/file.component';

const routes: Routes = [
    {
        path: '',
        component: AppComponent
    },

    {
        path: 'folder',
        component: FolderComponent
    },

    {
        path: 'file',
        component: FileComponent
    },

    {
        path: 'account',
        loadChildren: './account/account.module#AccountModule'
    }
];

@NgModule({
    imports: [ RouterModule.forRoot(routes) ],
    exports: [ RouterModule ]
  })
export class AppRoutingModule { }
