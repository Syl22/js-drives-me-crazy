import {Component, NgModule, OnInit} from '@angular/core';
import {MyOwnCustomMaterialModule } from '../MyOwnCustomMaterialModule';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})

export class AuthComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
