import { CustomizeDishComponent } from './customize-dish/customize-dish.component';
import { OrderDetailComponent } from './order-detail/order-detail.component';
import { OrderOverviewComponent } from './order-overview/order-overview.component';
import { MessageComponent } from './message/message.component';
import { CustomerProfileComponent } from './customer-profile/customer-profile.component';
import { CustomerRegistrationComponent } from './customer-registration/customer-registration.component';
import { CustomerLoginComponent } from './customer-login/customer-login.component';
import { AdminLoginComponent } from './admin-login/admin-login.component';
import { InventoryDishDetailComponent } from './inventory-dish-detail/inventory-dish-detail.component';
import { InventoryAddDishComponent } from './inventory-add-dish/inventory-add-dish.component';
import { InventoryComponent } from './inventory/inventory.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { MenuDishDetailComponent } from './menu-dish-detail/menu-dish-detail.component';
import { MenuComponent } from './menu/menu.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', redirectTo: '/menu', pathMatch: 'full' },
  { path: 'menu', component: MenuComponent },
  { path: 'menu-dish-detail/:id', component: MenuDishDetailComponent },
  { path: 'customize-dish', component: CustomizeDishComponent },
  { path: 'shopping-cart', component:  ShoppingCartComponent},
  { path: 'inventory', component:  InventoryComponent},
  { path: 'inventory-add-dish', component:  InventoryAddDishComponent},
  { path: 'inventory-dish-detail/:id', component:  InventoryDishDetailComponent},
  { path: 'admin-login', component:  AdminLoginComponent},
  { path: 'customer-login', component:  CustomerLoginComponent},
  { path: 'customer-registration', component:  CustomerRegistrationComponent},
  { path: 'customer-profile', component:  CustomerProfileComponent},
  { path: 'order-overview', component: OrderOverviewComponent },
  { path: 'order-detail/:id', component: OrderDetailComponent },
  { path: 'message', component:  MessageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
