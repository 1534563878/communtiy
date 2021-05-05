package com.zyq.community.provider;

import com.alibaba.fastjson.JSON;
import com.zyq.community.dto.AccessTokenDTO;
import com.zyq.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
 /**
  * @author zyq
  * @description  GithubProvider方法就是来获取token 的
  *@create 2021/5/4
  **/
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
        }
        return null;
    }


    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization", "token " + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (Exception e) {
        }
        return null;
    }

}

   /* public String getAccessToken(AccessTokenDTO accessTokenDTO){
        //POST请求
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
            //因为要用accessTokenDTO 中给参数换token 所以要以JSON 的方式传入
            RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
            Request request = new Request.Builder()
                    //设置此 HttpRequest的请求 URI
                    .url("https://github.com/login/oauth/access_token")
                    //将此构建器的请求方法设置为POST，并将其请求主体发布者设置为给定值。
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                //是来获取一个token 的
                String string = response.body().string();
                String[] split = string.split("&");
                String tokenstr= split[0];
                String token = tokenstr.split("=")[1];
                System.out.println("token==============="+token);
                return token;
            }catch (IOException e){
            }
            return null;
    }
    *//**
     * @author zyq
     * @description  通过token获取用户信息
     * @create 2021/5/4
     **//*
    public GithubUser getUser(String accessToken){
        //GET请求
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e){
        }
        return null;
    }
}
*/