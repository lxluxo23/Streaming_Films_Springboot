import { VideoService } from './../../services/video.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {

  constructor(private VideoService: VideoService){

  }
  async ngOnInit() {
    let res = await this.VideoService.getVideos();
    console.log(res);
  }

}
