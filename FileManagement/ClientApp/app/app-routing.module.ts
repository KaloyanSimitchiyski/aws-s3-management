import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';

const routes: Routes = [
    {
        path: '',
        component: AppComponent
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
