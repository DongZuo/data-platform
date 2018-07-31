package com.yxb.cms.controller;

import com.yxb.cms.architect.annotation.SystemControllerLog;
import com.yxb.cms.architect.constant.BussinessCode;
import com.yxb.cms.architect.utils.BussinessMsgUtil;
import com.yxb.cms.domain.bo.BussinessMsg;
import com.yxb.cms.model.Project;
import com.yxb.cms.service.InterfaceService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目列表及增删改查操作 Controller
 * @author zd
 * @date 2018/07/25
 *
 */

@RestController
@RequestMapping("projectList")
public class ProjectController extends  BasicController{
    @RequestMapping("/getProjectList")
    @ResponseBody
    @SystemControllerLog(description="项目列表获取")
    public BussinessMsg projectList() throws IOException {
        log.info("请求获取项目列表");
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            log.info("用户未登录");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_NOT_LOGIN);
        } else {
            InterfaceService interfaceService = new InterfaceService();

            List<Project> projectList = interfaceService.getProjectList();
            Map<String,Object> res = new HashMap<>();
            res.put("total", projectList.size());
            res.put("rows",projectList);
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_OP_SUCCESS,res);
        }
    }

    @RequestMapping("/addProject")
    @ResponseBody
    @SystemControllerLog(description="添加项目")
    public BussinessMsg addProject( @RequestBody Map<String,Object> body) throws IOException {
        log.info("请求添加项目");
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            log.info("用户未登录");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_NOT_LOGIN);
        } else {
            InterfaceService interfaceService = new InterfaceService();
            String username = getCurrentLoginName();
            if(interfaceService.addProject(body,username))
                return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_OP_SUCCESS,new int[0]);
            else
                return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_DB_ERROR);
        }
    }


    @RequestMapping("/deleteProject")
    @ResponseBody
    @SystemControllerLog(description="删除项目")
    public BussinessMsg deleteProject( @RequestBody Map<String,Object> body) throws IOException {
        log.info("请求删除项目");
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            log.info("用户未登录");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_NOT_LOGIN);
        } else {
            InterfaceService interfaceService = new InterfaceService();
            String key = (String)body.get("key");
            interfaceService.deleteProject(key);
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_OP_SUCCESS,new int[0]);
        }
    }

    @RequestMapping("/editProject")
    @ResponseBody
    @SystemControllerLog(description="修改项目")
    public BussinessMsg editProject( @RequestBody Map<String,Object> body) throws IOException {
        log.info("请求添加项目");
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            log.info("用户未登录");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_NOT_LOGIN);
        } else {
            InterfaceService interfaceService = new InterfaceService();
            if(interfaceService.editProject(body))
                return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_OP_SUCCESS,new int[0]);
            else
                return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_DB_ERROR);
        }
    }

}
