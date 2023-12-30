package io.chou401.framework.service;


import io.chou401.common.vo.login.LoginVo;

/**
 * {@code @author}  chou401
 * {@code @date} 2022/7/12
 **/
public interface LoginRedisService {

    /**
     * 获取登录用户token的redis key
     *
     * @param token
     * @return
     * @throws Exception
     */
    String getLoginRedisKey(String token) throws Exception;

    /**
     * 设置登录用户信息到redis
     *
     * @param token
     * @param loginVo
     * @throws Exception
     */
    void setLoginVo(String token, LoginVo loginVo) throws Exception;

    /**
     * 获取redis中的登录用户信息
     *
     * @param token
     * @return
     * @throws Exception
     */
    LoginVo getLoginVo(String token) throws Exception;

    /**
     * 删除redis中的登录用户信息
     *
     * @param token
     * @throws Exception
     */
    void deleteLoginVo(String token) throws Exception;

    /**
     * 刷新token
     *
     * @throws Exception
     */
    void refreshToken() throws Exception;

    /**
     * 通过用户token删除当前用户之前的所有的redis登录信息
     *
     * @param token
     * @throws Exception
     */
    void deleteLoginInfoByToken(String token) throws Exception;

}
