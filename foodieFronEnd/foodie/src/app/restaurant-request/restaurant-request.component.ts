import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenserviceService } from '../token/tokenservice.service';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-restaurant-request',
  templateUrl: './restaurant-request.component.html',
  styleUrls: ['./restaurant-request.component.css']
})
export class RestaurantRequestComponent implements OnInit{
  token:any;
  restaurants:any=[];
  data:any;
  datamsg:any;
  
  constructor(private route:Router,private service:TokenserviceService,public snackBar: MatSnackBar){}
  ngOnInit(): void {
    this.token=localStorage.getItem('token');
    this.service.getallRestaurants().subscribe((data)=>{ 
 this.restaurants=(data);
 this.restaurants=JSON.parse(this.restaurants);
    
    console.log(this.restaurants);
  });
  }

  users()
  {
    this.route.navigate(['admin']);
  }
  restaurantList(){
    this.route.navigate(['restaurants']);
  }
  approve(ownerId:any,restaurantName:any)
  {
    
    this.token=localStorage.getItem('token');
    
    this.service.approveRestaurants(this.token,ownerId,restaurantName).subscribe((data)=>{ 
      console.log(data);
      this.datamsg=data;
      this.snackBar.open(this.data, '', {
        duration: 2000,
      });
      // alert(data);---------------changed
  });

  this.token=localStorage.getItem('token');
  this.service.getallRestaurants().subscribe((data)=>{ 
this.restaurants=(data);
this.restaurants=JSON.parse(this.restaurants);
  
  console.log(this.restaurants);
});

  
  }

  
}