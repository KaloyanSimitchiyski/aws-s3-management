import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

import { UserService } from '../services/user.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html'
})
export class NavigationComponent implements OnInit, OnDestroy {
  private subscription: Subscription;

  public status: boolean;

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.subscription = this.userService.authNavStatus$.subscribe(status => this.status = status);
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  public logout(): void {
    this.userService.logout();
  }
}
