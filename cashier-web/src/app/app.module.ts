import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { InputNumberModule } from 'primeng/inputnumber';
import { CalendarModule } from 'primeng/calendar';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { CommonModule } from '@angular/common';
import { CreateSaleComponent } from './create-sale/create-sale.component';
import { CreateProductComponent } from './create-product/create-product.component';
import { ListSaleComponent } from './list-sale/list-sale.component';
import { ProductListComponent } from './product-list/product-list.component';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HomeComponent } from './home/home.component';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { ChangeStockComponent } from './change-stock/change-stock.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DropdownModule } from 'primeng/dropdown';
import { SupplierComponent } from './create-supplier/supplier.component';
import { CategoryComponent } from './create-category/category.component';
import { ListSupplierComponent } from './list-supplier/list-supplier.component';
import { ListCategoryComponent } from './list-category/list-category.component';
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    RegisterComponent,
    LoginComponent,
    DashboardComponent,
    CreateSaleComponent,
    CreateProductComponent,
    ListSaleComponent,
    ProductListComponent,
    HomeComponent,
    ProductDetailComponent,
    ChangeStockComponent,
    SupplierComponent,
    CategoryComponent,
    ListSupplierComponent,
    ListCategoryComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
     CommonModule,
    ReactiveFormsModule,
      BrowserAnimationsModule,
    FormsModule,
    DropdownModule,
    CalendarModule,
    InputNumberModule,
    InputTextModule,
    InputNumberModule,
    CalendarModule,
    ButtonModule,
    FormsModule,
     TranslateModule.forRoot({
      defaultLanguage: 'en',
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
