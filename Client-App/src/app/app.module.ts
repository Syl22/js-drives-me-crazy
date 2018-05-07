import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { NgModule } from '@angular/core';

import {MatButtonModule, MatCheckboxModule, MatDialogModule} from '@angular/material';
import {MatInputModule} from '@angular/material/input';
import {MatGridListModule} from '@angular/material/grid-list';

import { AppComponent } from './app.component';
import { AuthComponent } from './auth/auth.component';
import {FormsModule} from '@angular/forms';
import { ExplorerComponent } from './explorer/explorer.component';

import { Filetype } from './filetest';
import { FILELIST } from './mock';
import { FiletypeDetailComponent } from './filetype-detail/filetype-detail.component'


@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    ExplorerComponent,
    FiletypeDetailComponent
  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    FormsModule,
    MatButtonModule,
    MatCheckboxModule,
    MatInputModule,
    MatGridListModule,
    MatDialogModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
