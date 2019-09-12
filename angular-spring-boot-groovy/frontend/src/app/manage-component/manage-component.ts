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
  currentEntity: Entity;
  updateMode: boolean;

  constructor(private manageService: ManageService) {
  }

  ngOnInit() {
    this.updateMode = false;
    this.getEntitiesList();
  }

  private getEntitiesList() {
    this.currentEntity = new Entity();

    this.manageService.getList().subscribe((entitiesList: Entity[]) => {
      this.entities = entitiesList;
    });
  }

  add() {
    this.manageService.add(this.currentEntity).subscribe(() => {
      this.getEntitiesList();
    }, () => {
      console.error("error while saving entity");
    });
  }

  update() {
    this.manageService.update(this.currentEntity).subscribe(() => {
      this.updateMode = false;
      this.currentEntity = new Entity();
    }, () => {
      console.error("error while updating entity");
    });
  }

  delete(id: number) {
    this.manageService.delete(id).subscribe(() => {
      this.getEntitiesList();
    }, () => {
      console.error("error while delete entity with id " + id);
    });
  }

  select(entity: Entity) {
    this.updateMode = true;
    this.currentEntity = entity;
  }


}
