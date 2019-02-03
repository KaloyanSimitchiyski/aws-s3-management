import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { UserRegistration } from '../../shared/models/user.registration.interface';
import { UserService } from '../../shared/services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  public errors: string;

  constructor(private userService: UserService, private router: Router) {
  }

  public registerUser(formValue: any) {
    this.errors = '';

    const user: UserRegistration = {
      firstName: formValue.form.value.firstName,
      lastName: formValue.form.value.lastName,
      email: formValue.form.value.email,
      password: formValue.form.value.password
    };

    this.userService.register(user)
      .subscribe(result => {
        if (result) {
          this.router.navigate(['/login']);
        }
      }, errors => this.errors = errors);
  }
}
