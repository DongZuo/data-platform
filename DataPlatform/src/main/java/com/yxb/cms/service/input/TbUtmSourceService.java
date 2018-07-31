package com.yxb.cms.service.input;

import java.util.List;

import com.yxb.cms.model.input.TbUtmSource;

public interface TbUtmSourceService {
    public List<TbUtmSource> getAllUtmSources();
    
    public List<TbUtmSource> getUtmSources(String description, String category,String parentCategory, String platform, String chargeType, String abbreviation, int pageSize, int page);
    
    public TbUtmSource getUtmSource(int id);
    
    public TbUtmSource getUtmSourceByDescription(String description);
    
    public int getTotalCount(String description, String category,String parentCategory,String platform, String chargeType, String abbreviation);
    
    public int addUtmSource(TbUtmSource tbUtmSource);
    
    public int removeUtmSource(int id);
    
    public int updateUtmSource(TbUtmSource tbUtmSource);
}
