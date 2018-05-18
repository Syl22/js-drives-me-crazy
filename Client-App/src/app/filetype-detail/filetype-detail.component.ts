import { Component, OnInit, Input } from '@angular/core';
import { Filetype } from '../filetest';


@Component({
  selector: 'app-filetype-detail',
  templateUrl: './filetype-detail.component.html',
  styleUrls: ['./filetype-detail.component.css']
})
export class FiletypeDetailComponent implements OnInit {

@Input() selectedFiletype: Filetype;
  
  constructor() { }
  

  ngOnInit() {
  }

}
