import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { UserService } from '../../shared/services/user.service';
import { UserLogin } from '../../shared/models/user.login.interface';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  public errors: string;

  constructor(private userService: UserService, private router: Router) {
  }

  public login(formValue: any) {
    this.errors = '';

    const user: UserLogin = {
      email: formValue.form.value.email,
      password: formValue.form.value.password
    };

    this.userService.login(user)
      .subscribe(result => {
        if (result) {
          this.router.navigate(['/folder']);
        }
      }, errors => this.errors = errors);
  }
}
