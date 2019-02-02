import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions } from '@angular/http';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { UserRegistration } from '../models/user.registration.interface';

@Injectable()
export class UserService {
    private baseUrl = 'http://localhost:5000/api';

    constructor(private http: Http) {
    }

    public register(userRegistration: UserRegistration): Observable<boolean> {
        const headers = new Headers({ 'Content-Type': 'application/json' });
        const options = new RequestOptions({ headers: headers });

        return this.http.post(this.baseUrl + '/accounts', userRegistration, options)
            .pipe(map(res => true));
    }
}
