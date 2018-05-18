import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FiletypeDetailComponent } from './filetype-detail.component';

describe('FiletypeDetailComponent', () => {
  let component: FiletypeDetailComponent;
  let fixture: ComponentFixture<FiletypeDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FiletypeDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FiletypeDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
