import { Component, OnInit } from '@angular/core';
import { TokenserviceService } from '../token/tokenservice.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-favourite-resto',
  templateUrl: './favourite-resto.component.html',
  styleUrls: ['./favourite-resto.component.css']
})
export class FavouriteRestoComponent implements OnInit {
  token: any;
  fav: any = [];
  empty: any;

  constructor(private service: TokenserviceService, private route: Router) { }
  ngOnInit(): void {
    this.token=localStorage.getItem('token');
    this.service.getFavResto(this.token).subscribe((data) => {
      this.fav = (data);

      this.fav = JSON.parse(this.fav);
      console.log(this.fav);
    });

  }


}