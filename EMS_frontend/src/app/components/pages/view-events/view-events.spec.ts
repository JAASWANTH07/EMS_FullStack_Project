import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewEvents } from './view-events';

describe('ViewEvents', () => {
  let component: ViewEvents;
  let fixture: ComponentFixture<ViewEvents>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewEvents]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewEvents);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
