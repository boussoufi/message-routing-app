import { Component } from '@angular/core';

@Component({
  selector: 'app-header',
  standalone: false,
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  navLinks = [
    { path: '/messages', label: 'Messages' },
    { path: '/partners', label: 'Partenaires' }
  ];
}
