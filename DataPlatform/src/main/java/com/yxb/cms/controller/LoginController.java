package com.yxb.cms.controller;

import com.yxb.cms.architect.annotation.SystemControllerLog;
import com.yxb.cms.architect.constant.BussinessCode;
import com.yxb.cms.architect.constant.Constants;
import com.yxb.cms.architect.utils.BussinessMsgUtil;
import com.yxb.cms.dao.DpRoleMapper;
import com.yxb.cms.domain.bo.BussinessMsg;
import com.yxb.cms.domain.vo.User;
import com.yxb.cms.model.DpRole;
import com.yxb.cms.model.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 登陆Controller
 * @author zd
 * @date 2018/06/06
 *
 */
@RestController
@RequestMapping("login")
public class LoginController  extends BasicController{
//    protected Logger log = LogManager.getLogger(getClass());
    /**
     *  登录验证处理
     * @param request 请求中包含  userName,password
     * @return
     */
    @RequestMapping("/loginCheck")
    @ResponseBody
    @SystemControllerLog(description="用户登陆")
    public BussinessMsg loginCheck(HttpServletRequest request,@RequestBody Map<String,String> body){
        log.info("登陆验证处理开始");
        String username = body.get("userName");
        String password = body.get("password");
        long start = System.currentTimeMillis();
        try {
            //1.用户名不能为空
            if (StringUtils.isEmpty(username)) {
                log.error("登陆验证失败,原因:用户名不能为空");
                return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_LOGIN_NAME_NULL);
            }
            //2.密码不能为空
            if (StringUtils.isEmpty(password)) {
                log.error("登陆验证失败,原因:密码不能为空");
                return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_LOGIN_PASS_NULL);
            }

            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true);
            Subject currentUser = SecurityUtils.getSubject();

            currentUser.login(token);
            if (currentUser.isAuthenticated()) {
                request.getSession().setAttribute(Constants.SESSION_KEY_LOGIN_NAME,getCurrentUser());
                return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_LOGIN_SUCCESS);
            }
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_LOGIN_FAIL);
        } catch (IncorrectCredentialsException ice) {
            log.error("登陆验证失败,原因:用户名或密码不匹配");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_LOGIN_FAIL);
        }catch (AccountException e){
            log.error("登陆验证失败,原因:用户名或密码不匹配");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_LOGIN_FAIL);
        }catch (Exception e) {
            log.error("登陆验证失败,原因:系统登陆异常", e);
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_LOGIN_ERROR);
        } finally {
            log.info("登陆验证处理结束,用时" + (System.currentTimeMillis() - start) + "毫秒");
        }

    }

    //for query of dpRole
    @Autowired
    private DpRoleMapper dpRoleMapper  ;
    /**
     *  获取用户信息/权限
     * @param request 请求中包含  userName,password
     * @return
     */
    @RequestMapping("/getLoginUser")
    @ResponseBody
    @SystemControllerLog(description="用户信息/权限列表")
    public BussinessMsg getLoginUser(HttpServletRequest request){
        log.info("请求列出登陆人权限");
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated()){
            log.info("用户未登录");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_NOT_LOGIN);
        }else{

            User user = (User) currentUser.getSession().getAttribute("LOGIN_NAME");
            String username = user.getUserLoginName();

            //get user roles by roleIds
            String [] roleIds = user.getRoleIds().split(",");
            List<DpRole> roles = new ArrayList<>();

            List<DpRole> channels = new ArrayList<>();
            List<DpRole> metadatas = new ArrayList<>();

            int idx = 0;
            for (String roleId: roleIds) {
                //select by roleId
                DpRole condition = DpRole.QueryBuild().fetchAll().roleId(roleId).build();
                DpRole role = dpRoleMapper.queryDpRoleLimit1(condition);

                int roleid = Integer.parseInt(role.getRoleId());
                if(roleid>=31&&roleid<=34){
                    DpRole con = DpRole.QueryBuild().fetchAll().roleId(role.getRoleId()).build();
                    DpRole channel = dpRoleMapper.queryDpRoleLimit1(con);
                    channels.add(channel);
                    continue;
                }
                if(roleid>=71&&roleid<=73){
                    DpRole con = DpRole.QueryBuild().fetchAll().roleId(role.getRoleId()).build();
                    DpRole metadata = dpRoleMapper.queryDpRoleLimit1(con);
                    metadatas.add(metadata);
                    continue;
                }
                roles.add(role);
            }
            if(channels.size()>0){
                DpRole [] menu = new DpRole[channels.size()];
                //roleId=3   <->  渠道管理
                DpRole con = DpRole.QueryBuild().fetchAll().roleId("3").build();
                DpRole channel = dpRoleMapper.queryDpRoleLimit1(con);
                for(int i =0; i<channels.size();i++){
                    menu[i] = channels.get(i);
                }
                channel.setMenu(menu);
                roles.add(channel);
            }
            if(metadatas.size()>0){
                DpRole [] menu = new DpRole[metadatas.size()];
                //roleid=7   <->  信息录入管理
                DpRole con = DpRole.QueryBuild().fetchAll().roleId("7").build();
                DpRole metadata = dpRoleMapper.queryDpRoleLimit1(con);
                for(int i =0; i<metadatas.size();i++){
                    menu[i] = metadatas.get(i);
                }
                metadata.setMenu(menu);
                roles.add(metadata);
            }
            roles.sort(Comparator.comparingInt((DpRole d) -> (Integer.parseInt(d.getRoleId()))));
            UserRole res = new UserRole(user.getUserId(),username,roles);
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_GETLOGIN_SUCCESS,res);
        }
    }
    @RequestMapping("/clearLogin")
    @ResponseBody
    @SystemControllerLog(description="用户退出")
    public BussinessMsg logoutUser(HttpServletRequest request){
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_SUCCESS,null);
    }
}