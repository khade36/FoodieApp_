import { AfterContentChecked, Component, SimpleChanges } from '@angular/core';
import { TokenserviceService } from '../token/tokenservice.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ModelresService } from '../modelres.service';
import { RestoItemServiceService } from '../resto-item-service.service';
import { Router } from '@angular/router';
import { AfterContentInit } from '@angular/core';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-restaurant-owner',
  templateUrl: './restaurant-owner.component.html',
  styleUrls: ['./restaurant-owner.component.css']
})
export class RestaurantOwnerComponent implements AfterContentInit { 
  constructor(private service:TokenserviceService,private http:HttpClient,private restItem:RestoItemServiceService,private route:Router,public snackBar: MatSnackBar){}  
  
  login:String="login";
  file:any;
  array:any;
  isShowDiv = false;
  AllResto:any;
  rstoName:any;
  closePop:any=1;
  closeWindow:any=0;
       
  toggleDisplayDiv() {  
  this.isShowDiv = !this.isShowDiv;
  }
  
  // to get the name in user 
  ngAfterContentChecked(): void {
  console.log(this.login=this.service.loginId);
  }
  //selecting the file here
  upload(event:any){
    this.file=event.target.files[0];
    console.log(this.file);
  }
  
  //saving resto data along with image using formData
  saveResto(myform:any){
    

    var data = new FormData();
     
    //data.append('imageFile', new Blob([this.file], {type: 'multipart/form-data'}));
    data.append('imageFile',this.file);
    data.append('Restaurant', new Blob([JSON.stringify(myform)], {type: 'application/json'})); 
    let token=localStorage.getItem('token');
    let headers=new HttpHeaders().set('Authorization',`Bearer ${token}`);
    //console.log(myform);
    this.http.post('http://localhost:8082/api/restaurant/saveRestaurant',data,{'headers': headers}).subscribe((data:any)=>{ 
    console.log(data);
    this.snackBar.open("Restaurant added 🎉", '', {
      duration: 2000,
    });
    // alert("Restaurant added 🎉");
   });
  }
  
  ngAfterContentInit(){
    // console.log("token"+localStorage.getItem('token'));
    let token=localStorage.getItem('token');
    let headers=new HttpHeaders().set('Authorization',`Bearer ${token}`);
    this.http.get('http://localhost:8082/api/restaurant/getRestaurantList',{'headers': headers}).subscribe((result)=>{
    this.AllResto=result;
    this.AllResto=JSON.parse(this.AllResto);
    console.log("***"+this.AllResto);
  });
  }
  
  
  update(){
    let token=localStorage.getItem('token');
    let headers=new HttpHeaders().set('Authorization',`Bearer ${token}`);
    this.http.get('http://localhost:8082/api/restaurant/getRestaurantList',{'headers': headers}).subscribe((result)=>{
      this.AllResto=result;
      this.AllResto=JSON.parse(this.AllResto);
  });
  }
  
  
  // viewResto(){
  //   let headers=new HttpHeaders().set('Authorization',`Bearer ${localStorage.getItem('token')}`);
  //   this.http.get('http://localhost:8082/api/restaurant/getRestaurantList',{headers:headers}).subscribe((result)=>{
  //   this.array=result;
  //   });
  //   }
  
    delteRsto(data:any){
    // console.log(data);
    this.restItem.delete(data);
    setTimeout(()=>{
      this.update();
    },1000);
    this.snackBar.open("deleted 😒:"+data, '', {
      duration: 2000,
    });
  }
    item(data:any){
    // console.log(data.value);
    this.restItem.getItem(data.value);
    localStorage.setItem("restaurantName",data.value);
    // console.log(this.restItem);
  }
  
    logOut(){
    this.service.logOut();
  }
  closeBtn(){
    this.closePop=0;
    this.closeWindow=1;
    this.update();
  }
  popWindow(){
     return this.closePop;
  }
  cardWindow(){
      return this.closeWindow;
     }
  AddRes(){
      this.closePop=1;
      this.closeWindow=0;
  }   
// -------------------------Sushant's Code-------------------------
orders()
{
  this.route.navigate(["resto-orders"]);
}


  }