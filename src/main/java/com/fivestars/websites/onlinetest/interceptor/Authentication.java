package com.fivestars.websites.onlinetest.interceptor;

import java.util.Map;

import com.fivestars.websites.onlinetest.constant.SessionConst;
import com.fivestars.websites.onlinetest.constant.UserConst;
import com.fivestars.websites.onlinetest.model.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
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
		User user = (User) session.get(SessionConst.USER);
		if (user == null || user.getIsAdmin() == UserConst.IS_NOT_ADMIN) {
			return ActionSupport.LOGIN;
		}
		return invocation.invoke();
	}
}
