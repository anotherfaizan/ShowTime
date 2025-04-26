package com.ShowTime.ShowTimeApp.mapper;

import com.ShowTime.ShowTimeApp.entities.Video;
import com.ShowTime.ShowTimeApp.modal.VideoDto;
import org.springframework.beans.BeanUtils;

public class VideoMapper {

    public static Video convertVideoDtoToVideo(VideoDto videoDto){
        Video video = new Video();
        BeanUtils.copyProperties(videoDto, video);
        return video;
    }

    public static VideoDto convertVideoToVideoDto(Video video){
        VideoDto videoDto = new VideoDto();
        BeanUtils.copyProperties(video, videoDto);
        return videoDto;
    }
}
