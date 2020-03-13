package com.example.demo.provider;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.AccessTokenDto;
import com.example.demo.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 接收github传递的code 再次向github发送请求
 */
@Component
public class GithutProvider {

    public String getAccessToken(AccessTokenDto accessTokenDto){
       MediaType mediaType  = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDto),mediaType);
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                System.out.print(response.body().string());
                String splitstr = response.body().string().split("&")[0];
                String split = splitstr.split("=")[1];

                return split;

        } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
    }

    public GitHubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?acees_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GitHubUser gitHubUser = JSON.parseObject(string, GitHubUser.class);
            System.out.println(gitHubUser.getName());
            return gitHubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
