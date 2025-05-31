package com.example.rentvideo.service;

import com.example.rentvideo.dto.VideoDto;
import com.example.rentvideo.exception.ResourceNotFoundException;
import com.example.rentvideo.model.Video;
import com.example.rentvideo.repository.VideoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoService {

    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public List<VideoDto> getAllVideos() {
        return videoRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public VideoDto getVideoById(Long id) {
        return videoRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found with id: " + id));
    }

    public VideoDto createVideo(VideoDto videoDto) {
        Video video = convertToEntity(videoDto);
        Video savedVideo = videoRepository.save(video);
        return convertToDto(savedVideo);
    }

    public VideoDto updateVideo(Long id, VideoDto videoDto) {
        Video existingVideo = videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found with id: " + id));
        
        existingVideo.setTitle(videoDto.getTitle());
        existingVideo.setDirector(videoDto.getDirector());
        existingVideo.setGenre(videoDto.getGenre());
        existingVideo.setAvailable(videoDto.isAvailable());
        
        Video updatedVideo = videoRepository.save(existingVideo);
        return convertToDto(updatedVideo);
    }

    public void deleteVideo(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found with id: " + id));
        
        videoRepository.delete(video);
    }

    public Video findById(Long id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found with id: " + id));
    }

    private VideoDto convertToDto(Video video) {
        return VideoDto.builder()
                .id(video.getId())
                .title(video.getTitle())
                .director(video.getDirector())
                .genre(video.getGenre())
                .available(video.isAvailable())
                .build();
    }

    private Video convertToEntity(VideoDto videoDto) {
        return Video.builder()
                .id(videoDto.getId())
                .title(videoDto.getTitle())
                .director(videoDto.getDirector())
                .genre(videoDto.getGenre())
                .available(videoDto.isAvailable())
                .build();
    }
}
