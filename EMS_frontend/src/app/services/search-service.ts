import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  private searchQuerySource = new BehaviorSubject<string>(''); // holds latest search text
  currentSearchQuery = this.searchQuerySource.asObservable();

  updateSearchQuery(query: string) {
    this.searchQuerySource.next(query);
  }
}
