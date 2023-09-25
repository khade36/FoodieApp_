import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TokenserviceService } from '../token/tokenservice.service';
import {MatSnackBar} from '@angular/material/snack-bar';


@Component({
  selector: 'app-otp',
  templateUrl: './otp.component.html',
  styleUrls: ['./otp.component.css']
})
export class OtpComponent {
  constructor(private http:HttpClient,private route:Router,private service:TokenserviceService,public snackBar: MatSnackBar){}
  loginEmail:any;
  otpRes:any;
  msg:any;
  OTPSign:any;
  email:String="";
  otpReg:any;

  ngAfterContentChecked(): void {
  this.loginEmail=this.service.emailOTP;
  //console.log(this.loginEmail);
} 

  submit(myform:any){
    this.otpRes=myform.otp1;
    setTimeout(() => {
    this.http.put(`http://localhost:8084/api/verify-account?emailId=${this.loginEmail}&otp=${this.otpRes}`,{responseType: 'text' as 'json'}).subscribe((msg)=>{     
   this.msg=msg;
   if(this.msg==true)
    {
      console.log("verify otp method"+this.msg);
      this.snackBar.open("OTP Verified 🎉", '', {
        duration: 2000,
      });
      // alert("OTP Verified 🎉");-----------alert changed
      this.route.navigate(['login']);
    }
    else
    {
      this.snackBar.open("Something went wrong, try regenerating OTP", '', {
        duration: 2000,
      });
      // alert("Something went wrong, try regenerating OTP");---------------------changed alert
    }
    });
  }, 3000);  
  }

  resend(){
    this.http.put(`http://localhost:8084/api/regenerate-otp?emailId=${this.loginEmail}`,{responseType: 'text' as 'String'}).subscribe((verify)=>{
      //confirm("OTP sent to registered mail id");
      console.log("regenerated"+verify);
      this.snackBar.open("regenerated otp sent to your registered mail id 🎉", '', {
        duration: 2000,
      });
      //this.otpRes=verify;
    });
  }
}
