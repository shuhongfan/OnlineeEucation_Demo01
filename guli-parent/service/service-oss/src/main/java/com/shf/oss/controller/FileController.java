package com.shf.oss.controller;

import com.shf.commonutils.R;
import com.shf.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api("阿里云文件管理")
@RestController
@RequestMapping("/oss/admin/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation("文件上传")
    @PostMapping("upload")
    public R upload(@ApiParam(name = "file", value = "文件", required = true)
                    @RequestParam("file") MultipartFile file,
                    @ApiParam(name = "host", value = "文件上传路径", required = false)
                    @RequestParam("host") String host
    ) {
        String uploadUrl = fileService.upload(file,host);
        return R.ok().message("文件上传成功").data("url", uploadUrl);
    }

}
