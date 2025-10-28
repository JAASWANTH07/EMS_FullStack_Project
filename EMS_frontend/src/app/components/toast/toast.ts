import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-toast',
  imports: [CommonModule],
  templateUrl: './toast.html',
  styleUrl: './toast.css',
})
export class Toast {
  @Input() message: string = '';
  @Input() show: boolean = false;

  hideToast() {
    this.show = false;
  }
}
