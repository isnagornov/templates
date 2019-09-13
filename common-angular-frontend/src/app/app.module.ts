import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {RouterModule, Routes} from '@angular/router';

import {AppComponent} from './app.component';
import {ManageComponent} from "./manage-component/manage-component";
import {ManageService} from "./manage-component/manage.service";

const appRoutes: Routes = [
  {path: 'manage', component: ManageComponent},
  {path: '**', redirectTo: '/manage'}
];

@NgModule({
  declarations: [
    AppComponent,
    ManageComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes, {useHash: true})
  ],
  providers: [
    ManageService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}


