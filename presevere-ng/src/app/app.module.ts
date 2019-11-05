// bootstrap支持
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainComponent } from './pages/main/main.component';
import { PageNotFountComponent } from './pages/page-not-fount/page-not-fount.component';
import { AngularCliPageComponent } from './pages/angular-cli-page/angular-cli-page.component';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    PageNotFountComponent,
    AngularCliPageComponent,
  ],
  imports: [
    NgbModule,
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
