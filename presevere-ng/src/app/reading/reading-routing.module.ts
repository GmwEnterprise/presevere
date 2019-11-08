import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ReadingComponent } from './reading.component';
import { CatalogComponent } from './components/catalog/catalog.component';
import { ArchivesComponent } from './components/archives/archives.component';
import { ResultsComponent } from './components/results/results.component';
import { ContentComponent } from './components/content/content.component';

const routes: Routes = [
  {
    path: 'home',
    component: ReadingComponent,
    children: [
      { path: '', component: CatalogComponent },
      { path: 'archives', component: ArchivesComponent },
      // 不同的路由形式决定ResultsComponent的不同显示方式
      { path: ':year', component: ResultsComponent },
      { path: ':year/:month', component: ResultsComponent },
      { path: ':year/:month/:id', component: ContentComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReadingRoutingModule { }
