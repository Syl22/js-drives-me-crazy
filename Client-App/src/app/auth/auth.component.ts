import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})

export class AuthComponent implements OnInit {
  connected = false;
  checkedG = false;
  checkedO = false;
  valInit = '';

  constructor() {
  }

  ngOnInit() {
  }
  public reinit() {
    this.valInit = '';
  }
}
