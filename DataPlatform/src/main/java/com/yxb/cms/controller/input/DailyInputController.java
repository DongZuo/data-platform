package com.yxb.cms.controller.input;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
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
import com.alibaba.fastjson.JSON;
import com.data.util.PoiExt;
import com.yxb.cms.domain.vo.TbDailyInputVo;
import com.yxb.cms.model.input.TbDailyInput;
import com.yxb.cms.model.input.TbUtmSource;
import com.yxb.cms.service.input.TbDailyInputService;
import com.yxb.cms.service.input.TbUtmSourceService;

@Controller
@RequestMapping("/dailyInput")
public class DailyInputController {
    @Autowired
    private TbDailyInputService tbDailyInputService;
    @Autowired
    private TbUtmSourceService tbUtmSourceService;
    
    @ResponseBody
    @RequestMapping(value = "/getChannelDaily", method = RequestMethod.POST)
    public Map<String, Object> listDailyInputs(@RequestBody Map<String,String> body) throws ParseException {
    	String date = body.get("date");
    	String description= body.get("description");
    	String category=body.get("category");
    	String parentCategory=body.get("parentCategory");
    	String platform=body.get("platform");
    	String chargeType=body.get("chargeType");
    	Integer rows= Integer.valueOf(body.get("pageSize"));
    	Integer page= Integer.valueOf(body.get("page"));
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Date date_ ;
    	if(date != null && !date.equals("")){
        	 date_ = sdf.parse(date);
    	}else{
    		 date_ = new Date();
    	}
    	List<TbDailyInput> tbDailyInputs = tbDailyInputService.getAllDailyInputs(date_);
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
        try {
            description = java.net.URLDecoder.decode(description, "UTF-8");
            category = java.net.URLDecoder.decode(category, "UTF-8");
            parentCategory = java.net.URLDecoder.decode(parentCategory, "UTF-8");
            platform = java.net.URLDecoder.decode(platform, "UTF-8");
            chargeType = java.net.URLDecoder.decode(chargeType, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        data.put("total", tbUtmSourceService.getTotalCount(description, category,parentCategory,platform, chargeType, ""));
        List<TbDailyInputVo> tbDailyInputVos = new ArrayList<TbDailyInputVo>();
        for (TbUtmSource tbUtmSource : tbUtmSourceService.getUtmSources(description, category,parentCategory,platform, chargeType, "", rows, page)) {
            TbDailyInputVo tbDailyInputVo = new TbDailyInputVo();
            tbDailyInputVo.setDate(date_);
            tbDailyInputVo.setKey(tbUtmSource.getId().toString());
            tbDailyInputVo.setId(tbUtmSource.getId());
            tbDailyInputVo.setDescription(tbUtmSource.getDescription());
            tbDailyInputVo.setCategory(tbUtmSource.getCategory());
            tbDailyInputVo.setParentCategory(tbUtmSource.getParentCategory());
            tbDailyInputVo.setPlatform(tbUtmSource.getPlatform());
            tbDailyInputVo.setChargeType(tbUtmSource.getChargeType());
            tbDailyInputVo.setUtmSourceId(tbUtmSource.getId());
            tbDailyInputVo.setLandingPage(tbUtmSource.getLandingPage());
            tbDailyInputVo.setDownloadPage(tbUtmSource.getLandingPage());
            for (TbDailyInput tbDailyInput : tbDailyInputs) {
                if (tbDailyInput.getUtmSourceId().toString().equals(tbUtmSource.getId().toString())) {
                    tbDailyInputVo.setConsume(tbDailyInput.getConsume());
                    tbDailyInputVo.setDiscount(tbDailyInput.getDiscount());
                    tbDailyInputVo.setDisplay(tbDailyInput.getDisplay());
                    tbDailyInputVo.setClick(tbDailyInput.getClick());
                    tbDailyInputVo.setExposure(tbDailyInput.getExposure());
                    tbDailyInputVo.setComment(tbDailyInput.getComment());
                    tbDailyInputVo.setDownloads(tbDailyInput.getDownloads());
                }
            }
            tbDailyInputVos.add(tbDailyInputVo);
        }
        data.put("rows", tbDailyInputVos);
        Map<String,Object> result = new HashMap<String, Object>();
		result.put("code", 0);
		result.put("msg", "数据查询成功");
		result.put("data", data);       
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/postChannelEditDaily", method = RequestMethod.POST)
    public Map<String, Object> updateDailyInputs(@RequestBody Map<String,String> body) {
        TbDailyInputVo tbDailyInputVo = new TbDailyInputVo();
        tbDailyInputVo.setCategory(body.get("category"));
        tbDailyInputVo.setChargeType(body.get("chargeType"));
        tbDailyInputVo.setClick(body.get("click").equals("")?null:Integer.valueOf(body.get("click")));
        tbDailyInputVo.setComment(body.get("comment"));
        tbDailyInputVo.setConsume(body.get("consume").equals("")?null:Double.parseDouble(body.get("consume")));
        tbDailyInputVo.setDate(new Date(Long.valueOf(body.get("date"))));
        tbDailyInputVo.setDescription(body.get("description"));
        tbDailyInputVo.setDiscount(body.get("discount").equals("")?null:Float.parseFloat(body.get("discount")));
        tbDailyInputVo.setDisplay(body.get("display").equals("")?null:Integer.valueOf(body.get("display")));
        tbDailyInputVo.setDownloadPage(body.get("downloadPage"));
        tbDailyInputVo.setDownloads(body.get("downloads").equals("")?null:Integer.valueOf(body.get("downloads")));
        tbDailyInputVo.setExposure(body.get("exposure").equals("")?null:Integer.valueOf(body.get("exposure")));
        tbDailyInputVo.setId(Integer.valueOf(body.get("id")));
        tbDailyInputVo.setLandingPage(body.get("landingPage"));
        tbDailyInputVo.setParentCategory(body.get("parentCategory"));
        tbDailyInputVo.setPlatform(body.get("platform"));
        tbDailyInputVo.setUtmSourceId(Integer.valueOf(body.get("utmSourceId")));
        
        Map<String, Object> result = new HashMap<String, Object>();
        int retCode = 1;
        try {
            	if(tbDailyInputVo.getLandingPage() != null || tbDailyInputVo.getDownloadPage() != null){
                	//更新landingPage与downloadPage
            		TbUtmSource  ts = tbUtmSourceService.getUtmSource(tbDailyInputVo.getUtmSourceId());
            		if(tbDailyInputVo.getLandingPage() != null){
                		ts.setLandingPage(tbDailyInputVo.getLandingPage());
            		}
            		if(tbDailyInputVo.getDownloadPage() != null){
            			ts.setDownloadPage(tbDailyInputVo.getLandingPage());
            		}   
            		tbUtmSourceService.updateUtmSource(ts);
            	}           	
                // 按日期和utmSourceId检查，如果没有则插入，否则更新
                if (tbDailyInputVo.getDate() == null) {
                    retCode = 0;
                    result.put("errMsg", "请选择日期。");
                }
                Integer utmSourceId = tbDailyInputVo.getUtmSourceId();
                SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd");
                TbDailyInput tbDailyInput = tbDailyInputService.getDailyInput(format0.format(tbDailyInputVo.getDate()), utmSourceId);
                boolean isEmpty = (tbDailyInputVo.getConsume() == null && tbDailyInputVo.getDiscount() == null 
                        && tbDailyInputVo.getDisplay() == null && tbDailyInputVo.getClick() == null  && tbDailyInputVo.getExposure() == null 
                        && (tbDailyInputVo.getComment() == null || tbDailyInputVo.getComment().length() == 0
                        		|| tbDailyInputVo.getDownloads() == null));
                boolean exists = true;
                int code = 0;
                if (tbDailyInput == null) {
                    exists = false;
                }
                if (isEmpty) {
                    if (!exists) {
                        code = 1;
                    } else {
                        code = tbDailyInputService.removeDailyInput(tbDailyInputVo.getDate(), utmSourceId);
                    }
                } else {
                    if (!exists) {
                        tbDailyInput = new TbDailyInput();
                    }
                    tbDailyInput.setClick(tbDailyInputVo.getClick());
                    tbDailyInput.setComment(tbDailyInputVo.getComment());
                    tbDailyInput.setConsume(tbDailyInputVo.getConsume());
                    tbDailyInput.setDate(format0.format(tbDailyInputVo.getDate()));
                    tbDailyInput.setDiscount(tbDailyInputVo.getDiscount());
                    tbDailyInput.setDisplay(tbDailyInputVo.getDisplay());
                    tbDailyInput.setExposure(tbDailyInputVo.getExposure());
                    tbDailyInput.setUtmSourceId(tbDailyInputVo.getUtmSourceId());
                    tbDailyInput.setDownloads(tbDailyInputVo.getDownloads());
                    if (exists) {
                        code = tbDailyInputService.updateDailyInput(tbDailyInput);
                    } else {
                        code = tbDailyInputService.addDailyInput(tbDailyInput);
                    }
                }
                	result.put("code", 0);
              		result.put("msg", "数据写入成功");
              		result.put("data", "");   
              		
        } catch (Exception e) {
        	result.put("code", -1);
      		result.put("msg", "数据写入失败");
      		result.put("data", "");  
            result.put("msg", "保存渠道日常数据失败。");
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 导入EXCEL文件解析
     * @param request
     * @param parentId
     * @param keywordFile
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addDailyInputExcel", method = RequestMethod.POST)
    public Map<String, Object>  addDailyInputExcel(HttpServletRequest request,MultipartFile keywordFile) {
    	 int retCode = 0;
        String fileName = keywordFile.getOriginalFilename().trim();
        if (fileName != "" && fileName.endsWith(".xlsx")) {
    		PoiExt poiExt = new PoiExt();
    		Workbook wb = null;
    		try {
    			wb = poiExt.readExcel(keywordFile.getInputStream(),fileName);
    			Sheet sheet = wb.getSheetAt(0);
//    			Row row = sheet.getRow(0);
    			int rowNum = sheet.getPhysicalNumberOfRows(); 	//查询总行数
//    			int colNum = row.getPhysicalNumberOfCells();	//查询总列数
    			// 遍历行 从第二行开始遍历
    			if(rowNum>1){
    				for (int i = 1; i < rowNum; i++) {
    					Row currRow = sheet.getRow(i);
    					if(currRow!=null){
    						try {
    							//单条导入	
    								Date date = currRow.getCell(0).getDateCellValue();
    							 	String description = poiExt.getCellFormatValue(currRow.getCell(1));
    			                    String landingPage = poiExt.getCellFormatValue(currRow.getCell(5));	
    			                    String downloadPage = poiExt.getCellFormatValue(currRow.getCell(6));	
    			                    String consume = poiExt.getCellFormatValue(currRow.getCell(7));	
    			                    String discount = poiExt.getCellFormatValue(currRow.getCell(8));
    			                    String display = poiExt.getCellFormatValue(currRow.getCell(9));
    			                    String exposure = poiExt.getCellFormatValue(currRow.getCell(10));
    			                    String click = poiExt.getCellFormatValue(currRow.getCell(11));
    			                    String downloads = poiExt.getCellFormatValue(currRow.getCell(12));
    			                    String comment = poiExt.getCellFormatValue(currRow.getCell(13));
    			                    	//更新landingPage与downloadPage
    			                	TbUtmSource  ts = tbUtmSourceService.getUtmSourceByDescription(description);
    			                		if(landingPage != null && !landingPage.equals("")){
    			                    		ts.setLandingPage(landingPage);
    			                		}
    			                		if(downloadPage != null && !downloadPage.equals("")){
    			                			ts.setDownloadPage(downloadPage);
    			                		}   
    			                		tbUtmSourceService.updateUtmSource(ts);          	
    			                    TbDailyInput tbDailyInput = tbDailyInputService.getDailyInputByDescription(date, description);
    			                    boolean isEmpty = (consume == null && discount == null 
    			                            && display == null  && exposure == null && click == null 
    			                            && (comment == null || comment.length() == 0
    			                            		|| downloads == null));
    			                    boolean exists = true;
    			                    int code = 0;
    			                    if (tbDailyInput == null) {
    			                        exists = false;
    			                    }
    			                    if (isEmpty) {
    			                        if (!exists) {
    			                            code = 1;
    			                        } else {
    			                            code = tbDailyInputService.removeDailyInput(date, tbDailyInput.getUtmSourceId());
    			                        }
    			                    } else {
    			                        if (!exists) {
    			                            tbDailyInput = new TbDailyInput();
    			                        }
    			                        if(click != null && !click.equals("")){
        			                        Float f = new Float(click);
        			                        tbDailyInput.setClick(f.intValue());
    			                        }
    			                        if(comment != null && !comment.equals("")){
        			                        tbDailyInput.setComment(comment);
    			                        }
    			                        if(consume != null && !consume.equals("")){
        			                        tbDailyInput.setConsume(Double.valueOf(consume));
    			                        }
    			                        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd");
    			                        tbDailyInput.setDate(format0.format(date));
    			                        if(discount != null && !discount.equals("")){
        			                        tbDailyInput.setDiscount(Float.valueOf(discount));
    			                        }
    			                        if(display != null && !display.equals("")){
    			                        	Float f = new Float(display);
        			                        tbDailyInput.setDisplay(f.intValue());
    			                        }
    			                        if(exposure != null && !exposure.equals("")){
    			                        	Float f = new Float(exposure);
        			                        tbDailyInput.setExposure(f.intValue());
    			                        }
    			                        tbDailyInput.setUtmSourceId(ts.getId());
    			                        if(downloads != null && !downloads.equals("")){
    			                        	Float f = new Float(downloads);
        			                        tbDailyInput.setDownloads(f.intValue());
    			                        }
    			                        if (exists) {
    			                            code = tbDailyInputService.updateDailyInput(tbDailyInput);
    			                        } else {
    			                            code = tbDailyInputService.addDailyInput(tbDailyInput);
    			                        }
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
