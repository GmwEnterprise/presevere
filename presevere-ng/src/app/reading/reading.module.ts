import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReadingRoutingModule } from './reading-routing.module';
import { ReadingComponent } from './reading.component';
import { ArchivesComponent } from './components/archives/archives.component';
import { ContentComponent } from './components/content/content.component';
import { MaterialRepositoryModule } from '../material-repository.module';
import { NavigationBarComponent } from './components/navigation-bar/navigation-bar.component';
import { CatalogComponent } from './components/catalog/catalog.component';
import { ResultsComponent } from './components/results/results.component';


@NgModule({
  declarations: [
    ReadingComponent,
    ArchivesComponent,
    ContentComponent,
    NavigationBarComponent,
    CatalogComponent,
    ResultsComponent
  ],
  imports: [
    MaterialRepositoryModule,
    CommonModule,
    ReadingRoutingModule
  ]
})
export class ReadingModule { }
