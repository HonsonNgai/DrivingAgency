package com.beautifulsoup.driving.controller;

import com.beautifulsoup.driving.common.ResponseResult;
import com.beautifulsoup.driving.dto.AgentDto;
import com.beautifulsoup.driving.service.AgentService;
import com.beautifulsoup.driving.vo.AgentBaseInfoVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(tags = "/agent",description = "代理操作",protocols = "http")
@Controller
@RequestMapping(value = "/agent")
public class AgentController {

    @Autowired
    private AgentService agentService;

    @PostMapping(value = "/login",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseResult<AgentBaseInfoVo> adminLogin(@RequestParam("username")String username,
                                                      @RequestParam("password")String password, HttpServletResponse response){

        AgentBaseInfoVo baseInfoVo = agentService.login(username, password,response);

        if (baseInfoVo != null) {
            return ResponseResult.createBySuccess("用户登录成功",baseInfoVo);
        }

        return ResponseResult.createByError("用户登录失败");
    }

    @PostMapping(value = "/logout",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseResult<AgentBaseInfoVo> logout(@RequestHeader(value = "token",required = true)String token){
        AgentBaseInfoVo baseInfoVo = agentService.logout(token);
        if (baseInfoVo != null) {
            return ResponseResult.createBySuccess("登出成功",baseInfoVo);
        }
        return ResponseResult.createByError("登出失败");
    }

    @PostMapping(value = "/password/reset",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseResult<AgentBaseInfoVo> passwordReset(@RequestHeader(value = "token",required = true)String token
    ,@RequestParam("username")String username,@RequestParam("newPassword")String newPassword,@RequestParam("password")String password
    ,@RequestParam("email")String email){
        AgentBaseInfoVo baseInfoVo = agentService.resetPassword(token,username,newPassword,password,email);
        if (baseInfoVo != null) {
            return ResponseResult.createBySuccess("密码重置成功",baseInfoVo);
        }
        return ResponseResult.createByError("密码重置失败");
    }

    @PostMapping(value = "/add",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseResult<AgentBaseInfoVo> addNewAgent(@Valid @RequestBody AgentDto agentDto, BindingResult result){
        AgentBaseInfoVo agentBaseInfoVo = agentService.addNewAgent(agentDto, result);
        if (agentBaseInfoVo != null) {
            return ResponseResult.createBySuccess("添加代理成功",agentBaseInfoVo);
        }
        return ResponseResult.createByError("代理添加失败");
    }

}
