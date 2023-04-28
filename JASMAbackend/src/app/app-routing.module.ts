import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {UserFormComponent} from "./user-form/user-form.component";
import {UserListComponent} from "./user-list/user-list.component";
import {RegisterComponent} from "./register/register.component";
import {LoginComponent} from "./login/login.component";
import {ProfileComponent} from "./profile/profile.component";
import {PostFormComponent} from "./post-form/post-form.component";
import {ChangePasswordComponent} from "./change-password/change-password.component";
import {FeedComponent} from "./feed/feed.component";

const routes: Routes = [
    { path: 'users', component: UserListComponent },
    { path: 'adduser', component: UserFormComponent },
    { path: 'login', component: LoginComponent},
    { path: 'register', component: RegisterComponent},
    { path: 'profile', component: ProfileComponent},
    { path: 'newpost', component: PostFormComponent},
    { path: 'feed', component: FeedComponent},
    { path: 'changepassword', component: ChangePasswordComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
