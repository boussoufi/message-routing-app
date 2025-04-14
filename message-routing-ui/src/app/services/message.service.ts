import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Message} from '../models/message.model';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  private apiUrl = `${environment.apiUrl}/messages`;

  constructor(private http: HttpClient) { }

  getMessages(page: number = 0, size: number = 20): Observable<PageResponse<Message>> {
    return this.http.get<PageResponse<Message>>(`${this.apiUrl}?page=${page}&size=${size}`);
  }

  getMessage(id: number): Observable<Message> {
    return this.http.get<Message>(`${this.apiUrl}/${id}`);
  }
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}
