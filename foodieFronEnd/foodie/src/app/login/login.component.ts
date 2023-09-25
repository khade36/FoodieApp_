import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TokenserviceService } from '../token/tokenservice.service';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { timeout } from 'rxjs';
import { User } from '../signup/model';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent {
user:any={};
token:any
cart:any;
qty:any;
  constructor(private route:Router,private service:TokenserviceService){}

  invalid:any;
  
  public login(myform:{emailId:String,password:String,loginas:String}){
    this.service.generateToken(myform.emailId,myform.password,myform.loginas);
    // this.invalid=this.service.invalidCred;
   this.token=this.service.token;
   console.log("++**++"+this.token);
     //************************** */
    //Sushant Code
    
     this.service.getRole(myform.emailId,myform.password).subscribe((data)=>{
       this.user=(data);
       this.user=JSON.parse(this.user);
       localStorage.setItem("UserName",this.user.firstName);
       console.log("*******"+this.user);
this.token=localStorage.getItem("token");
this.service.getCart(this.token).subscribe((cart)=>{
  this.cart=cart;
  this.cart=JSON.parse(this.cart);
  this.qty=0;
  if(this.cart.item==null)
  {
    localStorage.setItem("cartSize","0");

    console.log("$$$$$%%DFGHGV");
  }
  else
  {
    if(this.cart.item.length==0)
    {
      localStorage.setItem("cartSize","0");

    console.log("$$$$$%%DFGHGV11111");
    }
    else
    {
      console.log("$$$$$%%DFGHGV222");
      for(let i=0;i<this.cart.item.length;i++)
      {
        this.qty=this.qty+parseInt(this.cart.item[i].itemQty);
      }
      localStorage.setItem("cartSize",this.qty);
    }
    }
  
});


       this.invalid=this.service.invalidCred;
      setTimeout(()=>{
        this.invalid="";
        },3000)
      if(this.user.role=="User")
      {
        localStorage.setItem("User",this.user.role);
        
        this.route.navigate(["userview"]);
        
      }
      
      else if(this.user.role=="Owner")
      {
        localStorage.setItem("User",this.user.role);
        this.route.navigate(["restaurant"]);
      }
      else
      {
        localStorage.setItem("User","admin");
        this.route.navigate(["admin"]);
      }
      console.log("*******"+this.invalid);
     });
     setTimeout(()=>{
      this.invalid="invalid credentials";
    },100);

   //************************************ */
    // this.route.navigate(['restaurant']);
    // console.log(myform)
  }
  
    navigate(){
      //console.log("redirect to signup");
     this.route.navigate(['/signup']);
    }
  }
  