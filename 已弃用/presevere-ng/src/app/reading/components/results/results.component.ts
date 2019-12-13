import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';

@Component({
  selector: 'app-results',
  templateUrl: './results.component.html',
  styleUrls: ['./results.component.css']
})
export class ResultsComponent implements OnInit {

  constructor(private route: ActivatedRoute) { }

  year: string;
  month: string;
  get archive(): string {
    return this.year + (this.month ? ` / ${this.month}` : '');
  }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.year = params.get('year');
      this.month = params.get('month');
    });
  }
}
