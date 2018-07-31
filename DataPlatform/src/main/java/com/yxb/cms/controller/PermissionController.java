package com.yxb.cms.controller;

import com.yxb.cms.architect.annotation.SystemControllerLog;
import com.yxb.cms.architect.constant.BussinessCode;
import com.yxb.cms.architect.utils.BussinessMsgUtil;
import com.yxb.cms.dao.DpRoleMapper;
import com.yxb.cms.dao.UserMapper;
import com.yxb.cms.domain.bo.BussinessMsg;
import com.yxb.cms.domain.vo.User;
import com.yxb.cms.model.DpRole;
import com.yxb.cms.model.UserPermission;
import com.yxb.cms.model.UserRole;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 登陆Controller
 * @author lyx
 * @date 2018/06/25
 *
 */
@RestController
@RequestMapping("rrd-report")
public class PermissionController  extends BasicController{
//    protected Logger log = LogManager.getLogger(getClass());
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private DpRoleMapper dpRoleMapper;
    /**
     *  用户查询
     * @param request 空
     * @return
     */
    @RequestMapping("/getBiAuth")
    @ResponseBody
    @SystemControllerLog(description="用户查询")
    public BussinessMsg getBiAuth(HttpServletRequest request){
    	log.info("请求操作数据库");
        Subject currentUser = SecurityUtils.getSubject();
        List<UserPermission> res = new ArrayList<>();
        if(!currentUser.isAuthenticated()){
            log.info("用户未登录,请求失败");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_NOT_LOGIN);
        }else{
            log.info("请求列出所有用户");
        	List<User> users = userMapper.selectUserList(null);
            for (User user: users) {
                List<String> permissions= new ArrayList<>();
            	String[] roleIds = user.getRoleIds().split(",");
            	for (String roleId: roleIds) {
            		//select by roleId
                    DpRole condition = DpRole.QueryBuild().fetchAll().roleId(roleId).build();
                    DpRole role = dpRoleMapper.queryDpRoleLimit1(condition);
                    if(role!=null)
                        permissions.add(role.getName());
            	}
            	UserPermission up = new UserPermission(user.getUserId(),user.getUserName(),permissions);
            	res.add(up);
            }
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_SUCCESS, res);
        }
    }
    
    /**
     *  用户删除
     * @param request 空
     * @return
     */
    @RequestMapping("/deleteBiAuth")
    @ResponseBody
    @SystemControllerLog(description="用户删除")
    public BussinessMsg deleteBiAuth(HttpServletRequest request, @RequestBody Map<String,Object> body){
    	log.info("请求操作数据库");
        Subject currentUser = SecurityUtils.getSubject();
        List<UserRole> res = new ArrayList<UserRole>();
        if(!currentUser.isAuthenticated()){
            log.info("用户未登录,请求失败");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_NOT_LOGIN);
        }else{
        	userMapper.deleteByPrimaryKey((Long)body.get("key"));
        	return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_SUCCESS, res);
        }
    }
    
    /**
     *  用户修改
     * @param request 空
     * @return
     */
    @RequestMapping("/updateBiAuth")
    @ResponseBody
    @SystemControllerLog(description="用户修改")
    public BussinessMsg updateBiAuth(HttpServletRequest request, @RequestBody Map<String,Object> body){
    	log.info("请求修改用户");
        Subject currentUser = SecurityUtils.getSubject();
        List<UserRole> res = new ArrayList<UserRole>();
        if(!currentUser.isAuthenticated()){
            log.info("用户未登录,请求失败");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_NOT_LOGIN);
        }else{
        	Long userId = (Long) body.get("key");
        	User user = userMapper.selectByPrimaryKey(userId);
        	List<String> Permissions = (List<String>) body.get("permissions");
        	List<Integer> roles = new ArrayList<Integer>();
        	for (String permission : Permissions) {
        		DpRole condition = DpRole.QueryBuild().fetchAll().name(permission).build();
        		DpRole role = dpRoleMapper.queryDpRoleLimit1(condition);
        		roles.add(Integer.valueOf(role.getRoleId()));
        	}
            StringBuilder sb =new StringBuilder();
            for(int num : roles){
                sb.append(num).append(",");
            }
            if(sb.charAt(sb.length()-1)==',')
                sb.setLength(sb.length()-1);
            String roleIds = sb.toString();
        	user.setRoleIds(roleIds);
        	user.setUpdateTime(new Date());
        	userMapper.updateByPrimaryKey(user);
        	return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_SUCCESS, res);
        }
    }
    
    /**
     *  用户新建
     * @param request 空
     * @return
     */
    @RequestMapping("/createBiAuth")
    @ResponseBody
    @SystemControllerLog(description="用户新建")
    public BussinessMsg createBiAuth(HttpServletRequest request, @RequestBody Map<String,Object> body){
    	log.info("请求新建用户");
        Subject currentUser = SecurityUtils.getSubject();
        List<UserRole> res = new ArrayList<UserRole>();

        	String userLoginName = (String) body.get("userName");
        	String userPassword = (String) body.get("password");
        	Long uid = (Long)body.get("key");
        	List<String> Permissions = (List<String>) body.get("permissions");
        	List<Integer> roles = new ArrayList<Integer>();
        	if(Permissions!=null){
                for (String permission : Permissions) {
                    DpRole condition = DpRole.QueryBuild().fetchAll().name(permission).build();
                    DpRole role = dpRoleMapper.queryDpRoleLimit1(condition);
                    roles.add(Integer.valueOf(role.getRoleId()));
                }

            }

        	StringBuilder sb =new StringBuilder();
        	for(int num : roles){
        	    sb.append(num).append(",");
            }
            if(sb.charAt(sb.length()-1)==',')
                sb.setLength(sb.length()-1);
            String roleIds = sb.toString();
        	User user = new User();
        	user.setUserLoginName(userLoginName);
        	user.setUserName(userLoginName);
        	user.setUserPassword(userPassword);
        	user.setUserStatus(Long.valueOf(0));
        	user.setCreator(userLoginName);
        	user.setModifier(userLoginName);
        	user.setRoleIds(roleIds);
        	user.setCreateTime(new Date());
        	user.setUpdateTime(new Date());
        	user.setUserId(uid);
        	userMapper.insert(user);
        	return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_SUCCESS, res);

    }
    /**
     *  获取所有权限列表
     * @param request 空
     * @return
     */
    @RequestMapping("/getPermissionsSelect")
    @ResponseBody
    @SystemControllerLog(description="获取所有权限")
    public BussinessMsg getPermissions(HttpServletRequest request){
        DpRole condition = DpRole.QueryBuild().fetchAll().build();
        List<DpRole> allRoles = dpRoleMapper.queryDpRole(condition);
        List<String> data = new ArrayList<>();
        for(DpRole r : allRoles){
            if(!r.getName().equals("渠道管理")&&!r.getName().equals("信息录入管理"))
            data.add(r.getName());
        }
        return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_SUCCESS, data);
    }
}
