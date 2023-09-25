import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenserviceService } from '../token/tokenservice.service';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({ 
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  restaurantName = 'Restaurant ABC';
  carts:any=[{}];
  flag:any=false;
  user:any={};
  token:any;
  order:any={};
  cart:any=false;
  subTotal:any=0;
  gstResto:any=0;
  delivery:any=0;
  cart1:any;
  
  grandTotal=0;
   i:any=0;
  constructor(private route:Router,private service:TokenserviceService,public snackBar: MatSnackBar){}
  ngOnInit(): void {
    let token=localStorage.getItem('token');
    console.log("**/*/88988"+localStorage.getItem('token'));
    //this.token="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdXNoYW50Z2Fpa3dhZDA5OTBAZ21haWwuY29tIiwiaWF0IjoxNjg5Nzc2MjAyfQ.FZPqj6uYlYxUdX8Ftr9QUzTMj_idCAUAVsqidYSfn1w";
    this.service.getCart(token).subscribe((data)=>{
      this.carts=data;
      this.carts=JSON.parse(this.carts);
      for(let item of this.carts.item) 
      {
       this.subTotal=Math.round(this.subTotal+item.itemPrice*item.itemQty);
    
      }
      if(this.subTotal==0)
      {
        this.gstResto=0;
        this.delivery=0
      }
      else{
        this.gstResto=Math.round(this.subTotal*0.18+20);
        this.delivery=40;
      }
        this.grandTotal=Math.round(this.subTotal+this.gstResto+this.delivery);
      
      this.carts.item[0].itemPrice;
      if(this.carts!=null)
      {
        this.cart=true;
      }
      console.log(this.carts);
    });

    this.service.getUser(token).subscribe((data)=>{
      this.user=data;
      this.user=JSON.parse(this.user);
      if(this.user.address.length==0)
      {
        this.snackBar.open("No Address added. Add Address first from profile.", '', {
          duration: 2000,
        });
        // alert("No Address added. Add Address first from profile.") //alert changed -----------------
        this.route.navigate(["profile"]);
      }
      
      console.log(this.user);
    });

  }
  get()
  {return this.flag}
  orderNow(ownerId:any,restaurantName:any,item:any,address:any)
  {
    this.flag=true;
    this.order=
    {
      "ownerId":ownerId,
      "restaurantName":restaurantName,
      "address":address,
      "item":item
    }
    let token=localStorage.getItem('token');
    //this.token="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdXNoYW50Z2Fpa3dhZDA5OTBAZ21haWwuY29tIiwiaWF0IjoxNjg5Nzc2MjAyfQ.FZPqj6uYlYxUdX8Ftr9QUzTMj_idCAUAVsqidYSfn1w";
    this.service.placeOrder(token,this.order).subscribe((data)=>{
    

      this.service.deleteCart(token).subscribe((data1)=>{
        this.flag=false;
        this.snackBar.open("Order placed successfully", '', {
          duration: 2000,
        });
      //  alert changed------------------
      });
      // this.service.getCart(this.token).subscribe((data)=>{
      //   this.carts=data;
      //   this.carts=JSON.parse(this.carts);
      //   console.log(this.carts);
      // });
      
        this.cart1=0;
        localStorage.setItem("cartSize",this.cart1);
      this.route.navigate(["orders"]);


    });
  }
  
  deleteItem(itemName:any,itemPrice:any,itemQty:any)
  {
    let token=localStorage.getItem('token');
    //this.token="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdXNoYW50Z2Fpa3dhZDA5OTBAZ21haWwuY29tIiwiaWF0IjoxNjg5Nzc2MjAyfQ.FZPqj6uYlYxUdX8Ftr9QUzTMj_idCAUAVsqidYSfn1w";
    this.service.deleteCartItem(token,itemName).subscribe((data)=>{
    // alert("Item deleted successfully");
    this.snackBar.open("Item deleted successfully", '', {
      duration: 2000,
    });
  //  alert changed------------------
    //////////////////////////////////////////////////////////////////
    if(localStorage.getItem("cartSize")==null)
{
  this.cart1=0;
}
else{
  this.cart1=localStorage.getItem("cartSize");
  this.cart1=parseInt(this.cart1);
}
  console.log("$$$$"+this.cart1);
  this.cart1=this.cart1-parseInt(itemQty);
  console.log("#$#$#"+this.cart1);
  localStorage.setItem("cartSize",this.cart1);
  console.log(".$$$........this.cart"+this.cart1);
    /////////////////////////////////////////////////////////////////
    this.subTotal=this.subTotal-(itemPrice*itemQty);
    if(this.subTotal==0)
    {
      this.gstResto=0;
      this.delivery=0
    }
    else{
      this.gstResto=Math.round(this.subTotal*0.18+20);
      this.delivery=40;
    }
      this.grandTotal=Math.round(this.subTotal+this.gstResto+this.delivery);
//////////////////////////////////////////////////////////////
    let token=localStorage.getItem('token');
    this.service.getCart(token).subscribe((data)=>{
      this.carts=data;
      this.carts=JSON.parse(this.carts);
      
    
      if(this.carts.item.length!=0)
      {
        this.cart=true;
      }
      else
      {this.cart=false;}
      console.log(this.carts);
    });

///////////////////////////////////////////////////////////////////////////////

    });

  }


  changeQty(itemName:any,newQty:any,emailId:any)
  {
    let token=localStorage.getItem('token');
    //this.token="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdXNoYW50Z2Fpa3dhZDA5OTBAZ21haWwuY29tIiwiaWF0IjoxNjg5Nzc2MjAyfQ.FZPqj6uYlYxUdX8Ftr9QUzTMj_idCAUAVsqidYSfn1w";
    if(newQty<1)
    {
      newQty=1;
    }
    if(newQty<1)
    {
      this.cart=false;
    }
    if(newQty>5)
    {
      this.snackBar.open("Can not order more than 5 Qty of a item in one order", '', {
        duration: 2000,
      });
    //  alert changed------------------
     // alert("Can not order more than 5 Qty of a item in one order");
      newQty=5;
    }
    else{
      this.cart=true;
    }

    this.service.changeQty(itemName,newQty,emailId).subscribe((data)=>{

      this.service.getCart(token).subscribe((data1)=>{
        this.carts=data1;
        this.carts=JSON.parse(this.carts);
        //////////////////////////////////////////////////////////////////////////
this.cart1=0;
        if(this.carts.item.length==0)
    {
      localStorage.setItem("cartSize","0");

    console.log("$$$$$%%DFGHGV11111");
    }
    else
    {
      console.log("$$$$$%%DFGHGV222");
      for(let i=0;i<this.carts.item.length;i++)
      {
        this.cart1=this.cart1+parseInt(this.carts.item[i].itemQty);
      }
      localStorage.setItem("cartSize",this.cart1);
    }
       //////////////////////////////////////////////////////////////////////////////
         this.subTotal=0;
        for(let item of this.carts.item) 
        {
         this.subTotal=Math.round(this.subTotal+item.itemPrice*item.itemQty);
      
        }
        if(this.subTotal==0)
        {
          this.gstResto=0;
          this.delivery=0
        }
        else{
          this.gstResto=Math.round(this.subTotal*0.18+20);
          this.delivery=40;
        }
          this.grandTotal=Math.round(this.subTotal+this.gstResto+this.delivery);

      });

    });


  }



// ----------------------popUp----------------------

closePop:any=0;
closeWindow:any=0;

closeBtn()
{
  this.closePop=0;
  this.closeWindow=1;
}
popWindow()
{
   return this.closePop;
}
cardWindow()
{
    return this.closeWindow;
   }
AddRes(){
    this.closePop=1;
    this.closeWindow=0;
}   

changeAddress(j:any)
{this.i= j;
  console.log(this.i);
this.closeBtn();
}


}


