import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenserviceService } from '../token/tokenservice.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit{
  token :any;
  users:any=[];
    
    constructor(private route:Router,private service:TokenserviceService){}
    ngOnInit(): void {
      this.token=localStorage.getItem('token');
      this.service.getallUsers(this.token).subscribe((data)=>{ 
   this.users=(data);
  this.users=JSON.parse(this.users);
      
      console.log(this.users);
     });
    }
  
    restaurantReq()
    {
      this.route.navigate(['restoReq']);
    }
      restaurantList(){this.route.navigate(['restaurants']);}}
