import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';



@Injectable({
  providedIn: 'root'
})
export class TokenserviceService {

  constructor(private http:HttpClient,private route:Router,public snackBar: MatSnackBar) { }
  loginId:String="";
  token:any;
  login:String="";
  emailOTP:String="";
  invalidCred:any;
  OTP:any;
  RestoName:any;
  loginBy:any;

public generateToken(emailId:any,password:any,loginas:any){
  this.http.post('http://localhost:8084/api/login',{emailId, password},{responseType: 'text' as 'json'}).subscribe((result:any)=>{
  this.token= result;
  localStorage.setItem('token',this.token);
  // if(this.token!=null && loginas=="Owner"){
  // localStorage.setItem("token",this.token);  
  // this.loginBy=loginas;
  // this.loginId=emailId;
  // this.route.navigate(['restaurant']);
  // }
  // if(this.token!=null && loginas=="User"){
  //   localStorage.setItem("token",this.token);
  //   this.loginId=emailId;
  //   this.route.navigate(['/userview']);
  // }
  if(this.token==null)
  {
  this.invalidCred="Invalid Credentials ";
  }
  else{
    this.invalidCred="";
  }
  });

   
}  
 addOwners(data:any){
  this.http.post('http://localhost:8082/api/saveRestoOwner',data,{responseType:'text' as 'json'}).subscribe((result)=>{
    console.log(result);
    this.snackBar.open("Owner added successfully !", '', {
      duration: 2000,
    });
    // alert("Owner added successfully !");---------------alert changed
  })
 }
 
