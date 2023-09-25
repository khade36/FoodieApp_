import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AfterContentChecked, AfterContentInit, AfterViewInit, Component } from '@angular/core';
import { RestoItemServiceService } from '../resto-item-service.service';
import { TokenserviceService } from '../token/tokenservice.service';
import {MatSnackBar} from '@angular/material/snack-bar';


@Component({
  selector: 'app-items',
  templateUrl: './items.component.html',
  styleUrls: ['./items.component.css']
})
export class ItemsComponent implements AfterViewInit {

  constructor(private https:HttpClient,private service:RestoItemServiceService,private services:TokenserviceService,public snackBar: MatSnackBar){}
  closePop:any=1;
  closeWindow:any=0;
  itemsArrays:any;
  file:any;
  restoNamePost:any;
  restaName:any;
  itemsArray:any;
  itemNameP:String=""
  
  
  ngAfterViewInit(): void {
      this.restaName=this.service.nameResta;
      this.restaName=localStorage.getItem("restaurantName");
      console.log("localStorage.getItem(restaurantName)"+localStorage.getItem("restaurantName"));
      this.service.getItem(this.restaName);
  }
  
  upload(event:any){
    this.file=event.target.files[0];
    console.log(this.file);
  }
  
  postItem(myform:any){
    //  this.restoNamePost=this.service.restoName;
    // console.log("----"+this.);
    // console.log(this.restaName+"55588");
    const data = new FormData();
    //data.append('imageFile', new Blob([this.file], {type: 'multipart/form-data'}));
    data.append('imageFile',this.file);
    data.append('Item', new Blob([JSON.stringify(myform)], {type: 'application/json'})); 
     let token=localStorage.getItem('token');
    let headers=new HttpHeaders().set('Authorization',`Bearer ${token}`);
    this.https.post(`http://localhost:8082/api/restaurant/saveItem/${this.restaName}`,data,{headers:headers}).subscribe((result)=>{
   // console.log(result); 
    // alert("Item added 🎉");---------------changed alert
    this.snackBar.open("Item added 🎉", '', {
      duration: 2000,
    });
    this.service.getItem(this.restaName);
    //this.update();
  });
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
  logOut(){
    this.services.logOut();
  }  
  update(){
    let token=localStorage.getItem('token');
       let headers=new HttpHeaders().set('Authorization',`Bearer ${token}`);
       this.https.get(`http://localhost:8082/api/restaurant/getItemList/${this.restaName}`,{'headers': headers}).subscribe((result)=>{
      //  this.itemsArray=JSON.stringify(result);
       this.itemsArray=result;
       this.itemsArray=JSON.parse(this.itemsArray);
      //  console.log(this.itemsArrays+"------4478");
  });
  }
  //Method to delete the Item
  delete(item:any){
    // console.log(item);
    // console.log(this.restaName);
    let token=localStorage.getItem('token');
    let headers=new HttpHeaders().set('Authorization',`Bearer ${token}`);
    this.https.delete(`http://localhost:8082/api/restaurant/deleteRestaurantItem/${this.restaName}/${item}`,{headers:headers}).subscribe((result)=>{
    console.log("result");
  });
  setTimeout(()=>{
    this.update();
  },1000);
  }
  
  updatePrice(){
    const price=prompt("Enter your new price");  
    let token=localStorage.getItem('token');
    let headers=new HttpHeaders().append('Authorization',`Bearer ${token}`);
    console.log(headers);
    this.https.put(`http://localhost:8082/api/restaurant/updatePrice/${this.restaName}/${this.itemNameP}?newPrice=${price}`,{},{'headers': headers ,responseType:'text' as 'json'}).subscribe((result)=>{
      console.log(result);
      this.update();
  });
  }
  getItemName(itemName:any){
   this.itemNameP=itemName;
   console.log(this.itemNameP);
  }
  }