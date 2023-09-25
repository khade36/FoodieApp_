import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { RestoItemServiceService } from '../resto-item-service.service';
import { Router } from '@angular/router';
import { TokenserviceService } from '../token/tokenservice.service';
import {MatSnackBar} from '@angular/material/snack-bar';


@Component({
  selector: 'app-userview',
  templateUrl: './userview.component.html',
  styleUrls: ['./userview.component.css']
})
export class UserviewComponent {

  constructor(private http: HttpClient, private service: RestoItemServiceService,private tService:TokenserviceService, private route: Router,public snackBar: MatSnackBar) { }

  userArray: any;
  getSearchValue: String = '';

  ngAfterContentInit() {
    // let headers=new HttpHeaders().set('Authorization',`Bearer ${localStorage.getItem('token')}`);  
    this.http.get('http://localhost:8082/api/resto/getAllRestOwners').subscribe((result:any) => {
      this.userArray = result;
      // console.log(this.userArray);
    });
  }
  getItem(ownerId:any,RestoNameItem: any) {
    if (localStorage.getItem('token') == null) {
      // alert("Please login first !");
      this.snackBar.open("Please login first !", '', {
        duration: 2000,
      });
      setTimeout(() => {
        this.route.navigate(['login']);
      }, 1100);
    }
    else {
      setTimeout(() => {
        console.log("4797"+ownerId);
        if(localStorage.getItem("ownerId")!=null)
        {
          localStorage.removeItem("ownerId");
        }
        localStorage.setItem("ownerId",ownerId);
        if(localStorage.getItem("restaurantName")!=null)
        {
          localStorage.removeItem("restaurantName");
        }
        localStorage.setItem("restaurantName",RestoNameItem);
        console.log("localStorage.getItem(ownerId) "+localStorage.getItem("ownerId"));
        this.service.getRestoNameUser(ownerId,RestoNameItem);
          this.route.navigate(['home']);
      }, 1000)
    }

  }
  getSearchvalue(searchText: String) {
    this.getSearchValue = searchText;
  }
  //********Sushant's Code *********************/
  saveFavResto($event:any,ownerId: any, restaurant: any) {

    if(localStorage.getItem('token')==null)
    {
      alert("Please login first");
      this.route.navigate(['login']);
    }
    $event.stopPropagation();
    let favResto =
    {
      "ownerId": ownerId,
      "restaurant":
      {
        "restaurantName": restaurant.restaurantName,
        "rating": restaurant.rating,
        "location": restaurant.location,
        "restoImage": restaurant.restoImage,
        
      }

    };
    console.log("fffffffffffff..."+JSON.stringify(favResto));
    let token=localStorage.getItem('token');
    console.log("token"+token);
    this.service.saveFavResto(token,favResto).subscribe((data)=>{
    alert("Restaurant saved successfully in Favourite section");
    });
  }


}
