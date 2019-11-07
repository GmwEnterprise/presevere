import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material';
import { MatCardModule } from '@angular/material/card';

const modules = [
  MatButtonModule,
  MatCardModule
];

@NgModule({
  imports: modules,
  exports: modules
})
export class MaterialRepositoryModule { }
