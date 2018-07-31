package com.yxb.cms.controller.indicator;

import com.yxb.cms.architect.annotation.SystemControllerLog;
import com.yxb.cms.architect.constant.BussinessCode;
import com.yxb.cms.architect.utils.BussinessMsgUtil;
import com.yxb.cms.controller.BasicController;
import com.yxb.cms.domain.bo.BussinessMsg;
import com.yxb.cms.model.Indicator;
import com.yxb.cms.service.IndicatorService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 指标列表及增删改查操作 Controller
 * @author zd
 * @date 2018/07/18
 *
 */
@RestController
@RequestMapping("quotaList")
public class IndicatorController extends BasicController {
    @RequestMapping("/getquotaList")
    @ResponseBody
    @SystemControllerLog(description="指标列表获取")
    public BussinessMsg quotaList( @RequestBody Map<String,String> body) throws IOException {
        log.info("请求获取指标列表");
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            log.info("用户未登录");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_NOT_LOGIN);
        } else {
            IndicatorService indicatorService = new IndicatorService();
            String searchTerm = null;
            searchTerm = body.get("info");
            List<Indicator> indicatorsList = indicatorService.getIndicatorList(searchTerm);
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_OP_SUCCESS,indicatorsList);
        }
    }

    @RequestMapping("/addquotaList")
    @ResponseBody
    @SystemControllerLog(description="添加指标")
    public BussinessMsg addquotaList( @RequestBody Map<String,Object> body) throws IOException {
        log.info("请求添加指标");
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            log.info("用户未登录");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_NOT_LOGIN);
        } else {
            IndicatorService indicatorService = new IndicatorService();
            indicatorService.addIndicator(body);
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_OP_SUCCESS,new int[0]);
        }
    }
    @RequestMapping("/savequotaContent")
    @ResponseBody
    @SystemControllerLog(description="修改指标")
    public BussinessMsg modifyQuotaList( @RequestBody Map<String,Object> body) throws IOException {
        log.info("请求修改指标");
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            log.info("用户未登录");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_NOT_LOGIN);
        } else {
            IndicatorService indicatorService = new IndicatorService();
            indicatorService.addIndicator(body);
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_OP_SUCCESS,new int[0]);
        }
    }

    @RequestMapping("/getquotaContent")
    @ResponseBody
    @SystemControllerLog(description="获取一个指标详情")
    public BussinessMsg getIndicator( @RequestBody Map<String,String> body) throws IOException {
        log.info("请求指标详情");
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            log.info("用户未登录");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_NOT_LOGIN);
        } else {
            IndicatorService indicatorService = new IndicatorService();
            String key = body.get("key");
            Indicator indicator = indicatorService.getIndicator(key);
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_OP_SUCCESS,indicator);
        }
    }
    @RequestMapping("/deletequotaList")
    @ResponseBody
    @SystemControllerLog(description="删除指标")
    public BussinessMsg deleteIndicator( @RequestBody Map<String,String> body) throws IOException {
        log.info("请求删除指标");
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            log.info("用户未登录");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_NOT_LOGIN);
        } else {
            IndicatorService indicatorService = new IndicatorService();
            indicatorService.deleteIndicator(body);
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_OP_SUCCESS,new int[0]);
        }
    }

    @RequestMapping("/quotaTagList")
    @ResponseBody
    @SystemControllerLog(description="获取指标标签列表")
    public BussinessMsg tagsIndicator() throws IOException {
        log.info("请求指标标签列表");
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            log.info("用户未登录");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_NOT_LOGIN);
        } else {
            IndicatorService indicatorService = new IndicatorService();
            List<String> tags = indicatorService.getTagList();
            Collections.sort(tags);
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_OP_SUCCESS,tags);
        }
    }
}
