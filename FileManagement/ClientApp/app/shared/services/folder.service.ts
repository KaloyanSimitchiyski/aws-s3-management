import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable()
export class FolderService {
    private baseUrl = 'http://localhost:8080';

    constructor(private http: Http) {
    }

    public folderExists(): Observable<boolean> {
        const folderName = localStorage.getItem('user_folder');

        return this.http.head(this.baseUrl + `/folders/${folderName}`)
            .pipe(map(res => {
                return res.status.toString() === '200';
            }));
    }

    public createFolder(): Observable<boolean> {
        const folderName = localStorage.getItem('user_folder');

        return this.http.post(this.baseUrl + `/folders/${folderName}`, {})
            .pipe(map(res => {
                return res.status.toString() === '201';
            }));
    }

    public deleteFolder(): Observable<boolean> {
        const folderName = localStorage.getItem('user_folder');

        return this.http.delete(this.baseUrl + `/folders/${folderName}`)
            .pipe(map(res => {
                return res.status.toString() === '200';
            }));
    }
}
