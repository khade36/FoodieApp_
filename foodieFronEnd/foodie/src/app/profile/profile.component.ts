import { Component, OnInit } from '@angular/core';
import { TokenserviceService } from '../token/tokenservice.service';
import { Router } from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  token: any;
  file: any;
  user: any = [];

  constructor(private service: TokenserviceService, private route: Router,public snackBar: MatSnackBar) { }
  ngOnInit(): void {
    // this.token=localStorage.getItem('token');
    this.token=localStorage.getItem('token');
    this.service.getUser(this.token).subscribe((data) => {
      this.user = (data);
      this.user = JSON.parse(this.user);

      console.log(this.user);
    });

  }

  GetFavResto() {
    this.route.navigate(['fav-resto']);
  }
  GetFavItem() {
    this.route.navigate(['fav-item']);
  }





  // -------------popup------------
  closePop: any = 0;
  closeWindow: any = 0;


  saveAddress(myform: any) {
    const data = new FormData();
    console.log(myform);
    this.token=localStorage.getItem('token');
    this.service.addAddress(this.token, myform).subscribe((data1: any) => {
      console.log(data1);
      this.closePop = 0;
      this.closeWindow = 1;
    
      this.service.getUser(this.token).subscribe((data) => {
        this.user = (data);
        this.user = JSON.parse(this.user);
  
        console.log(this.user);
    
      });
      this.snackBar.open("Address added 🎉", '', {
        duration: 2000,
      });
      // alert("Address added 🎉");----------------changed

    });
  }


  closeBtn() {
    this.closePop = 0;
    this.closeWindow = 1;
  }
  popWindow() {
    return this.closePop;
  }
  cardWindow() {
    return this.closeWindow;
  }
  AddRes() {
    this.closePop = 1;
    this.closeWindow = 0;
  }


  // --------------------popUp2------------------

 

  closePop2: any = 0;
  closeWindow2: any = 0;
present:any=false;
data:any;

  upload(event: any) {
    this.file =( event.target.files[0]);
    console.log("---");
    const data = new FormData();
    data.append("file",new Blob([this.file], {type: 'multipart/form-data'}));
    console.log("+++");
    this.present=true;
   
  }


  closeBtn2() {
    this.closePop2 = 0;
    this.closeWindow2 = 1;
  }
  popWindow2() {
    return this.closePop2;
  }
  cardWindow2() {
    return this.closeWindow2;
  }

  editPic() {
    this.closePop2 = 1;
    this.closeWindow2 = 0;
  }

  uploadImage()
  {
    this.token=localStorage.getItem('token');
    this.service.editProfilePic(this.token, this.file).subscribe((data1: any) => {
     
      this.closePop2 = 0;
    this.closeWindow2 = 1;
    console.log("********"+data1);
    this.snackBar.open("Profile Pic uploaded successfully 🎉", '', {
      duration: 2000,
    });
      // alert("Profile Pic uploaded successfully 🎉");---------------------alert changed

    });
  }


}
