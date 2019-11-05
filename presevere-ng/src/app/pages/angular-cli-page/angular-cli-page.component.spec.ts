import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AngularCliPageComponent } from './angular-cli-page.component';

describe('AngularCliPageComponent', () => {
  let component: AngularCliPageComponent;
  let fixture: ComponentFixture<AngularCliPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AngularCliPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AngularCliPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
