import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule,Routes } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FaqComponent } from './faq/faq.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { FormsModule } from '@angular/forms';
import { FooterComponent } from './footer/footer.component';
import { SignupComponent } from './signup/signup.component';
import { HttpClientModule } from '@angular/common/http';
import { RestaurantOwnerComponent } from './restaurant-owner/restaurant-owner.component';
import { OtpComponent } from './otp/otp.component';
import { ItemsComponent } from './items/items.component';
import { UserviewComponent } from './userview/userview.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { CartComponent } from './cart/cart.component';
import { OrdersComponent } from './orders/orders.component';
import { AdminComponent } from './admin/admin.component';
import { RestaurantRequestComponent } from './restaurant-request/restaurant-request.component';
import { RestaurantsComponent } from './restaurants/restaurants.component';
import { ProfileComponent } from './profile/profile.component';
import { FavouriteItemsComponent } from './favourite-items/favourite-items.component';
import { FavouriteRestoComponent } from './favourite-resto/favourite-resto.component';
import { SetPasswordComponent } from './set-password/set-password.component';
import { SubmitPassComponent } from './submit-pass/submit-pass.component';
import { OwnerOrdersComponent } from './owner-orders/owner-orders.component';
import {AuthGuardService } from './services/auth-guard.service';
import { AdminAuthService } from './services/admin-auth.service';
import { OwnerGuard } from './services/owner.guard';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatBadgeModule} from '@angular/material/badge';
import { ReactiveFormsModule } from '@angular/forms';

const routes:Routes=[
  
  {path:'userview',component:UserviewComponent},
  {path:'home',component:HomeComponent,canActivate:[AuthGuardService]},
  {path:'home',component:HomeComponent,canActivate:[AuthGuardService]},
  {path:'faq',component:FaqComponent},
  {path:'login',component:LoginComponent},
  {path:'signup',component:SignupComponent},
  {path:'restaurant',component:RestaurantOwnerComponent,canActivate:[OwnerGuard]},
  {path: "item", component: ItemsComponent,canActivate:[OwnerGuard]},
  {path:'resto-orders',component:OwnerOrdersComponent,canActivate:[OwnerGuard]},
  {path:'otp',component:OtpComponent},
  {path:'item',component:ItemsComponent,canActivate:[OwnerGuard]},
  {path: 'profile', component: ProfileComponent,canActivate:[AuthGuardService]},
  {path: 'fav-item', component: FavouriteItemsComponent,canActivate:[AuthGuardService]},
  {path:'fav-resto',component:FavouriteRestoComponent,canActivate:[AuthGuardService]},
  {path:'admin',component:AdminComponent,canActivate:[AdminAuthService]},
  {path:'restoReq',component:RestaurantRequestComponent,canActivate:[AdminAuthService]},
  {path:'restaurants',component:RestaurantsComponent,canActivate:[AdminAuthService]},
  {path:'cart',component:CartComponent,canActivate:[AuthGuardService]},
  {path:'orders',component:OrdersComponent,canActivate:[AuthGuardService]},
  {path:'',component:UserviewComponent},
  {path:'password',component:SetPasswordComponent},
  {path:'submit',component:SubmitPassComponent},
  {path:'userview',component:UserviewComponent,canActivate:[AuthGuardService]},
  {path:'cart/userview',component:UserviewComponent,canActivate:[AuthGuardService]},
  {path:'orders/userview',component:UserviewComponent,canActivate:[AuthGuardService]},
  {path:'fav-item/userview',component:UserviewComponent,canActivate:[AuthGuardService]},
  {path:'fav-resto/userview',component:UserviewComponent,canActivate:[AuthGuardService]},
  {path:'profile/userview',component:UserviewComponent,canActivate:[AuthGuardService]},
  {path:'home/userview',component:UserviewComponent,canActivate:[AuthGuardService]},
  {path:'faq/userview',component:UserviewComponent,canActivate:[AuthGuardService]}
]

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FaqComponent,
    HomeComponent,
    LoginComponent,
    FooterComponent,
    SignupComponent,
    RestaurantOwnerComponent,
    OtpComponent,
    ItemsComponent,
    UserviewComponent,
    ProfileComponent,
    CartComponent,
    OrdersComponent,
    FavouriteItemsComponent,
    FavouriteRestoComponent,
    RestaurantRequestComponent,
    RestaurantsComponent,
    AdminComponent,
    SetPasswordComponent,
    SubmitPassComponent,
    OwnerOrdersComponent,
  ],
  imports: [

    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(routes),
    BrowserAnimationsModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatBadgeModule,
    ReactiveFormsModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
