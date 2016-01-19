package com.fivestars.websites.onlinetest.action;

import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.fivestars.websites.onlinetest.model.User;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("user")
@Namespace("/")
@Result(name="success", location="/WEB-INF/views/index.jsp")
public class HomeAction {

	@Action("home")
	public String home() {
		Map session = ServletActionContext.getContext().getSession();
		String error = (String) session.get("error");
		if(error != null && !"".equals(error)) {
			session.remove("error");
			Map request = (Map) ServletActionContext.getContext().get("request");
			request.put("error", error);
		}
		return ActionSupport.SUCCESS;
	}
}