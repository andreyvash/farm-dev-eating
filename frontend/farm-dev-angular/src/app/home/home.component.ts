import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  onGetStarted(): void {
    // Navegar para página de registro de refeições ou cadastro
    console.log('Começar Agora clicado - redirecionar para registro de refeições');
  }

  onLearnMore(): void {
    // Navegar para página sobre ou rolar para as funcionalidades
    console.log('Saiba Mais clicado - mostrar mais informações');
  }

  onStartJourney(): void {
    // Navegar para cadastro ou processo de onboarding
    console.log('Começar Minha Jornada clicado - iniciar processo de cadastro');
  }
}
