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

@NgModule({
    bootstrap: [AppComponent],
    declarations: [
        AppComponent,
        LoginFormComponent
    ],
    imports: [
        BrowserModule,
        ReactiveFormsModule,
        RouterLinkActive,
        AppRoutingModule,
        HttpClientModule,
        NgOptimizedImage,
        FontAwesomeModule,
        DocenteModule,
        EstagiarioModule,
        PacienteModule,
    ],
    providers: []
})
export class AppModule { }
