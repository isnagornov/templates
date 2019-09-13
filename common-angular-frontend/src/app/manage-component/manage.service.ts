import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Entity} from "../model/entity";

@Injectable()
export class ManageService {
  private readonly url: string = "entity";
  private readonly httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) {
  }

  getList(): Observable<Entity[]> {
    return this.http.get<Entity[]>(this.url + '/getAll');
  }

  add(entity: Entity): Observable<Entity> {
    return this.http.post<Entity>(`${this.url}/add`, entity, this.httpOptions);
  }

  update(entity: Entity): Observable<Entity> {
    return this.http.put<Entity>(`${this.url}/update`, entity, this.httpOptions);
  }

  delete(id: number) {
    return this.http.delete(`${this.url}/${id}`);
  }

}
