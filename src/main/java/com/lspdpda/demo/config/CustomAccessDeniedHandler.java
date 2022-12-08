package com.lspdpda.demo.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest httpServeletRequest, HttpServletResponse httpServletResponse,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		httpServletResponse.sendRedirect(httpServeletRequest.getContextPath() + "/forbidden");

	}

}
