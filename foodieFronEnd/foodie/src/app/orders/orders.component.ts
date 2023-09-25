import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenserviceService } from '../token/tokenservice.service';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {
  orders:any = [];
  token:any;
  flag:any=false;
  constructor(private route:Router,private service:TokenserviceService,public snackBar: MatSnackBar){}
  ngOnInit(): void {
    this.token=localStorage.getItem('token');
    this.service.getOrders(this.token).subscribe((data)=>{
      this.orders=data;
      this.orders=JSON.parse(this.orders);
      
      console.log(this.orders);
    });
  }
 
get()
{return this.flag}

  deleteOrder(orderId: any) {
    this.token=localStorage.getItem('token');
    // Handle delete order logic here
    this.flag=true;
    this.service.cancelOrder(this.token,orderId).subscribe((data)=>
    {
     this.flag=false;   
     this.snackBar.open("Order deleted successfully", '', {
      duration: 2000,
    });
      // alert("Order deleted successfully"); alert changed
  
    this.service.getOrders(this.token).subscribe((data)=>{
      this.orders=data;
      this.orders=JSON.parse(this.orders);
    console.log(this.orders)});
  
  });
    
  }
}
