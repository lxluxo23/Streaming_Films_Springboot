import { VideoService } from './../../services/video.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Video } from '../../classes/video';


@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrl: './player.component.scss'
})
export class PlayerComponent implements OnInit {
  public videoID: string;
  public video: Video

  constructor(
    private route: ActivatedRoute,
    private VideoService: VideoService
  ) { }


  async ngOnInit() {
    this.videoID = this.route.snapshot.paramMap.get('id');
    await this.getVideo()
  }

  async getVideo() {
    this.video = await this.VideoService.getVideo(parseInt(this.videoID));
  }
}
