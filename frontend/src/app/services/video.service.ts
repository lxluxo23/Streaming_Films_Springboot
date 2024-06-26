import { Injectable } from '@angular/core';
import axios from 'axios';
import { environment } from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class VideoService {

  constructor() { }

  getVideos = async () => {
    try {
        return (await axios.get(`${environment.apiUrl}api/videos`)).data;
    } catch (error) {
        console.error('Error al obtener los videos:', error);
        throw error;
    }
};
}
