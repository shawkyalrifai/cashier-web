import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import {ProductListComponent} from "./product-list/product-list.component";
import {ListSaleComponent} from "./list-sale/list-sale.component";
import {CreateSaleComponent} from "./create-sale/create-sale.component";
import { CreateProductComponent } from './create-product/create-product.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },

  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  { path: 'dashboard', component: DashboardComponent},
  { path: 'listProducts', component: ProductListComponent },
  { path: 'createProduct', component: CreateProductComponent },
  { path: 'listSales', component: ListSaleComponent, },
  { path: 'createSale', component: CreateSaleComponent},

  { path: '**', redirectTo: '/login' }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
[
]
