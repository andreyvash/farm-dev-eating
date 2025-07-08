import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from "./header/header.component";
import { MainContentComponent } from "./main-content/main-content.component";
import { FooterComponent } from "./footer/footer.component";
import { HomeComponent } from "./home/home.component";

interface FoodEntry {
  id: number;
  name: string;
  description?: string;
  mealType: string;
  time: string;
  naturalness: 'High' | 'Medium' | 'Low';
}

interface MacroData {
  carbs: { percentage: number; grams: number };
  protein: { percentage: number; grams: number };
  fats: { percentage: number; grams: number };
}

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, HeaderComponent, MainContentComponent, FooterComponent, HomeComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  // User data
  userName = 'João Silva';

  // Current date
  currentDate = new Date();

  // Naturalness score
  naturalnessScore = 85;

  // Daily calorie goal
  dailyCalorieGoal = 2000;

  // Macro data
  macroData: MacroData = {
    carbs: { percentage: 55, grams: 275 },
    protein: { percentage: 25, grams: 125 },
    fats: { percentage: 20, grams: 50 }
  };

  // Food diary entries
  foodEntries: FoodEntry[] = [
    {
      id: 1,
      name: 'Apple',
      mealType: 'Snack',
      time: '10:30 AM',
      naturalness: 'High'
    },
    {
      id: 2,
      name: 'Grilled Chicken Salad',
      description: 'Lettuce, tomato, grilled chicken breast, olive oil',
      mealType: 'Lunch',
      time: '01:15 PM',
      naturalness: 'High'
    },
    {
      id: 3,
      name: 'Pasta with Tomato Sauce & Processed Meatballs',
      description: 'Refined pasta, canned sauce, processed meatballs with preservatives',
      mealType: 'Dinner',
      time: '07:00 PM',
      naturalness: 'Low'
    },
    {
      id: 4,
      name: 'Banana',
      mealType: 'Snack',
      time: '09:00 PM',
      naturalness: 'High'
    }
  ];

  // Methods
  navigateDate(direction: 'prev' | 'next') {
    const newDate = new Date(this.currentDate);
    if (direction === 'prev') {
      newDate.setDate(newDate.getDate() - 1);
    } else {
      newDate.setDate(newDate.getDate() + 1);
    }
    this.currentDate = newDate;
  }

  logNewFood() {
    // This would typically open a modal or navigate to a form
    console.log('Log new food clicked');
  }

  editFood(id: number) {
    console.log('Edit food entry:', id);
  }

  deleteFood(id: number) {
    this.foodEntries = this.foodEntries.filter(entry => entry.id !== id);
  }

  logout() {
    console.log('Logout clicked');
  }

  // Utility methods
  formatDate(date: Date): string {
    return date.toLocaleDateString('en-US', {
      day: '2-digit',
      month: 'short',
      year: 'numeric'
    });
  }

  getNaturalnessScoreMessage(): string {
    if (this.naturalnessScore >= 80) {
      return 'Excellent! Keep up the great work.';
    } else if (this.naturalnessScore >= 60) {
      return 'Good job! Try to include more natural foods.';
    } else {
      return 'Consider adding more natural, unprocessed foods.';
    }
  }
}
