import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-set-password',
  templateUrl: './set-password.component.html',
  styleUrls: ['./set-password.component.css']
})
export class SetPasswordComponent {

  constructor(private http:HttpClient,private route:Router,public snackBar: MatSnackBar){}

submit(email:any){
this.http.put(`http://localhost:8084/api/forget-password?emailId=${email}`,{}).subscribe(()=>{
});
// setTimeout(()=>{
//   alert("Forgot password link sent to :"+email);  --------------changed
// },2000);

this.snackBar.open("Forgot password link sent to :"+email, '', {
  duration: 2000,
});
}
}