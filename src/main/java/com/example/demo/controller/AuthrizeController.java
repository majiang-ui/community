package com.example.demo.controller;

import com.example.demo.dto.AccessTokenDto;
import com.example.demo.dto.GitHubUser;
import com.example.demo.provider.GithutProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthrizeController {
    @Autowired
    private GithutProvider githutProvider;
    @Value("${github.cliend.id}")
    private String clientId;
    @Value("${github.cliend.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")  String code,
                           @RequestParam(name="state") String state){
        final AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setState(state);
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        String accessToken = githutProvider.getAccessToken(accessTokenDto);
        GitHubUser user = githutProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
