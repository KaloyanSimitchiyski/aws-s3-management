import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions } from '@angular/http';

import { Observable, BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';

import { UserRegistration } from '../models/user.registration.interface';
import { UserLogin } from '../models/user.login.interface';

@Injectable()
export class UserService {
    private baseUrl = 'http://localhost:5000/api';
    private loggedIn = false;
    private authNavStatusSource = new BehaviorSubject<boolean>(false);

    public authNavStatus$ = this.authNavStatusSource.asObservable();

    constructor(private http: Http) {
        this.loggedIn = !!localStorage.getItem('auth_token');
        this.authNavStatusSource.next(this.loggedIn);
    }

    public register(userRegistration: UserRegistration): Observable<boolean> {
        const headers = new Headers({ 'Content-Type': 'application/json' });
        const options = new RequestOptions({ headers: headers });

        return this.http.post(this.baseUrl + '/accounts', userRegistration, options)
            .pipe(map(res => true));
    }

    public login(userLogin: UserLogin): Observable<boolean> {
        const headers = new Headers({ 'Content-Type': 'application/json' });
        const options = new RequestOptions({ headers: headers });

        return this.http.post(this.baseUrl + '/auth/login', userLogin, options)
            .pipe(map(res => res.json()), map(res => {
                localStorage.setItem('auth_token', res.auth_token);
                localStorage.setItem('user_folder', res.user_folder);
                this.loggedIn = true;
                this.authNavStatusSource.next(true);

                return true;
            }));
    }

    public isLoggedIn(): boolean {
        return this.loggedIn;
    }

    public logout(): void {
        localStorage.removeItem('auth_token');
        localStorage.removeItem('user_folder');
        this.loggedIn = false;
        this.authNavStatusSource.next(false);
    }
}
