package com.shf.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
     * 上传文件到阿里云
     *
     * @param file
     * @param host
     * @return
     */
    String upload(MultipartFile file, String host);
}
