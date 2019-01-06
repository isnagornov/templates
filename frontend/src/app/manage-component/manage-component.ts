import {Component, OnInit} from '@angular/core';
import {ManageService} from "./manage.service";
import {Entity} from "../model/entity";

@Component({
  selector: 'manage-comp',
  templateUrl: './manage-component.html',
  styleUrls: ['./manage-component.less']
})
export class ManageComponent implements OnInit {
  entities: Entity[];

  constructor(private manageService: ManageService) {
  }

  ngOnInit() {
    this.manageService.getList().subscribe((response: Entity[]) => {
      this.entities = response;
    });
  }
}
