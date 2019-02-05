import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable()
export class FolderService {
    private baseUrl = 'http://ec2-18-197-178-192.eu-central-1.compute.amazonaws.com:8080/aws-s3-management/services';

    constructor(private http: Http) {
    }

    public folderExists(): Observable<boolean> {
        const folderName = localStorage.getItem('user_folder');

        return this.http.head(this.baseUrl + `/folders/${folderName}`)
            .pipe(map(res => {
                if (res.status.toString() === '200') {
                    return true;
                } else {
                    return false;
                }
            }));
    }

    public createFolder(): Observable<boolean> {
        const folderName = localStorage.getItem('user_folder');

        return this.http.post(this.baseUrl + `/folders/${folderName}`, {})
            .pipe(map(res => {
                if (res.status.toString() === '201') {
                    return true;
                } else {
                    return false;
                }
            }));
    }

    public deleteFolder(): Observable<boolean> {
        const folderName = localStorage.getItem('user_folder');

        return this.http.delete(this.baseUrl + `/folders/${folderName}`)
            .pipe(map(res => {
                if (res.status.toString() === '200') {
                    return true;
                } else {
                    return false;
                }
            }));
    }
}
