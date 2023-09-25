import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-submit-pass',
  templateUrl: './submit-pass.component.html',
  styleUrls: ['./submit-pass.component.css']
})
export class SubmitPassComponent {
  constructor(private http:HttpClient,private route:Router){}
  
  setPassword(value:any){  
  console.log(value.user_email);
  console.log(value.password);  
  this.http.put(`http://localhost:8084/api/set-password?emailId=${value.user_email}&newPassword=${value.password}`, {}).subscribe(() => {
    });
  alert("password reseted successfully");
  this.route.navigate(['login']);
  }
}
