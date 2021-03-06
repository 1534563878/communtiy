package com.zyq.community.contorller;

import com.zyq.community.bean.User;
import com.zyq.community.dto.AccessTokenDTO;
import com.zyq.community.dto.GithubUser;
import com.zyq.community.mapper.UserMapper;
import com.zyq.community.provider.GithubProvider;
import com.zyq.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
/**
 * @author zyq
 * @description
 * @create 2021/5/4
 **/
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @Autowired
    private UserService userService;
    @RequestMapping("/callback")
    public String callBack(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "state") String state,
            HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);;
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null &&githubUser.getId()!=null) {
            User user = new User();
            user.setName(githubUser.getName());
            user.setAccountId(githubUser.getId().toString());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.createOrUpdate(user);
            //??????cookie
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else{
            //????????????
            return "redirect:/";
        }
    }
    //??????????????????session ???cookie ????????????
    @GetMapping("/logout")
    public String logOut(HttpServletRequest request,
                         HttpServletResponse  response){
        request.getSession().removeAttribute("user");
         Cookie cookie = new Cookie("token",null);
         cookie.setMaxAge(0);
         response.addCookie(cookie);
        return "redirect:/";
    }
}
