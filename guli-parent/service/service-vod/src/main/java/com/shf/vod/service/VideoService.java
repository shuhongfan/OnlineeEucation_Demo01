package com.shf.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {
    String uploadVideo(MultipartFile file);

    /**
     * 删除视频
     * @param videoId
     */
    void removeVideo(String videoId);

    /**
     * 批量删除视频
     * @param videoList
     */
    void removeVideoList(List<String> videoList);
}
