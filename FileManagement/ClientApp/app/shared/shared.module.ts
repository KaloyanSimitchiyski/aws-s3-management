import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NavigationComponent } from './navigation/navigation.component';

import { UserService } from './services/user.service';

@NgModule({
  imports: [ CommonModule ],
  declarations: [ NavigationComponent ],
  providers: [ UserService ],
  exports : [ NavigationComponent ]
})
export class SharedModule { }
