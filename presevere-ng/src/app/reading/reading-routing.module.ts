import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ReadingComponent } from './reading.component';


const routes: Routes = [
  { path: 'home', component: ReadingComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReadingRoutingModule { }
