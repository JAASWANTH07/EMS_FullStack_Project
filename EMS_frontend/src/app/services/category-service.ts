import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import Category from '../models/category.model';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  baseUrl: string = 'http://localhost:8080/api/ems/categories';

  constructor(private httpClient:HttpClient) {};

  getAllCategories(): Observable<Category[]> {
    return this.httpClient.get<Category[]>(this.baseUrl);
  }

  addNewCategory(newCategory:Category): Observable<Category> {
    return this.httpClient.post<Category>(`${this.baseUrl}/add`,newCategory);
  }
}
