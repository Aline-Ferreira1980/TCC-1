import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { LoginFormComponent } from './security/login-form/login-form.component';
import {ReactiveFormsModule} from "@angular/forms";
import {RouterLinkActive} from "@angular/router";
import { AppRoutingModule } from './app-routing.module';
import {HttpClient, HttpClientModule, HttpHandler} from "@angular/common/http";
import {NgOptimizedImage} from "@angular/common";
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { DocenteModule } from './docente/docente.module';
import { EstagiarioModule } from './estagiario/estagiario.module';
import { PacienteModule } from './paciente/paciente.module';

import {LoginModule} from "./security/login.module";

@NgModule({
    bootstrap: [AppComponent],
    declarations: [
        AppComponent,
    ],
    imports: [
        BrowserModule,
        RouterLinkActive,
        AppRoutingModule,
        NgOptimizedImage,
        DocenteModule,
        EstagiarioModule,
        PacienteModule,
        LoginModule,
    ],
    providers: [
    ]
})
export class AppModule { }
