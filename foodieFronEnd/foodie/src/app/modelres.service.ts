import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ModelresService {

  constructor() { }
  array:{}=[{}];
  // rest(restaurantName:String,rating:any,location:String,item:any,restoImage:any){
   rest(data:any){
   this.array={data}
  }
}
