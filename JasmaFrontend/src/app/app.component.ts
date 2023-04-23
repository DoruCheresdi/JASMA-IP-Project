import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AuthenticateService} from "./services/authenticate.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'JasmaFrontend';

  constructor(private app: AuthenticateService) {
  }

  ngOnInit() {
    // authenticate:
    this.app.authenticate(undefined, undefined);
  }
}
