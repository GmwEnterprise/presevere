import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PageNotFountComponent } from './components/page-not-fount/page-not-fount.component';
import { AngularCliPageComponent } from './components/angular-cli-page/angular-cli-page.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialRepositoryModule } from './material-repository.module';
import { ReadingModule } from './reading/reading.module';

@NgModule({
  imports: [
    BrowserModule,
    // 支持动画
    BrowserAnimationsModule,
    // material组件依赖
    MaterialRepositoryModule,
    ReadingModule,
    // app-routing需要放在最后一个，关键要放在含有路由模块的子模块之后
    AppRoutingModule,
  ],
  declarations: [
    AppComponent,
    PageNotFountComponent,
    AngularCliPageComponent,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
