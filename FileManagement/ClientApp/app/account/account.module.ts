import { NgModule } from '@angular/core';

import { AccountRoutingModule } from './account-routing.module';

import { RegisterComponent } from './register/register.component';

@NgModule({
  imports: [
    AccountRoutingModule
  ],
  declarations: [
    RegisterComponent
  ]
})
export class AccountModule { }
