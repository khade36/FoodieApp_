import { Component, OnInit } from '@angular/core';
import { TokenserviceService } from '../token/tokenservice.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-favourite-items',
  templateUrl: './favourite-items.component.html',
  styleUrls: ['./favourite-items.component.css']
})
export class FavouriteItemsComponent implements OnInit {
  token:any;
  fav:any=[];
  constructor(private service:TokenserviceService,private route:Router){}
  ngOnInit(): void {
    this.token=localStorage.getItem('token');
   this.service.getFavItem(this.token).subscribe((data)=>{ 
this.fav=(data);
this.fav=JSON.parse(this.fav);
   
   console.log(this.fav); 
  });
}



}