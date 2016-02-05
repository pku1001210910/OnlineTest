package com.fivestars.websites.onlinetest.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.fivestars.websites.onlinetest.util.SignUtil;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("user")
@Namespace("/")
@Result(name="success", location="/WEB-INF/views/index.jsp")
public class HomeAction {

	@Action("home")
	@SuppressWarnings("unchecked")
	public String home() {
		Map<String, Object> session = ServletActionContext.getContext().getSession();
		String error = (String) session.get("error");
		if(error != null && !"".equals(error)) {
			session.remove("error");
			Map<String, Object> request = (Map<String, Object>) ServletActionContext.getContext().get("request");
			request.put("error", error);
		}
		return ActionSupport.SUCCESS;
	}
	
	@Action("weixin")
	public String weixin() {
		Map request = (Map) ServletActionContext.getContext().get("request");
		// 微信加密签名  
        String signature = (String) request.get("signature");  
        // 时间戳  
        String timestamp = (String) request.get("timestamp");  
        // 随机数  
        String nonce = (String) request.get("nonce");  
        // 随机字符串  
        String echostr = (String) request.get("echostr");  
  
        HttpServletResponse response = ServletActionContext.getResponse();  
        response.setContentType("text/html;charset=UTF-8");  
        PrintWriter out;
		try {
			out = response.getWriter();
			// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
			if (SignUtil.checkSignature(signature, timestamp, nonce)) {  
	            out.print(echostr);  
	        }  
	        out.close();  
	        out = null;  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        return null;  
	}
	
}