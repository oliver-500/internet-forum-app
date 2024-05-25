import { NgModule } from '@angular/core';
import { MatInputModule } from '@angular/material/input';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { HttpClientModule } from '@angular/common/http';
import {MatButtonModule} from '@angular/material/button';
import {MatListModule} from '@angular/material/list';
import {MatIconModule} from '@angular/material/icon';
import {MatDialogModule} from '@angular/material/dialog';
import {MatRadioModule} from '@angular/material/radio';
import {MatTableModule} from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatSnackBarModule} from '@angular/material/snack-bar';


const materialModules = [
  MatInputModule,
  ReactiveFormsModule,
  MatFormFieldModule,
  HttpClientModule,
  MatButtonModule,
  MatListModule,
  MatIconModule,
  MatDialogModule,
  FormsModule,
  MatRadioModule,
  MatTableModule,
  MatPaginatorModule,
  MatSlideToggleModule,
  MatCheckboxModule,
  MatSnackBarModule
]


@NgModule({
  declarations: [],
  imports: [   
    ...materialModules,
    
  ],
  exports: [
    ...materialModules
  ]
})
export class AppMaterialModule { }
