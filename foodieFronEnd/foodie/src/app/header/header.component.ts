import { Component, EventEmitter, Output } from '@angular/core';
import { TokenserviceService } from '../token/tokenservice.service';
import { AfterContentChecked } from '@angular/core';
import { Router } from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements AfterContentChecked{

  constructor(private logindetails:TokenserviceService,private services:TokenserviceService,private route: Router,public snackBar: MatSnackBar){}
  
  
  login:any;
  loggedIn:String="Login";
  loggedB:boolean=false;
  search:String='';
  loginByName:any;
  role:any;
  token:any;
  cartSize:any;
  
  ngAfterContentChecked(): void {
    let userName=localStorage.getItem("UserName");
    this.login=userName;
    this.token=localStorage.getItem('token');
    this.role=localStorage.getItem("User");
    this.cartSize=localStorage.getItem("cartSize");
   // console.log("token"+this.token);
  }
  
  logOut(){
    this.services.logOut();
  }  
  
  loggedO(){
    if(this.token!=null){
    return true;
    }
    else{
    return false;
    }
  }
  loggedI(){
    if(this.token!=null){
    return false;
    }
    else{
    return true;
    }
  }
  @Output()
  eventSearch:EventEmitter<String>=new EventEmitter<String>();
  
  sendText(){
  this.eventSearch.emit(this.search);
  }
  validUser()
  {
    if(localStorage.getItem("User")=="User" && localStorage.getItem("token")!=null)
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  validForSearch()
  {
    if(localStorage.getItem("User")=="User" )
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  goToCart()
  {
    if(localStorage.getItem('token')==null)
    {
      this.snackBar.open("Please login first", '', {
        duration: 2000,
      });
    //  alert("Please login first");-----------changed alert
      this.route.navigate(['login']);
    }
    else{
      this.route.navigate(['cart']);
    }
  }
  getloginBy(){
  
  console.log("+++"+this.services.loginBy);
  if(localStorage.getItem("User")=="Owner" || localStorage.getItem("User")=="admin" ){
     return false;
  }
  else{
    return true;
  }
  }
  hidden = false;

  toggleBadgeVisibility() {
    this.hidden = !this.hidden;
  }
  logo()
{
  if(this.role=="Owner")
  {
    this.route.navigate(['restaurant']);
  }
  else
  {
    this.route.navigate(['userview']);
  }
}
  }