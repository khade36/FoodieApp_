import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { TokenserviceService } from '../token/tokenservice.service';
import { RestoItemServiceService } from '../resto-item-service.service';
import { Router } from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  constructor(private http:HttpClient,private service:RestoItemServiceService,private tService:TokenserviceService,private route:Router,public snackBar: MatSnackBar){}

userArray:any;
restoName:any;
itemQty:any;
getSearchValue:String='';
cart:any;
cart1:any;


// getItem(){
//   //  this.restoName=this.service.restoNameFrmUser;
//   //  console.log(this.restoName+"787");
//    let headers=new HttpHeaders().set('Authorization',`Bearer ${localStorage.getItem('token')}`);
//    this.http.get("http://localhost:8082/api/restaurant/getItemList/susahntJI",{headers:headers}).subscribe((result)=>{
//    this.userArray=Object.values(result);
//    console.log(this.userArray);
//    });
//    }
ngAfterContentInit(){
  this.restoName=localStorage.getItem("restaurantName");
  let token=localStorage.getItem('token');
  //let token="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdXNoYW50Z2Fpa3dhZDA5OTBAZ21haWwuY29tIiwiaWF0IjoxNjg5ODUxNTg1fQ.RVM8b6p0pTs3wgjFezPzpBTaY3a99vQ_Sv4PyUK7DiE";
  let tokenStr='Bearer '+token;
  console.log(tokenStr);
  let headers=new HttpHeaders().set("Authorization",tokenStr);
  console.log(headers);
  console.log("6986552 "+this.restoName);
  let ownerId=localStorage.getItem("ownerId");
  this.http.get(`http://localhost:8082/api/restaurant/ItemList/${this.restoName}/${ownerId}`,{'headers': headers,responseType:'text' as 'json'}).subscribe((result)=>{
  this.userArray=result;
  this.userArray=JSON.parse(this.userArray);
  console.log("****"+this.userArray);
});   
}
getSearchvalue(searchText:String){
  this.getSearchValue=searchText;
}
addToCart(x:any)
{
  let token=localStorage.getItem('token');
//let token="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdXNoYW50Z2Fpa3dhZDA5OTBAZ21haWwuY29tIiwiaWF0IjoxNjg5ODUxNTg1fQ.RVM8b6p0pTs3wgjFezPzpBTaY3a99vQ_Sv4PyUK7DiE";



let ownerId=localStorage.getItem("ownerId");
let restaurantName=localStorage.getItem("restaurantName");
console.log("rrrrrrrrrrrres"+restaurantName);
console.log("oooooooooo"+ownerId);
if(x.itemQty<1)
{

  this.snackBar.open("Enter Valid Quantity", '', {
    duration: 2000,
  });
  
  // alert("Enter Valid Quantity");------------alert changed
  
}
else{
this.cart={
"ownerId":ownerId,
"restaurantName":restaurantName,
"item":[{
 "itemName":x.itemName,
 "itemPrice":x.itemPrice,
 "itemQty":x.itemQty,
 "description":x.description,
"itemPic":x.itemPic
}]
};
if(localStorage.getItem("cartSize")==null)
{
  this.cart1=0;
}
else{
  this.cart1=localStorage.getItem("cartSize");
  this.cart1=parseInt(this.cart1);
}
  console.log("$$$$"+this.cart1);
  this.cart1=this.cart1+parseInt(x.itemQty);
  console.log("#$#$#"+this.cart1);
  localStorage.setItem("cartSize",this.cart1);
  console.log(".$$$........this.cart"+this.cart1);


this.service.addToCart(token,this.cart).subscribe((cart)=>{
  this.snackBar.open("Item added to Cart", '', {
    duration: 2000,
  });
// alert("Item added to Cart");------------alert changed
});
}
}

addFavItem(itemName:any,itemPrice:any,description:any,itemPic:any)
{
  let token=localStorage.getItem('token');
  let favItem=
  {
    "ownerId":localStorage.getItem("ownerId"),
    "restaurantName":localStorage.getItem("restaurantName"),
    "item":
    {
      "itemName":itemName,
      "itemPrice":itemPrice,
      "description":description,
      "itemPic":itemPic
    }
  };
  
  this.service.addFavItem(token,favItem).subscribe(()=>{
    this.snackBar.open("Item Added in Favourite Section", '', {
      duration: 2000,
    });
    // alert("Item Added in Favourite Section");---------------alert changed
  });

}


}