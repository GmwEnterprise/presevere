import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-page-not-fount',
  templateUrl: './page-not-fount.component.html',
  styleUrls: ['./page-not-fount.component.css']
})
export class PageNotFountComponent implements OnInit {
  constructor(
    // 路由服务
    private router: Router,
    // 当前激活路由服务
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    console.log(this.router);
    console.log(this.activatedRoute);
  }
}
