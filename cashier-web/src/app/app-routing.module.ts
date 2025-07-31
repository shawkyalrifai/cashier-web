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
import { ChangeStockComponent } from './change-stock/change-stock.component';
import { SupplierComponent } from './create-supplier/supplier.component';
import { ListSupplierComponent } from './list-supplier/list-supplier.component';
import { ListCategoryComponent } from './list-category/list-category.component';
import { CategoryComponent } from './create-category/category.component';

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
   { path: 'changeStock', component: ChangeStockComponent },
   { path: 'listSuppliers', component: ListSupplierComponent },
   { path: 'createSupplier', component: SupplierComponent },
   { path: 'listCategory', component: ListCategoryComponent },
   { path: 'createCategory', component: CategoryComponent },




  { path: '**', redirectTo: '/home' }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
[
]
