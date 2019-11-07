import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ReadingComponent } from './reading.component';
import { CatalogComponent } from './components/catalog/catalog.component';
import { ArchivesComponent } from './components/archives/archives.component';
// import { ContentComponent } from './components/content/content.component';

const routes: Routes = [
  {
    path: 'home',
    component: ReadingComponent,
    children: [
      { path: '', component: CatalogComponent },
      { path: 'archives', component: ArchivesComponent },
      // { path: '/:date/:title', component: ContentComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReadingRoutingModule { }
