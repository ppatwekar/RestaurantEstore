import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenuComponent } from './menu/menu.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { CustomerLoginComponent } from './customer-login/customer-login.component';
import { AdminLoginComponent } from './admin-login/admin-login.component';
import { CustomerRegistrationComponent } from './customer-registration/customer-registration.component';
import { CustomerProfileComponent } from './customer-profile/customer-profile.component';
import { InventoryComponent } from './inventory/inventory.component';
import { MenuDishDetailComponent } from './menu-dish-detail/menu-dish-detail.component';
import { InventoryDishDetailComponent } from './inventory-dish-detail/inventory-dish-detail.component';
import { InventoryAddDishComponent } from './inventory-add-dish/inventory-add-dish.component';
import { MessageComponent } from './message/message.component';
import { ShoppingCartHoveringComponent } from './shopping-cart-hovering/shopping-cart-hovering.component';
import { LoginAndUserStatusComponent } from './login-and-user-status/login-and-user-status.component';
import { RightIconsComponent } from './right-icons/right-icons.component';
import { LogoMidComponent } from './logo-mid/logo-mid.component';
import { OrderOverviewComponent } from './order-overview/order-overview.component';
import { OrderDetailComponent } from './order-detail/order-detail.component';
import { CustomizeDishComponent } from './customize-dish/customize-dish.component';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    ShoppingCartComponent,
    CustomerLoginComponent,
    AdminLoginComponent,
    CustomerRegistrationComponent,
    CustomerProfileComponent,
    InventoryComponent,
    MenuDishDetailComponent,
    InventoryDishDetailComponent,
    InventoryAddDishComponent,
    MessageComponent,
    ShoppingCartHoveringComponent,
    LoginAndUserStatusComponent,
    RightIconsComponent,
    LogoMidComponent,
    OrderOverviewComponent,
    OrderDetailComponent,
    CustomizeDishComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
