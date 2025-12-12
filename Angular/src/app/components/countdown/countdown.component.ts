import { Component, OnDestroy, OnInit } from '@angular/core';

@Component({
  selector: 'app-countdown',
  imports: [],
  templateUrl: './countdown.component.html',
  styleUrl: './countdown.component.css'
})
export class CountdownComponent implements OnInit, OnDestroy{
  targetDate = new Date('December 25, 2025 00:00:00').getTime();

  days = 0;
  hours = 0;
  minutes = 0;
  seconds = 0;

  private interval: any;

  ngOnInit(): void {
    this.interval = setInterval(() => {
      this.updateCountdown();
    }, 1000);
  }

  updateCountdown() {
    const now = new Date().getTime();
    const distance = this.targetDate - now;

    if (distance <= 0) {
      clearInterval(this.interval);
      this.days = this.hours = this.minutes = this.seconds = 0;
      return;
    }

    this.days = Math.floor(distance / (1000 * 60 * 60 * 24));
    this.hours = Math.floor(
      (distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
    );
    this.minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
    this.seconds = Math.floor((distance % (1000 * 60)) / 1000);
  }

  ngOnDestroy(): void {
    clearInterval(this.interval);
  }
}
