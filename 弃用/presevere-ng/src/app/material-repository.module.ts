import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';

const modules = [
  MatButtonModule,
  MatCardModule,
  MatListModule
];

@NgModule({
  imports: modules,
  exports: modules
})
export class MaterialRepositoryModule { }