 addResto(data:any){
  this.http.post('http://localhost:8082/api/saveRestoOwner',data,{responseType:'text' as 'json'}).subscribe((result)=>{
  //console.log(result);
  });
 }

getEmailId(email:any){
this.emailOTP=email;
}  

getOTP(otp:any){
this.OTP=otp;
//console.log("logged in"+this.OTP);
}

logOut(){
  localStorage.clear();
  this.loginId="";
  this.snackBar.open("Logged Out", '', {
    duration: 2000,
  });
  // alert("Logged Out");---------------alert changed
  // alert("Logged Out");
  this.route.navigate(['login']);
}
getRestoName(data:any){
  this.RestoName=data;
}

//------------------xx---------Sushant's Code----------xx--------------------//


getRole(emailId:any,password:any)
{
  let user={
    "emailId":emailId,
    "password":password
  };

  return this.http.post('http://localhost:8084/api/checkRole',user,{responseType: 'text' as 'json'});
}

getUser(token:any)
{
  let tokenStr='Bearer '+token;
  console.log(tokenStr);
  let headers=new HttpHeaders().set("Authorization",tokenStr);
  console.log(headers);
 
 return this.http.get("http://localhost:8084/api/user/user",{headers,responseType:'text' as 'json'});

}


getFavResto(token:any)
{
  let tokenStr='Bearer '+token;
  console.log(tokenStr);
  let headers=new HttpHeaders().set("Authorization",tokenStr);
  console.log(headers);
 
 return this.http.get("http://localhost:8086/api/user/getFavRestList",{headers,responseType:'text' as 'json'});

}
getFavItem(token:any)
{
  let tokenStr='Bearer '+token;
  console.log(tokenStr);
  let headers=new HttpHeaders().set("Authorization",tokenStr);
  console.log(headers);
 
 return this.http.get("http://localhost:8086/api/user/getFavItemList",{headers,responseType:'text' as 'json'});

}

getallUsers(token:any)
{
  let tokenStr='Bearer '+token;
  console.log(tokenStr);
  let headers=new HttpHeaders().set("Authorization",tokenStr);
  console.log(headers);
 
 return this.http.get("http://localhost:8084/api/user/listOfUser",{headers,responseType:'text' as 'json'});

}
getallRestaurants()
{
  
 return this.http.get("http://localhost:8082/api/resto/getAllRestOwners",{responseType:'text' as 'json'});

}

approveRestaurants(token:any,ownerId:any,restaurantName:any)
{
  let tokenStr='Bearer '+token;
  console.log(tokenStr);
  let headers=new HttpHeaders().set("Authorization",tokenStr);
  console.log(headers);
 
 return this.http.put(`http://localhost:8082/api/changeRestoStatus/${ownerId}/${restaurantName}`,{headers,responseType:'text' as 'json'});

}

getCart(token:any)
{
  let tokenStr='Bearer '+token;
  console.log(tokenStr);
  let headers=new HttpHeaders().set("Authorization",tokenStr);
  console.log(headers);
 
 return this.http.get(`http://localhost:8085/api/cart/user/cart`,{headers,responseType:'text' as 'json'});

}


deleteCart(token:any)
{
  let tokenStr='Bearer '+token;
  console.log(tokenStr);
  let headers=new HttpHeaders().set("Authorization",tokenStr);
  console.log(headers);
 
 return this.http.delete(`http://localhost:8085/api/cart/user/emptyCart`,{headers,responseType:'text' as 'json'});

}

deleteCartItem(token:any,itemName:any)
{
  let tokenStr='Bearer '+token;
  console.log(tokenStr);
  let headers=new HttpHeaders().set("Authorization",tokenStr);
  console.log(headers);
 
 return this.http.delete(`http://localhost:8085/api/cart/user/${itemName}`,{headers,responseType:'text' as 'json'});

}


placeOrder(token:any,order:any)
{
  let tokenStr='Bearer '+token;
  console.log(tokenStr);
  let headers=new HttpHeaders().set("Authorization",tokenStr);
  console.log(headers);
 
 return this.http.post('http://localhost:8083/api/orders/user/cart',order,{headers,responseType:'text' as 'json'});

}


getOrders(token:any)
{
  let tokenStr='Bearer '+token;
  console.log(tokenStr);
  let headers=new HttpHeaders().set("Authorization",tokenStr);
 
  console.log(headers);
 
 return this.http.get(`http://localhost:8083/api/orders/user/Items`,{'headers': headers ,responseType:'text' as 'json'},);

}


cancelOrder(token:any,data:any)
{
  let tokenStr='Bearer '+token;
  console.log(tokenStr);
  let headers=new HttpHeaders().set("Authorization",tokenStr);
 
  console.log(headers);
 console.log(data);
 return this.http.delete(`http://localhost:8083/api/orders/user/${data}`,{'headers': headers ,responseType:'text' as 'json'});

}


addAddress(token:any,data:any)
{
  let tokenStr='Bearer '+token;
  console.log(tokenStr);
  let headers=new HttpHeaders().set("Authorization",tokenStr);
 
  console.log(headers);
 console.log(data);
 return this.http.post("http://localhost:8084/api/user/updateAddress",data,{'headers': headers ,responseType:'text' as 'json'});
}

changeQty(itemName:any,itemQty:any,emailId:any)
{
  
 
 return this.http.put(`http://localhost:8085/api/cart/Items/${itemName}/${itemQty}/${emailId}`,{responseType:'text' as 'json'});

}


editProfilePic(token:any,data:any)
{
  let tokenStr='Bearer '+token;
  console.log(tokenStr);
  let headers=new HttpHeaders().set("Authorization",tokenStr);
 
  console.log(headers);
 console.log(data);
 return this.http.post("http://localhost:8084/api/user/updatePic",data,{'headers': headers ,responseType:'text' as 'json'});
}

 getOwnerOrders(token:any){
  let tokenStr='Bearer '+token;
  
  console.log(tokenStr);
  let headers=new HttpHeaders().set("Authorization",tokenStr);
  console.log(headers);
 return this.http.get("http://localhost:8083/api/orders/user/OwnerOrders",{'headers': headers ,responseType:'text' as 'json'});
}

approveOrder(token:any,emailId:any,orderId:any)
{
  let tokenStr='Bearer '+token;
  
  console.log(tokenStr);
  let headers=new HttpHeaders().set("Authorization",tokenStr);
  console.log(headers);
 return this.http.put(`http://localhost:8083/api/orders/changeOrderStatus/${emailId}/${orderId}`,{responseType:'text' as 'json'});
}
checkEmailId()
{
  
 return this.http.get("http://localhost:8084/api/checkEmail",{responseType:'text' as 'json'});

}
}  



 

