package com.yxb.cms.controller;

import com.yxb.cms.architect.annotation.SystemControllerLog;
import com.yxb.cms.architect.constant.BussinessCode;
import com.yxb.cms.architect.utils.BussinessMsgUtil;
import com.yxb.cms.domain.bo.BussinessMsg;
import com.yxb.cms.model.APInterface;
import com.yxb.cms.service.InterfaceService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接口列表及增删改查操作 Controller
 * @author zd
 * @date 2018/07/26
 *
 */

@RestController
@RequestMapping("apiList")
public class InterfaceController extends  BasicController {

    @RequestMapping("/addapiList")
    @ResponseBody
    @SystemControllerLog(description="添加接口")
    public BussinessMsg addInterface(@RequestBody APInterface api) throws IOException, JSONException {
        log.info("请求添加接口");
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            log.info("用户未登录");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_NOT_LOGIN);
        } else {
            InterfaceService interfaceService = new InterfaceService();
            String icon = "capital";
            if(interfaceService.addAPI(api.getKey(),api.getProjectId(),icon,api.getTitle()))
                return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_OP_SUCCESS,new int[0]);
            else
                return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_DB_ERROR);
        }
    }


    @RequestMapping("/saveapiContent")
    @ResponseBody
    @SystemControllerLog(description="保存接口详情")
    public BussinessMsg saveInterface(@RequestBody APInterface api) throws IOException {
        log.info("请求保存接口详情");
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            log.info("用户未登录");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_NOT_LOGIN);
        } else {
            InterfaceService interfaceService = new InterfaceService();
            if(interfaceService.saveAPI(api))
                return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_OP_SUCCESS,new int[0]);
            else
                return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_DB_ERROR);
        }
    }

    @RequestMapping("/deleteapiList")
    @ResponseBody
    @SystemControllerLog(description="删除接口及其参数")
    public BussinessMsg deleteInterface(@RequestBody Map<String,String> body) throws IOException {
        log.info("请求删除接口");
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            log.info("用户未登录");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_NOT_LOGIN);
        } else {
            InterfaceService interfaceService = new InterfaceService();

            if(interfaceService.deleteAPI(body.get("key")))
                return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_OP_SUCCESS,new int[0]);
            else
                return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_DB_ERROR);
        }
    }
    @RequestMapping("/getapiList")
    @ResponseBody
    @SystemControllerLog(description="获得项目下接口列表")
    public BussinessMsg getInterfaceList(@RequestBody Map<String,Object> body) throws IOException {
        log.info("请求删除接口");
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            log.info("用户未登录");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_NOT_LOGIN);
        } else {
            InterfaceService interfaceService = new InterfaceService();
            List<Map<String,Object>> rows = interfaceService.getAPIList((String)body.get("projectId"));
            Map<String,Object> ret = new HashMap<>();
            ret.put("projectId",body.get("projectId"));
            ret.put("rows",rows);
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_OP_SUCCESS,ret);
        }
    }
    @RequestMapping("/getapiContent")
    @ResponseBody
    @SystemControllerLog(description="获取接口详情")
    public BussinessMsg getInterfaceDetail(@RequestBody Map<String,String> body) throws IOException {
        log.info("请求接口详情");
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            log.info("用户未登录");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_NOT_LOGIN);
        } else {
            InterfaceService interfaceService = new InterfaceService();
            APInterface res = interfaceService.getAPI(body.get("apiId"));
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_OP_SUCCESS,res);
        }
    }
}

