import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenserviceService } from '../token/tokenservice.service';

@Component({
  selector: 'app-restaurants',
  templateUrl: './restaurants.component.html',
  styleUrls: ['./restaurants.component.css']
})
export class RestaurantsComponent implements OnInit{
  token: any;
  restaurants:any=[];
  constructor(private route:Router,private service:TokenserviceService){}
  ngOnInit(): void {
    this.token=localStorage.getItem('token');
    this.service.getallRestaurants().subscribe((data)=>{ 
 this.restaurants=(data);
 this.restaurants=JSON.parse(this.restaurants);
    
    console.log(this.restaurants);
  });
};
  users(){
    this.route.navigate(['admin']);
  }
  restaurantReq()
  {
    this.route.navigate(['restoReq']);
  }
}

