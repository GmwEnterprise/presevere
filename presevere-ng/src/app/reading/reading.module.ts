import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReadingRoutingModule } from './reading-routing.module';
import { ReadingComponent } from './reading.component';
import { ArchivesComponent } from './components/archives/archives.component';
import { ContentComponent } from './components/content/content.component';
import { MaterialRepositoryModule } from '../material-repository.module';


@NgModule({
  declarations: [ReadingComponent, ArchivesComponent, ContentComponent],
  imports: [
    MaterialRepositoryModule,
    CommonModule,
    ReadingRoutingModule
  ]
})
export class ReadingModule { }
