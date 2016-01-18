package com.org.utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jspsmart.upload.File;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
import com.org.common.CommonConstant;

/**
 */
public class FileUploadUtil {
	//private static final String excelUploadPath = SmpPropertyUtil.getValue("filepath", "upload_file_path");
	private static String excelUploadPath = "";

	public FileUploadUtil() {
		super();
	}
	
	/**
	 * 上传文件
	 * 
	 * @param request
	 * @param response
	 * @throws Exception 
	 * @throws IOException
	 */
	public static JSONObject uploadFile(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if(StringUtils.isEmpty(excelUploadPath)) {
			excelUploadPath = request.getSession().getServletContext().getRealPath("/");
		}
		
		JSONObject res = new JSONObject();
		// 新建一个SmartUpload对象
		SmartUpload su = null;
		try {
			su = initSmartUpload(request, response);
		} catch (java.lang.SecurityException e) {
			//e.printStackTrace();
			res.put(CommonConstant.RESP_CODE, "ERROR");
			res.put(CommonConstant.RESP_MSG, "上传文件不符合要求");
			return res;
		}
		
		JSONObject formParams = new JSONObject();
		Enumeration<?> paramKey = su.getRequest().getParameterNames();
		while (paramKey.hasMoreElements()) {
			Object key = paramKey.nextElement();
			if(key != null) {
				formParams.put(String.valueOf(key), su.getRequest().getParameter(String.valueOf(key)));
			}
		}

		//如果要实现文件的批量上传，则只需用for循环，将getFile(0)中的0改为i即可
		File file = su.getFiles().getFile(0);
		file.getSize();
		if(file != null) {
			//物理路径 
			StringBuffer physicalPathTemp = new StringBuffer();
			//相对路径存放到数据库的
			StringBuffer relativePathTemp = new StringBuffer();
			relativePathTemp = relativePathTemp.append("files/").append(DateUtil.getCurrentShortDateStr()).append("/");
			
			String fileName = new String(file.getFileName().getBytes(), "UTF-8");
			physicalPathTemp = physicalPathTemp.append(excelUploadPath).append(relativePathTemp);
			// 保证物理路径目录存在
			java.io.File dir = new java.io.File(physicalPathTemp.toString());
			if(!dir.exists()){
				dir.mkdirs();
			}
			
			relativePathTemp = relativePathTemp.append(fileName);
			
			// 保存文件
			file.saveAs(physicalPathTemp.append(fileName).toString());
			formParams.put(CommonConstant.FILE_PATH, "/"+relativePathTemp.toString());
		}
		
		// 把文件存放路径再返回出去
		res.put(CommonConstant.RESP_CODE, "10000");
		res.put(CommonConstant.RESP_MSG, "");
		res.put(CommonConstant.FORM_PARAMS, formParams);
		
		return res;
	}

	private static SmartUpload initSmartUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, SmartUploadException {
		SmartUpload su = new SmartUpload();
		// 上传初始化
		JspFactory jspFactory = null;
        jspFactory = JspFactory.getDefaultFactory();
        PageContext pc = jspFactory.getPageContext((HttpServlet)request.getSession().getAttribute(CommonConstant.SERVLET),request,response,"",true,8192,true);
		su.initialize(pc);
		log.info("上传请求内容字节长度: " + request.getContentLength());
		// 设定上传限制
		// 1.限制每个上传文件的最大长度。
		su.setMaxFileSize(5000000);
		// 2.限制总上传数据的长度。
		su.setTotalMaxFileSize(50000000);
		// 3.设定允许上传的文件（通过扩展名限制）,仅允许doc,txt文件。
		su.setAllowedFilesList("jpg,jpeg");
		// 4.设定禁止上传的文件（通过扩展名限制）,禁止上传带有exe,bat,jsp,htm,html扩展名的文件和没有扩展名的文件。
		su.setDeniedFilesList("exe,bat,jsp,htm,html,xls,xlsx,,");
		// 上传文件
		try {
			su.upload();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return su;
	}

	private static Log log = LogFactory.getLog(FileUploadUtil.class);
}
