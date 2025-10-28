import { Component } from '@angular/core';
import { Navbar } from "../../navbar/navbar";
import { Footer } from "../../footer/footer";
import { CommonModule, DatePipe } from '@angular/common';
import { RouterOutlet } from '@angular/router';


@Component({
  selector: 'app-home',
  imports: [Navbar, Footer, CommonModule, RouterOutlet],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  
}
