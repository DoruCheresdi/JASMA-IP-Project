import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import { Observable } from 'rxjs';
import {HttpClient} from "@angular/common/http";
import {AuthenticateService} from "./services/authenticate.service";

@Injectable({
  providedIn: 'root'
})
export class IsAuthenticatedGuardGuard implements CanActivate {

    constructor(private auth: AuthenticateService, private router: Router) {
    }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        console.log(this.auth.isAuthenticated());
          if (!this.auth.isAuthenticated()) this.auth.authenticate(undefined, (isAuthenticated) => {
              if (isAuthenticated) {
                  // redirect here cuz the app didn't know before that
                  // the user is authenticated on the server:
                  // TODO: I don't know if this url[0] is right, read up on it:
                  // https://angular.io/api/router/ActivatedRouteSnapshot
                  // https://angular.io/api/router/UrlSegment
                  this.router.navigateByUrl(route.url[0].path);
              } else {
                  // TODO: do nothing:
              }
          });
        return this.auth.isAuthenticated();
  }

}
