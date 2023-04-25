import { Component } from '@angular/core';
import {AuthenticateService} from "../services/authenticate.service";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {catchError, throwError} from "rxjs";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  user = {
    email : "",
    password : "",
    name : ""
  };

  userMessage = "Hello! Register now!";

  constructor(private app: AuthenticateService, private http: HttpClient, private router: Router) {
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      this.userMessage = 'An error occurred:' + error.error;
      console.error('An error occurred:', error.error);
    } else {
      this.userMessage = `Backend returned code ${error.status}, body was: ` + error.error;
      console.error(
        `Backend returned code ${error.status}, body was: `, error.error);
    }
    // Return an observable with a user-facing error message.
    return throwError(() => new Error('Something bad happened; please try again later.'));
  }

  register() {
    if (this.app.authenticated) {
      // logout first:
      // this.http.post('logout', {}).subscribe();
      // this.app.authenticated = false;
      this.userMessage = "Esti deja autentificat";
      return;
    }

    this.http.post("devapi/register", this.user).pipe(
      catchError(this.handleError.bind(this))
    )
      .subscribe((response) => {
        this.app.authenticate({email: this.user.email, password: this.user.password}, () => {
          this.router.navigateByUrl("/home");
        });
      });
  }
}
