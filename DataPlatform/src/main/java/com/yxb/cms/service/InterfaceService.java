package com.yxb.cms.service;

import com.yxb.cms.controller.InterfaceController;
import com.yxb.cms.model.APInterface;
import com.yxb.cms.model.Param;
import com.yxb.cms.model.Project;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class InterfaceService {
    private DriverManagerDataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate  ;
    Logger log = LogManager.getLogger(ResourceService.class);
    public InterfaceService(){
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.1.43:3306/data_bi?autoReconnect=true&useUnicode=true&characterEncoding\\=utf-8");
        dataSource.setUsername("rrd_sjlc");
        dataSource.setPassword("Vpov9BBvxReqCZQxw3YS");
//        dataSource.setUrl("jdbc:mysql://localhost:3306/system?autoReconnect=true&useUnicode=true&characterEncoding\\=utf-8");
//        dataSource.setUsername("root");
//        dataSource.setPassword("password");

        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public List<Project> getProjectList(){
        String sql = "SELECT * FROM dp_project";
        List<Project> projects=jdbcTemplate.query(sql, (resultset, i) -> {
            Project proj = new Project();
            proj.setAuthor(resultset.getString("author"));
            proj.setHeaderImg(resultset.getString("headerImg"));
            proj.setDescription(resultset.getString("description"));
            proj.setCoverBg(resultset.getString("coverBg"));
            proj.setTitle(resultset.getString("title"));
            proj.setKey(resultset.getString("key"));
            return proj;
        });

        projects.sort(Comparator.comparing(Project::getTitle));
        return projects;
    }
    public boolean addProject(Map<String,Object> body,String username){
        try{
            String coverBg = (String) body.get("coverBg");
            String headerImg = (String) body.get("headerImg");
            String description = (String) body.get("description");
            String author = username;
            String title = (String) body.get("title");
            String key = String.valueOf(body.get("key"));
            //both update or insert could use this SQL
            String addSQL = String.format("REPLACE INTO dp_project " +
                    "VALUES('%s','%s','%s','%s','%s','%s')",coverBg,headerImg,description,author,title,key);
            jdbcTemplate.execute(addSQL);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    public void deleteProject(String key){
        String deleteSQL = "DELETE FROM dp_project WHERE dp_project.key = "+key;
        jdbcTemplate.execute(deleteSQL);
    }
    public boolean editProject(Map<String,Object> body){
        try{
            String coverBg = (String) body.get("coverBg");
            String headerImg = (String) body.get("headerImg");
            String description = (String) body.get("description");
            String author = (String) body.get("author");
            String title = (String) body.get("title");
            String key = (String)body.get("key");

            //both update or insert could use this SQL
            String addSQL = String.format("REPLACE INTO dp_project " +
                    "VALUES('%s','%s','%s','%s','%s','%s')",coverBg,headerImg,description,author,title,key);
            jdbcTemplate.execute(addSQL);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public boolean addAPI(String key,String projectId,String icon,String title){
        try{
            //both update or insert could use this SQL
            String addSQL = String.format("REPLACE INTO dp_interface(dp_interface.key,dp_interface.projectId,dp_interface.icon,dp_interface.title) " +
                    "VALUES('%s','%s','%s','%s')",key,projectId,icon,title);
            jdbcTemplate.execute(addSQL);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    public boolean saveAPI(APInterface api){
        try{
            String key = api.getKey();
            String projectId = api.getProjectId();
            String icon = "capital";
            String description = api.getDescription();
            String title = api.getTitle();

            String saveSQL = String.format("REPLACE INTO dp_interface(dp_interface.key,dp_interface.projectId,dp_interface.title,dp_interface.icon,dp_interface.description) " +
                    "VALUES('%s','%s','%s','%s','%s')",key,projectId,title,icon,description);
            jdbcTemplate.execute(saveSQL);
            if(api.getParams()!=null){
                for(Param p : api.getParams()){
                    saveParam(p,"API",api.getKey());
                }
            }
            if(api.getReturnParams()!=null){
                for(Param p : api.getReturnParams()){
                    saveParam(p,"returnAPI",api.getKey());
                }
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
    private void saveParam(Param p,String type, String ref){
        if(type.equals("API")){
            String saveSQL = String.format("REPLACE INTO dp_param(dp_param.key,dp_param.APIKey,dp_param.name,dp_param.type,dp_param.commit) " +
                    "VALUES('%s','%s','%s','%s','%s')",p.getKey(),ref,p.getName(),p.getType(),p.getCommit());
            jdbcTemplate.execute(saveSQL);
            if(p.getChildren()!=null&&p.getChildren().size()>0){
                for(Param child : p.getChildren()){
                    saveParam(child,"parent",p.getKey());
                }
            }
        }else if(type.equals("returnAPI")){
            String saveSQL = String.format("REPLACE INTO dp_param(dp_param.key,dp_param.returnAPIKey,dp_param.name,dp_param.type,dp_param.commit) " +
                    "VALUES('%s','%s','%s','%s','%s')",p.getKey(),ref,p.getName(),p.getType(),p.getCommit());
            jdbcTemplate.execute(saveSQL);
            if(p.getChildren()!=null&&p.getChildren().size()>0){
                for(Param child : p.getChildren()){
                    saveParam(child,"parent",p.getKey());
                }
            }
        }else{
            if(type.equals("parent")){
                String saveSQL = String.format("REPLACE INTO dp_param(dp_param.key,dp_param.parentParamKey,dp_param.name,dp_param.type,dp_param.commit) " +
                        "VALUES('%s','%s','%s','%s','%s')",p.getKey(),ref,p.getName(),p.getType(),p.getCommit());
                jdbcTemplate.execute(saveSQL);
                if(p.getChildren()!=null&&p.getChildren().size()>0){
                    for(Param child : p.getChildren()){
                        saveParam(child,"parent",p.getKey());
                    }
                }
            }
        }
    }
    public boolean deleteAPI(String key){
        try{
            String deleteSQL = String.format("DELETE FROM dp_interface where dp_interface.key = '%s'",key);
            jdbcTemplate.execute(deleteSQL);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    public List<Map<String, Object>> getAPIList(String key){
        String selectSQL = String.format("SELECT * FROM dp_interface where dp_interface.projectId = '%s'",key);
        java.util.List<Map<String, Object>> APIs = jdbcTemplate.queryForList(selectSQL);
        return APIs;
    }
    public APInterface getAPI(String key){
        Logger log = LogManager.getLogger(InterfaceController.class);
        key="'"+key+"'";
        String sql = "SELECT * FROM dp_interface WHERE dp_interface.key = "+key;
        APInterface res = (APInterface)jdbcTemplate.queryForObject(sql, new InterfaceRowMapper());
//        APInterface res = (APInterface) jdbcTemplate.queryForObject(
//                sql, new Object[] {key}, new InterfaceRowMapper());
        return res;
    }
    class InterfaceRowMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Logger log = LogManager.getLogger(InterfaceController.class);
            APInterface api = new APInterface();
            api.setIcon(rs.getString("icon"));
            api.setDescription(rs.getString("description"));
            api.setKey(rs.getString("key"));
            api.setProjectId(rs.getString("projectId"));
            api.setTitle(rs.getString("title"));
            //select params who belong to this API
            List<Param> params = collectParams(api.getKey(),"API");
            if(params!=null&&params.size()>0)
                api.setParams(params);
            //select params who return to this API
            List<Param> returnParams = collectParams(api.getKey(),"returnAPI");
            if(returnParams!=null&&returnParams.size()>0)
                api.setReturnParams(returnParams);
            return api;
        }
        private List<Param> collectParams(String key,String type){
            if(type.equals("API")){
                String selectSQL = String.format("SELECT * FROM dp_param where dp_param.APIKey = '%s'",key);
                java.util.List<Map<String, Object>> params = jdbcTemplate.queryForList(selectSQL);
                if(params!=null&&params.size()>0){
                    List<Param> res = new ArrayList<>();
                    for(Map<String, Object> p : params){
                        Param newParam = new Param();
                        newParam.setKey((String)p.get("key"));
                        newParam.setCommit((String)p.get("commit"));
                        newParam.setName((String)p.get("name"));
                        newParam.setType((String)p.get("type"));
                        newParam.setChildren(collectParams(newParam.getKey(),"parent"));
                        res.add(newParam);
                    }
                    return res;
                }else{
                    return null;
                }
            }else if (type.equals("returnAPI")){
                String selectSQL = String.format("SELECT * FROM dp_param where dp_param.returnAPIKey = '%s'",key);
                java.util.List<Map<String, Object>> params = jdbcTemplate.queryForList(selectSQL);
                if(params!=null&&params.size()>0){
                    List<Param> res = new ArrayList<>();
                    for(Map<String, Object> p : params){
                        Param newParam = new Param();
                        newParam.setKey((String)p.get("key"));
                        newParam.setCommit((String)p.get("commit"));
                        newParam.setName((String)p.get("name"));
                        newParam.setType((String)p.get("type"));
                        newParam.setChildren(collectParams(newParam.getKey(),"parent"));
                        res.add(newParam);
                    }
                    return res;
                }else{
                    return null;
                }
            }else if(type.equals("parent")){
                String selectSQL = String.format("SELECT * FROM dp_param where dp_param.parentParamKey = '%s'",key);
                java.util.List<Map<String, Object>> params = jdbcTemplate.queryForList(selectSQL);
                if(params!=null&&params.size()>0){
                    List<Param> res = new ArrayList<>();
                    for(Map<String, Object> p : params){
                        Param newParam = new Param();
                        newParam.setKey((String)p.get("key"));
                        newParam.setCommit((String)p.get("commit"));
                        newParam.setName((String)p.get("name"));
                        newParam.setType((String)p.get("type"));
                        newParam.setChildren(collectParams(newParam.getKey(),"parent"));
                        res.add(newParam);
                    }
                    return res;
                }else{
                    return null;
                }
            }else{
                return null;
            }
        }
    }
}
