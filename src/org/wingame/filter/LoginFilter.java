package org.wingame.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LoginFilter
 */
public class LoginFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public LoginFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		// 有无Session
		if (username != null) {
			// 有Session
			filter(request, response, chain, true);
		} else {
			// 无Session
			filter(request, response, chain, false);
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	private void filter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain, boolean isLogin)
			throws IOException, ServletException {
		if (isLogin) {
			if (request.getServletPath().startsWith("/userlogin/")) {
				// 如果已经登录，但又要访问登录页面
				response.sendRedirect(request.getContextPath() + "/");
			} else {
				chain.doFilter(request, response);
			}
		} else {
			if (request.getServletPath().startsWith("/userlogin/")) {
				chain.doFilter(request, response);
			} else {
				String query = request.getQueryString();
				if(query == null) query = "";
				else query = "?" + query;
				response.sendRedirect(request.getContextPath()
						+ "/userlogin/?redirect="
						+ request.getServletPath().substring(1)+query);
			}
		}
	}
}
