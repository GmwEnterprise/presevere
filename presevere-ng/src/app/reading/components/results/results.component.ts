import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';

@Component({
  selector: 'app-results',
  templateUrl: './results.component.html',
  styleUrls: ['./results.component.css']
})
export class ResultsComponent implements OnInit {

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    /**
     * snapshot的问题在于，同组件路由切换无法更新路由参数信息
     * /home/archives -> /home/2019/11 有信息
     * /home/2019/11  -> /home/2019/9 无信息
     */
    const {
      year, month
    } = this.route.snapshot.params;
    console.log(`${year || 'XXXX'}-${month || 'XX'}`);
  }

}
