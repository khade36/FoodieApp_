import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class RestoItemServiceService {

  constructor(private http:HttpClient,private route:Router,public snackBar: MatSnackBar) { }
  restoName:any;
  itemArray:any;
  restoNameFrmUser:any;
  nameResta:any;
  ownerId:any;

  delete(data:any){
  this.restoName=data;
  // console.log(this.restoName);
  let token=localStorage.getItem("token");
  let headers=new HttpHeaders().set('Authorization',`Bearer ${token}`);
  this.http.delete(`http://localhost:8082/api/restaurant/deleteRestaurant/${data}`,{headers:headers}).subscribe((done)=>{
    // alert("deleted 😒:"+data);----------------------changed
  });
}
  //ITEM LIST //restowoner home page
  getItem(restName:any){ 
   this.nameResta=restName;
  console.log("--------------restoitem"+this.nameResta);
  let token=localStorage.getItem("token");
   let headers=new HttpHeaders().set('Authorization',`Bearer ${token}`);
   this.http.get(`http://localhost:8082/api/restaurant/getItemList/${restName}`,{'headers': headers}).subscribe((result)=>{
   this.itemArray=result;
   console.log(result+"------");
   this.route.navigate(['item']);
  //  console.log(this.itemArray+"88");
  //  var array=JSON.stringify(this.itemArray);
  //  this.itemArray=array;
  //  });
   })
  }
  getRestoNameUser(ownerId:any,RestoNameItem:any){
    this.restoNameFrmUser=RestoNameItem;
    this.ownerId=ownerId;
console.log("9747"+this.ownerId);
   // console.log("4444"+this.restoNameFrmUser);
  } 

//******Sushant's Code ****************/

saveFavResto(token:any,resto:any)
{
  
  let tokenStr='Bearer '+token;
  console.log(tokenStr);
  let headers=new HttpHeaders().set("Authorization",tokenStr);
 
  console.log(headers);
 console.log(resto);
 return this.http.post("http://localhost:8086/api/user/favResto",resto,{'headers': headers ,responseType:'text' as 'json'});
}

addFavItem(token:any,favItem:any)
{
  let tokenStr='Bearer '+token;
  console.log(tokenStr);
  let headers=new HttpHeaders().set("Authorization",tokenStr);
 
  console.log(headers);
 console.log(favItem);
 return this.http.post("http://localhost:8086/api/user/favItem",favItem,{'headers': headers ,responseType:'text' as 'json'});
}




addToCart(token:any,cart:any)
{
   
  let tokenStr='Bearer '+token;
  console.log(tokenStr);
  let headers=new HttpHeaders().set("Authorization",tokenStr);
 
  console.log(headers);
 console.log(cart);
 return this.http.post("http://localhost:8085/api/cart/user/saveItem",cart,{'headers': headers ,responseType:'text' as 'json'});
}


  }


