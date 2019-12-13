import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PageNotFountComponent } from './components/page-not-fount/page-not-fount.component';
import { AngularCliPageComponent } from './components/angular-cli-page/angular-cli-page.component';


const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'ng-help', component: AngularCliPageComponent },
  { path: '**', component: PageNotFountComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    // enableTracing: true // 调试路由时使用
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
