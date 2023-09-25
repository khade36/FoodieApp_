import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenserviceService } from '../token/tokenservice.service';

@Component({
  selector: 'app-owner-orders',
  templateUrl: './owner-orders.component.html',
  styleUrls: ['./owner-orders.component.css']
})
export class OwnerOrdersComponent implements OnInit{
 users:any;
  constructor(private route:Router,private service:TokenserviceService){}
  ngOnInit(): void {
     let token=localStorage.getItem('token');
    this.service.getOwnerOrders(token).subscribe((users)=>{
    this.users=users;
    this.users=JSON.parse(this.users);
    });
  }

  approveOrder(emailId:any,orderId:any)
  {
    let token=localStorage.getItem('token');
    setTimeout(() => {
    this.service.approveOrder(token,emailId,orderId).subscribe(()=>{
    });
  },0);
    setTimeout(() => {
      this.service.getOwnerOrders(token).subscribe((users)=>{
        this.users=users;
        this.users=JSON.parse(this.users);
        });
      },1000);

  }
  
}
