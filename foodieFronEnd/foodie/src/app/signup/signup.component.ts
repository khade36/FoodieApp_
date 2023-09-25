import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { TokenserviceService } from '../token/tokenservice.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  // user=new User("","",0,"","",0,"","","","",0,null,"","","",0)
  file:any;
  name:any;
  otpEmail:any;
  boolean:any;
  otpSign:any;
  result:any;
  user:any;
  myform:FormGroup ;
  emailList:any;
  emailId:any="";
  emailExist:any=false;
  
  constructor(private http:HttpClient,private fb: FormBuilder,private route:Router,private service:TokenserviceService,public snackBar: MatSnackBar)
  {
   this.myform = new FormGroup({
      firstName: new FormControl ('', [Validators.required, Validators.minLength(2), Validators.maxLength(20),Validators.pattern(/^[A-Za-z]+$/)]),
      lastName: new FormControl ('', [Validators.required, Validators.minLength(2), Validators.maxLength(20),Validators.pattern(/^[A-Za-z]+$/)]),
      phoneNo: new FormControl ('', [Validators.required, Validators.pattern(/^[1-9]\d{9}$/)]),
      emailId: new FormControl ('', [Validators.required, Validators.email]),
      role: new FormControl ('', [Validators.required]),
      password: new FormControl ('', [Validators.required, Validators.minLength(8),Validators.maxLength(20),Validators.pattern(/^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*])(?=.*\d).*$/)])
    });
  }
  ngOnInit(): void {
    this.service.checkEmailId().subscribe((data)=>{
     this.emailList=data;
     this.emailList=JSON.parse(this.emailList);
     console.log("sadfdhgj"+this.emailList);
    });
   
  }
  get firstName() {
    return this.myform.get('firstName');
  }
  get lastName() {
    return this.myform.get('lastName');
  }
  get phoneNo() {
    return this.myform.get('phoneNo');
  }

  get password() {
    return this.myform.get('passsword');
  }
  get role() {
    return this.myform.get('role');
  }
 
  
  upload(event:any){
  this.file=event.target.files[0];
  console.log(this.file);
  }

  checkEmail()
  {
    
    console.log("....d.fsdfgemail"+this.emailId);
    let flag=true;
    for(let email of this.emailList)
    {
      console.log("emaaaail"+email)
      if(email==this.emailId)
      {
        console.log("came inside");
        flag=false;
        this.emailExist=true;
      }
    }
    if(flag)
    {
      this.emailExist=false;
    }
    
  }


  signUp(myform:any){

    // this.otpEmail=myform.emailId;
    this.service.getEmailId(myform.emailId);
    this.name=myform.firstName;
    console.log("Myform......"+JSON.stringify(myform));
    const data = new FormData();
    data.append('user', new Blob([JSON.stringify(myform)], {type: 'application/json'}));
     data.append('imageFile', new Blob([this.file], {type: 'image/jpeg'}));
   
    this.http.post('http://localhost:8084/api/signUp',data, {responseType: 'text' as 'json'}).subscribe((user:any)=>{

    console.log("****++++User"+user);
    
this.user=user;
this.user=JSON.parse(this.user);
    this.otpSign=this.user.otp;

    console.log(this.otpSign);

    this.service.getOTP(this.otpSign);

    this.snackBar.open("Sign Up successfully done..😍 :"+ this.name, '', {
      duration: 2000,
    });
    // alert("Sign Up successfully done..😍 :"+ this.name);
  this.route.navigate(['otp'])

   });
  
  }
  getValue(){
    return this.boolean;
  }
  get(data:any){
     console.log(this.boolean=data);
  }
}



