import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MessagesComponent} from './pages/messages/messages.component';
import {PartnersComponent} from './pages/partners/partners.component';

const routes: Routes = [
  { path: '', redirectTo: '/messages', pathMatch: 'full' },
  { path: 'messages', component: MessagesComponent },
  { path: 'partners', component: PartnersComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
