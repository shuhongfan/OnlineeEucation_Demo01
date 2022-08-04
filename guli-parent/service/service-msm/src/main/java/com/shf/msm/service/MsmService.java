package com.shf.msm.service;

import java.util.HashMap;

public interface MsmService {
    /**
     * 发送短信
     * @param phone
     * @param template
     * @param param
     * @return
     */
    boolean send(String phone, String template, HashMap<String, Object> param);
}
