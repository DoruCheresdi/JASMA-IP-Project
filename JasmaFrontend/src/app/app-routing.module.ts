import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {UserFormComponent} from "./user-form/user-form.component";
import {UserListComponent} from "./user-list/user-list.component";
import {RegisterComponent} from "./register/register.component";
import {LoginComponent} from "./login/login.component";

const routes: Routes = [
  { path: 'users', component: UserListComponent },
  { path: 'adduser', component: UserFormComponent },
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
