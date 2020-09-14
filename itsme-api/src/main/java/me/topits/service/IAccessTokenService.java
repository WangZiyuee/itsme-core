package me.topits.service;

import me.topits.service.model.AccessTokenCheckModel;

import java.time.LocalDateTime;

/**
 * @author QingKe
 * @date 2020-09-14 17:21
 **/
public interface IAccessTokenService {

    /**
     * 创建accessToken
     *
     * @param userId  userId
     * @param timeout timeout
     * @return accessToken
     */
    String createAccessToken(Long userId, LocalDateTime timeout);

    /**
     * 校验accessToken
     *
     * @param accessToken accessToken
     * @return 校验accessToken结果
     */
    AccessTokenCheckModel checkAccessToken(String accessToken);

    /**
     * 移除accessToken
     *
     * @param userId userId
     */
    void removeAccessToken(Long userId);
}
