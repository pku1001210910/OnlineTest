package com.fivestars.websites.onlinetest.interceptor;

import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.fivestars.websites.onlinetest.constant.SessionConst;
import com.fivestars.websites.onlinetest.model.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import lombok.Setter;

/**
 * The {@link Authentication} authorize users while access pages.
 * 
 */

public class Authentication extends AbstractInterceptor {
	private static final long serialVersionUID = 8126607448307547295L;

	@Setter
	private String role;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		String namespace = ServletActionContext.getActionMapping().getNamespace();
		boolean needAdmin = namespace.contains(SessionConst.ADMIN_NAMESPACE);
		
		User user = needAdmin ? (User) session.get(SessionConst.ADMIN) : (User) session.get(SessionConst.USER);
		if(user == null) {
			return needAdmin ? "admin_login" : "user_home";
		}
		return invocation.invoke();
	}
}
