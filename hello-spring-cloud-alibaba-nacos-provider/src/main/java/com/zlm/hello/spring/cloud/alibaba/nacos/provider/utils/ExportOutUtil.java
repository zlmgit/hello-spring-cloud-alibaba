package com.zlm.hello.spring.cloud.alibaba.nacos.provider.utils;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider.utils.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 导出输出Util
 */
public class ExportOutUtil {

    /**
     * 导出
     *
     * @param request
     * @param response
     * @param fileName
     * @param sheetName
     * @param excelInterfaces
     * @param list
     * @throws IOException
     */
    public static <T> void export(HttpServletRequest request, HttpServletResponse response, String fileName,
                                  String sheetName, ExcelInterface[] excelInterfaces, List<T> list) throws IOException {
        
        fileName = StringUtil.repFileName(fileName);
        //导出Excel文档
        HSSFWorkbook workbook;
        StringBuffer buffer;
        workbook = ExcelUpload.writeExcel("", sheetName, excelInterfaces, list);
        //创建文件名
        buffer = new StringBuffer().append(fileName).append(".xls");
        String encodedfileName;
        // 火狐
        if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
            encodedfileName = new String(buffer.toString().getBytes("UTF-8"), "ISO8859-1");
        } else {
            // IE
            encodedfileName = java.net.URLEncoder.encode(buffer.toString(), "UTF-8");
        }
        response.setContentType(ExcelUpload.EXCEL_TYPE);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
        workbook.write(response.getOutputStream());
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
    
}
