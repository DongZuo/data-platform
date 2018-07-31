package com.yxb.cms.service.input;

import java.util.List;

import com.yxb.cms.model.input.TbSubUtmSource;

public interface TbSubUtmSourceService {
    public List<TbSubUtmSource> getAllSubUtmSources();
    
    public List<TbSubUtmSource> getSubUtmSources(String utmSource, String description, String plan, String unit, String keyword, int pageSize, int page);
    
    public List<TbSubUtmSource> getSubUtmSourcesExcel(String utmSource, String description, String plan, String unit, String keyword);
    
    public int getTotalCount(String utmSource, String description, String plan, String unit, String keyword);
    
    public int addSubUtmSource(TbSubUtmSource tbSubUtmSource);
    
    public int removeSubUtmSource(int id);
    
    public int updateSubUtmSource(TbSubUtmSource tbSubUtmSource);
}
