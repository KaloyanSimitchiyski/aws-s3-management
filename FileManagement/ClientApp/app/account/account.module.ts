import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';

import { AccountRoutingModule } from './account-routing.module';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    SharedModule,
    AccountRoutingModule
  ],
  declarations: [
    RegisterComponent,
    LoginComponent
  ]
})
export class AccountModule { }
