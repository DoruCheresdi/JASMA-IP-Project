import {Injectable, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserListComponent } from './user-list/user-list.component';
import { UserFormComponent } from './user-form/user-form.component';
import {FormsModule, ReactiveFormsModule } from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import { RegisterComponent } from './register/register.component';
import { NavbarComponent } from './navbar/navbar.component';
import { LoginComponent } from './login/login.component';
import {AuthenticateService} from "./services/authenticate.service";
import { ProfileComponent } from './profile/profile.component';
import { PostFormComponent } from './post-form/post-form.component';
import { PostListComponent } from './post-list/post-list.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { FeedComponent } from './feed/feed.component';
import { PostCardComponent } from './post-card/post-card.component';
import { UserSearchComponent } from './user-search/user-search.component';
import { NotificationsComponent } from './notifications/notifications.component';
import { OtherUserProfileComponent } from './other-user-profile/other-user-profile.component';
import {NgOptimizedImage} from "@angular/common";
import {MessagesComponent} from "./messages/messages.component";

@Injectable()
export class WithCredentialsInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const xhr = req.clone({
      withCredentials: true
    });
    return next.handle(xhr);
  }
}

@Injectable()
export class XhrInterceptor implements HttpInterceptor {

    intercept(req: HttpRequest<any>, next: HttpHandler) {
        const xhr = req.clone({
            headers: req.headers.set('X-Requested-With', 'XMLHttpRequest')
        });
        return next.handle(xhr);
    }
}

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    UserFormComponent,
    RegisterComponent,
    NavbarComponent,
    LoginComponent,
    ProfileComponent,
    PostFormComponent,
    PostListComponent,
    ChangePasswordComponent,
    FeedComponent,
    PostCardComponent,
    UserSearchComponent,
    NotificationsComponent,
    OtherUserProfileComponent,
    MessagesComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        NgOptimizedImage
    ],
    // exports: [
    //     NotificationsComponent
    // ],
  providers: [AuthenticateService, { provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
