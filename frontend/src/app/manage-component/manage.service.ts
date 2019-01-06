import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Entity} from "../model/entity";

@Injectable()
export class ManageService {
  private url: string = "entity";

  constructor(private http: HttpClient) {
  }

  getList(): Observable<Entity[]> {
    return this.http.get<Entity[]>(this.url + '/getAll');
  }

}
