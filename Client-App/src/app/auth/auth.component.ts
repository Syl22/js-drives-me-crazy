import {Component, Injectable, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MatDialog} from '@angular/material';

export interface Token {
  tokenString: string;
}

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})

@Injectable()
export class AuthComponent implements OnInit {
  googleTokenUrl = 'http://localhost:8080/projet/redirect/google';
  googleToken: Token;

  constructor(private http: HttpClient) {
  }

  ngOnInit() {
  }

  public checkConnexion(): boolean {
    this.tokenHandler();
    if(this.googleToken.tokenString != undefined){
      return true;
    }
    else {
      return false;
    }
  }

  public getGToken() {
    return this.http.get<Token>(this.googleTokenUrl);
  }

  public tokenHandler() {
    this.getGToken()
      .subscribe((data: Token) => this.googleToken = {
        tokenString: data['tokenString']
      }, error => {
        console.log(error);
        this.tokenHandler();
      });
  }
}
