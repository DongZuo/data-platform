package com.yxb.cms.controller.input;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.data.util.ExcelPOI;
import com.data.util.PoiExt;
import com.yxb.cms.model.input.TbUtmSource;
import com.yxb.cms.service.input.TbUtmSourceService;

@Controller
@RequestMapping("/channelManage")
public class UtmSourceController {
    @Autowired
    private TbUtmSourceService tbUtmSourceService;
    
    @ResponseBody
    @RequestMapping(value = "/listAllUtmSources", method = RequestMethod.POST)
    public Map<String, Object> listAllUtmSources() {
        Map<String,Object> result = new HashMap<String, Object>();
		result.put("code", 0);
		result.put("msg", "数据查询成功");
		List<TbUtmSource> lists = tbUtmSourceService.getAllUtmSources();
		for (TbUtmSource tbUtmSource : lists) {
			tbUtmSource.setKey(tbUtmSource.getId().toString());
		}
		result.put("data",lists);       
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/getChannelFirst", method = RequestMethod.POST)
    public Map<String, Object> listUtmSources(@RequestBody Map<String,String> body) {
    	String description= body.get("description");
    	String category=body.get("category");
    	String parentCategory=body.get("parentCategory");
    	String platform=body.get("platform");
    	String chargeType=body.get("chargeType");
    	String abbreviation=body.get("abbreviation");
    	Integer pageSize= Integer.valueOf(body.get("pageSize"));
    	Integer page= Integer.valueOf(body.get("page"));
    	
        Map<String, Object> data = new HashMap<String, Object>();
        if (description == null) {
            description = "";
        }
        if (category == null) {
            category = "";
        }
        if (parentCategory == null) {
        	parentCategory = "";
        }
        if (platform == null) {
            platform = "";
        }
        if (chargeType == null) {
            chargeType = "";
        }
        if (abbreviation == null) {
            abbreviation = "";
        }
        try {
            description = java.net.URLDecoder.decode(description, "UTF-8");
            category = java.net.URLDecoder.decode(category, "UTF-8");
            parentCategory = java.net.URLDecoder.decode(parentCategory, "UTF-8");
            platform = java.net.URLDecoder.decode(platform, "UTF-8");
            chargeType = java.net.URLDecoder.decode(chargeType, "UTF-8");
            abbreviation = java.net.URLDecoder.decode(abbreviation, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        data.put("total", tbUtmSourceService.getTotalCount(description, category,parentCategory, platform, chargeType, abbreviation));
        List<TbUtmSource> rows = tbUtmSourceService.getUtmSources(description, category,parentCategory, platform, chargeType, abbreviation, pageSize, page);
        for (TbUtmSource tbUtmSource : rows) {
        	tbUtmSource.setKey(tbUtmSource.getId().toString());
		}
        data.put("rows", rows);
       
        Map<String,Object> result = new HashMap<String, Object>();
		result.put("code", 0);
		result.put("msg", "数据查询成功");
		result.put("data", data);       
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/postChannelFirstAdd", method = RequestMethod.POST)
    public Map<String, Object> addUtmSource(@RequestBody Map<String,String> body) {
//    	“description”:"",     -----------------------描述
//    	   ”category“:"",        -----------------------分类
//    	   “parentCategory“:"",  -----------------------大类渠道分类
//    	   ”platform”:"",        -----------------------渠道平台
//    	   “chargeType“:"",      -----------------------付费类型
//    	   ”abbreviation”:"",    -----------------------简写,
//    	   “key“:"" ,            -----------------------该条数据的唯一标识 
//    	   ”principal”:"",       -----------------------负责人       
//    	   “superior‘:"",        -----------------------上级
    	TbUtmSource tbUtmSource = new TbUtmSource();
    	tbUtmSource.setDescription(body.get("description"));
    	tbUtmSource.setCategory(body.get("category"));
    	tbUtmSource.setParentCategory(body.get("parentCategory"));
    	tbUtmSource.setPlatform(body.get("platform"));
    	tbUtmSource.setChargeType(body.get("chargeType"));
    	tbUtmSource.setAbbreviation(body.get("abbreviation"));
    	tbUtmSource.setPrincipal(body.get("principal"));
    	tbUtmSource.setSuperior(body.get("superior"));
    	 int retCode = 0;
        try {
            tbUtmSource.setDescription(tbUtmSource.getDescription().replace("\n", ""));
            retCode = tbUtmSourceService.addUtmSource(tbUtmSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String,Object> result = new HashMap<String, Object>();
        if(retCode != 0){
    		result.put("code", 0);
    		result.put("msg", "新增成功");
    		result.put("data", ""); 	
        }else{
        	result.put("code", -1);
    		result.put("msg", "新增失败");
    		result.put("data", ""); 
        }    
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/deleteChannelFirst", method = RequestMethod.POST)
    public Map<String, Object> removeUtmSource(@RequestBody Map<String,Object> body){
        Map<String,Object> result = new HashMap<String, Object>();
        ArrayList<String> keys = (ArrayList)body.get("key");
        for (String string : keys) {
    		 try {
    	            tbUtmSourceService.removeUtmSource(Integer.valueOf(string));
    	        } catch (Exception e) {
    	            e.printStackTrace();
    	            result.put("code", -1);
    	    		result.put("msg", "删除失败");
    	    		result.put("data", ""); 
    	        }	
		}
		result.put("code", 0);
		result.put("msg", "删除成功");
		result.put("data", "");       
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/postChannelEditFirst", method = RequestMethod.POST)
    public Map<String, Object> editUtmSource(@RequestBody Map<String,String> body) {
    	TbUtmSource tbUtmSource = new TbUtmSource();
    	tbUtmSource.setDescription(body.get("description"));
    	tbUtmSource.setCategory(body.get("category"));
    	tbUtmSource.setParentCategory(body.get("parentCategory"));
    	tbUtmSource.setPlatform(body.get("platform"));
    	tbUtmSource.setChargeType(body.get("chargeType"));
    	tbUtmSource.setAbbreviation(body.get("abbreviation"));
    	tbUtmSource.setPrincipal(body.get("principal"));
    	tbUtmSource.setSuperior(body.get("superior"));
    	tbUtmSource.setLandingPage(body.get("landingPage"));
    	tbUtmSource.setDownloadPage(body.get("downloadPage"));
    	tbUtmSource.setId(Integer.valueOf(body.get("id")));
        int retCode = 0;
        try {
            tbUtmSource.setDescription(tbUtmSource.getDescription().replace("\n", ""));
            retCode = tbUtmSourceService.updateUtmSource(tbUtmSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String,Object> result = new HashMap<String, Object>();
        if(retCode != 0){
    		result.put("code", 0);
    		result.put("msg", "修改成功");
    		result.put("data", ""); 	
        }else{
        	result.put("code", -1);
    		result.put("msg", "修改失败");
    		result.put("data", ""); 
        } 
        return result;
    }
    
    /**
	 * @Description: utmsource报表导出
	 * @param request
	 * @return String
	 */
	@RequestMapping(value= "/reportChannelFirst")
	@ResponseBody
	public void excelUtmSource(HttpServletResponse response) {
		List<Map<String, Object>> rowslist = new ArrayList<Map<String, Object>>();
		List<TbUtmSource> list =  tbUtmSourceService.getAllUtmSources();
		for (TbUtmSource tbUtmSource : list) {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			//map.put("id",tbUtmSource.getId()==null?"":tbUtmSource.getId());
			map.put("渠道描述",tbUtmSource.getDescription()==null?"":tbUtmSource.getDescription());
			map.put("渠道分类",tbUtmSource.getCategory()==null?"":tbUtmSource.getCategory());
			map.put("渠道大类",tbUtmSource.getParentCategory()==null?"":tbUtmSource.getParentCategory());
			map.put("平台",tbUtmSource.getPlatform()==null?"":tbUtmSource.getPlatform());
			map.put("类型",tbUtmSource.getChargeType()==null?"":tbUtmSource.getChargeType());
			map.put("缩写",tbUtmSource.getAbbreviation()==null?"":tbUtmSource.getAbbreviation());
			map.put("负责人",tbUtmSource.getPrincipal()==null?"":tbUtmSource.getPrincipal());
			map.put("上级",tbUtmSource.getSuperior()==null?"":tbUtmSource.getSuperior());								
			rowslist.add(map);
		}
		ExcelPOI<Object> ex = new ExcelPOI<Object>(); 
	 	   Object[] keys = null;
	 		if(rowslist.size()>0){
	 			Map<String, Object> map = rowslist.get(0);
	 				Set<String> keyset = map.keySet();
	 				 keys = keyset.toArray();
	 		}
	 		String[] headers = new String[keys.length];
	 		int flag = 0;
	 		for (Object object : keys) {
	 			headers[flag] = object.toString();
	 			flag++;
	 		}
	 		OutputStream out = null;
	 		try {
	 			out = new FileOutputStream("utmsource.xls");
	 			ex.exportExcel(headers, rowslist, out);
	 			out.close();  
	 			ExcelPOI.download(response,"utmsource.xls","utmsource");
	 		} catch (Exception e) {
	 			e.printStackTrace();
	 		}
	}
	
	
    /**
     * 导入EXCEL文件解析
     * @param request
     * @param parentId
     * @param keywordFile
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/importChannelFirst", method = RequestMethod.POST)
    public Map<String, Object> addUtmSourceExcel(HttpServletRequest request,MultipartFile keywordFile) {
        String fileName = keywordFile.getOriginalFilename().trim();
        if (fileName != "" && fileName.endsWith(".xls")) {
    		PoiExt poiExt = new PoiExt();
    		Workbook wb = null;
    		try {
    			wb = poiExt.readExcel(keywordFile.getInputStream(),fileName);
    			Sheet sheet = wb.getSheetAt(0);
    			int rowNum = sheet.getPhysicalNumberOfRows(); 	//查询总行数
    			// 遍历行 从第二行开始遍历
    			if(rowNum>1){
    				for (int i = 1; i < rowNum; i++) {
    					Row currRow = sheet.getRow(i);
    					if(currRow!=null){
    						try {
    							//单条导入	
    							 	String description = poiExt.getCellFormatValue(currRow.getCell(0));
    			                    String category = poiExt.getCellFormatValue(currRow.getCell(1));	
    			                    String parentCategory = poiExt.getCellFormatValue(currRow.getCell(2));	
    			                    String platform = poiExt.getCellFormatValue(currRow.getCell(3));	
    			                    String charge_type = poiExt.getCellFormatValue(currRow.getCell(4));
    			                    String abbreviation = poiExt.getCellFormatValue(currRow.getCell(5));
    			                    String principal = poiExt.getCellFormatValue(currRow.getCell(6));
    			                    String superior = poiExt.getCellFormatValue(currRow.getCell(7));        	
    			                    TbUtmSource tbUtmSource = tbUtmSourceService.getUtmSourceByDescription(description);
    			                    boolean isEmpty = (description == null && category == null);
    			                    if (isEmpty){continue;}
    			                    boolean exists = true;
    			                    int code = 0;
    			                    if (tbUtmSource == null) {
    			                        exists = false;
    			                    }
    			                        if (!exists) {
    			                        	tbUtmSource = new TbUtmSource();
    			                        }
    			                        if(description != null && !description.equals("")){
    			                        	tbUtmSource.setDescription(description);
    			                        }
    			                        if(category != null && !category.equals("")){
    			                        	tbUtmSource.setCategory(category);
    			                        }
    			                        if(parentCategory != null && !parentCategory.equals("")){
    			                        	tbUtmSource.setParentCategory(parentCategory);
    			                        }
    			                        if(platform != null && !platform.equals("")){
    			                        	tbUtmSource.setPlatform(platform);
    			                        }
    			                        if(charge_type != null && !charge_type.equals("")){
    			                        	tbUtmSource.setChargeType(charge_type);
    			                        }
    			                        if(abbreviation != null && !abbreviation.equals("")){
    			                        	tbUtmSource.setAbbreviation(abbreviation);
    			                        }
    			                        if(principal != null && !principal.equals("")){
    			                        	tbUtmSource.setPrincipal(principal);
    			                        }
    			                        if(superior != null && !superior.equals("")){
    			                        	tbUtmSource.setSuperior(superior);
    			                        }
    			                        
    			                        if (exists) {
    			                            code = tbUtmSourceService.updateUtmSource(tbUtmSource);
    			                        } else {
    			                            code = tbUtmSourceService.addUtmSource(tbUtmSource);
    			                        }
    						} catch (Exception e) {    
    							continue;
    						}
    					}
    				}
    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    		}finally{
    			try {
    				wb.close();
    				keywordFile.getInputStream().close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
        }
        Map<String,Object> result = new HashMap<String, Object>();
		result.put("code", 0);
		result.put("msg", "数据导入成功");
		result.put("data", "");       
        return result;
    }
}
