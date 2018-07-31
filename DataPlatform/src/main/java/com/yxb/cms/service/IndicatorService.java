package com.yxb.cms.service;

import com.yxb.cms.model.Indicator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class IndicatorService {
    private DriverManagerDataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate  ;
    private Logger log = LogManager.getLogger(ResourceService.class);
    public IndicatorService(){
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.1.43:3306/data_bi?autoReconnect=true&useUnicode=true&characterEncoding\\=utf-8");
        dataSource.setUsername("rrd_sjlc");
        dataSource.setPassword("Vpov9BBvxReqCZQxw3YS");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public List<Indicator> getIndicatorList(String searchTerm){
        if(searchTerm == null){
            String sql = "SELECT * FROM dp_indicator";
            List<Indicator> indicators=jdbcTemplate.query(sql, (resultset, i) -> {
                Indicator indicator = new Indicator();
                indicator.setTitle(resultset.getString("title"));
                indicator.setAuthor(resultset.getString("author"));
                indicator.setDescription(resultset.getString("description"));
                indicator.setKey(resultset.getString("key"));
                indicator.setSqlInfo(resultset.getString("sqlInfo"));
                String tagstr = resultset.getString("tag");
                if(!tagstr.trim().equals("")){
                    String [] tags = tagstr.trim().split(",");
                    indicator.setTag(tags);
                }else{
                    indicator.setTag(new String[0] );
                }
                return indicator;
            });

            indicators.sort(Comparator.comparing(Indicator::getTitle));
            return indicators;
        }else{
            String sql = "SELECT * FROM dp_indicator WHERE " +
                    "title LIKE '%"+searchTerm+"%' OR description LIKE '%"+searchTerm+"%' OR tag LIKE '%"+searchTerm+"%' " ;
            List<Indicator> indicators=jdbcTemplate.query(sql, (resultset, i) -> {
                Indicator indicator = new Indicator();
                indicator.setTitle(resultset.getString("title"));
                indicator.setAuthor(resultset.getString("author"));
                indicator.setDescription(resultset.getString("description"));
                indicator.setKey(resultset.getString("key"));
                indicator.setSqlInfo(resultset.getString("sqlInfo"));
                String tagstr = resultset.getString("tag");
                if(!tagstr.trim().equals("")){
                    String [] tags = tagstr.trim().split(",");
                    indicator.setTag(tags);
                }else{
                    indicator.setTag(new String[0] );
                }
                return indicator;
            });
            indicators.sort(Comparator.comparing(Indicator::getTitle));
            return indicators;
        }
    }
    public void addIndicator(Map<String,Object> body){
        StringBuilder tagsb = new StringBuilder();
        List<String> tags = (List<String>) body.get("tag");
        String key = (String)body.get("key");
        String author = (String)body.get("author");
        String title = (String)body.get("title");
        String sqlInfo = (String)body.get("sqlInfo");
        String description = (String)body.get("description");
        if(tags!=null){
            for(String tag:tags){
                tagsb.append(tag);
                tagsb.append(",");
            }
        }
        if(tagsb.length()>0&&tagsb.charAt(tagsb.length()-1)==','){
            tagsb.setLength(tagsb.length()-1);
        }
        String tag = tagsb.toString();
        //both update or insert could use this SQL
        String addSQL = String.format("REPLACE INTO dp_indicator " +
                "VALUES('%s','%s','%s','%s','%s','%s')",tag,title,author,key,sqlInfo,description);
        jdbcTemplate.execute(addSQL);
    }
    public Indicator getIndicator(String key){
        String sql = "SELECT * FROM dp_indicator WHERE dp_indicator.key = ?";
        log.info("key:"+key);
        Indicator indicator = (Indicator)jdbcTemplate.queryForObject(
                sql, new Object[] {key}, new IndicatorRowMapper());
        return indicator;
    }
    public void deleteIndicator(Map<String,String> body){
        String key = body.get("key");
        String deleteSQL = "DELETE FROM dp_indicator WHERE dp_indicator.key = "+key;
        jdbcTemplate.execute(deleteSQL);
    }
    public List<String> getTagList(){
        String sql = "SELECT tag FROM dp_indicator_tag";
        List<String> tags=jdbcTemplate.query(sql, (resultset, i) -> resultset.getString("tag"));
        return tags;
    }
}
class IndicatorRowMapper implements RowMapper {
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Indicator idc = new Indicator();
        String tagstr = rs.getString("tag").trim();
        if(tagstr.length()==0)
            idc.setTag(new String[0]);
        else{
            idc.setTag(tagstr.split(","));
        }
        idc.setKey(rs.getString("key"));
        idc.setAuthor(rs.getString("author"));
        idc.setTitle(rs.getString("title"));
        idc.setDescription(rs.getString("description"));
        idc.setSqlInfo(rs.getString("sqlInfo"));
        return idc;
    }
}
