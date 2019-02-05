import { Injectable } from '@angular/core';
import { Http, Headers, ResponseContentType } from '@angular/http';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable()
export class FileService {
    private baseUrl = 'http://localhost:8080';

    constructor(private http: Http) {
    }

    public getAvailableFiles(): Observable<string[]> {
        const folderName = localStorage.getItem('user_folder');

        return this.http.get(this.baseUrl + `/folders/${folderName}/files/`)
            .pipe(map(res => {
                return res.json();
            }));
    }

    public downloadFile(fileName: string): Observable<Blob> {
        const folderName = localStorage.getItem('user_folder');
        const headers = new Headers();
        headers.append('Content-Type', 'application/x-www-form-urlencoded');

        return this.http.get(this.baseUrl + `/folders/${folderName}/files/${fileName}`, {
            responseType: ResponseContentType.Blob,
            headers: headers
          })
          .pipe(map(res => {
                return res.blob();
            }));
    }

    public uploadFile(file: File): Observable<boolean> {
        const folderName = localStorage.getItem('user_folder');

        const formData: FormData = new FormData();
        formData.append('file', file, file.name);

        return this.http.post(this.baseUrl + `/folders/${folderName}/files/`, formData)
            .pipe(map(res => {
                return res.status.toString() === '201';
            }));
    }

    public deleteFile(fileName: string): Observable<boolean> {
        const folderName = localStorage.getItem('user_folder');

        return this.http.delete(this.baseUrl + `/folders/${folderName}/files/${fileName}`)
            .pipe(map(res => {
                return res.status.toString() === '200';
            }));
    }
}
