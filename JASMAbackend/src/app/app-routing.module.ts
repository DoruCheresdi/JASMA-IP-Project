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
import {IsAuthenticatedGuardGuard} from "./is-authenticated-guard.guard";
import {UserSearchComponent} from "./user-search/user-search.component";
import { NotificationsComponent } from './notifications/notifications.component';
import {OtherUserProfileComponent} from "./other-user-profile/other-user-profile.component";
import {MessagesComponent} from "./messages/messages.component";
import {FriendListComponent} from "./friend-list/friend-list.component";
import {PrivateMessageComponent} from "./private-message/private-message.component";

const routes: Routes = [
    { path: 'users', component: UserListComponent },
    { path: 'adduser', component: UserFormComponent },
    { path: 'login', component: LoginComponent},
    { path: 'register', component: RegisterComponent},
    { path: 'profile', component: ProfileComponent, canActivate: [IsAuthenticatedGuardGuard]},
    { path: 'newpost', component: PostFormComponent, canActivate: [IsAuthenticatedGuardGuard]},
    { path: 'feed', component: FeedComponent, canActivate: [IsAuthenticatedGuardGuard]},
    { path: 'changepassword', component: ChangePasswordComponent},
    { path: 'usersearch', component: UserSearchComponent, canActivate: [IsAuthenticatedGuardGuard]},
    {path:'notifications', component: NotificationsComponent, canActivate: [IsAuthenticatedGuardGuard]},
    {path:'messages', component: MessagesComponent, canActivate: [IsAuthenticatedGuardGuard]},
    { path: 'userprofile', component: OtherUserProfileComponent, canActivate: [IsAuthenticatedGuardGuard]},
    {path:'friendlist', component: FriendListComponent, canActivate: [IsAuthenticatedGuardGuard]},
    {path:'privatemessage', component: PrivateMessageComponent, canActivate: [IsAuthenticatedGuardGuard]},

    // redirects to feed for unknown link:
    { path: '**', component: FeedComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
