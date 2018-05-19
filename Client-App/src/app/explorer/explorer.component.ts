import { Component, OnInit } from '@angular/core';
import { FILELIST } from '../mock';
import { Filetype } from '../filetest';

@Component({
  selector: 'app-explorer',
  templateUrl: './explorer.component.html',
  styleUrls: ['./explorer.component.css']
})
export class ExplorerComponent implements OnInit {
	listeFichier=FILELIST;
	selectedFiletype: Filetype;
  constructor() { }

  ngOnInit() {
  }
  onSelect(ft: Filetype): void {
  	this.selectedFiletype=ft;
  }
  onLeave(){
  	setTimeout(()=>{
    
  	this.selectedFiletype=undefined;
 		},5000);
  	
  }
}
