import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import {ProductListComponent} from "./product-list/product-list.component";
import {ListSaleComponent} from "./list-sale/list-sale.component";
import {CreateSaleComponent} from "./create-sale/create-sale.component";
import { CreateProductComponent } from './create-product/create-product.component';
import { HomeComponent } from './home/home.component';
import { ProductDetailComponent } from './product-detail/product-detail.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  {path: 'home', component: HomeComponent},
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  { path: 'dashboard', component: DashboardComponent},
  { path: 'listProducts', component: ProductListComponent },
  { path: 'createProduct', component: CreateProductComponent },
  { path: 'listSales', component: ListSaleComponent, },
  { path: 'createSale', component: CreateSaleComponent},
   { path: 'product/:id', component: ProductDetailComponent },

  { path: '**', redirectTo: '/home' }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
[
]
