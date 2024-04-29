import { Router } from '@angular/router';
import { Video } from '../../classes/video';
import { VideoService } from './../../services/video.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  videos: Video[] = [];

  constructor(
    private VideoService: VideoService,
    private router: Router
  ) {

  }
  async ngOnInit() {
    this.videos = await this.VideoService.getVideos();

  }

  printInfo(video: Video) {
    this.router.navigate(['/reproductor', video.id]);
  }
}
