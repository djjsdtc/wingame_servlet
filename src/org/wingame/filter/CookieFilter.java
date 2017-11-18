package org.wingame.filter;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.wingame.util.UserUtil;

/**
 * Servlet Filter implementation class CookieFilter
 */
public class CookieFilter implements Filter {
    /**
     * Default constructor. 
     */
    public CookieFilter() {
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
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		UserUtil util = new UserUtil((Connection) req.getServletContext().getAttribute("conn"));
		//��Cookie����ȡ��¼��Ϣ
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		
		//����Session
		if(username != null){
			//��Session���Ͳ���Ҫ������ȡ��¼��Ϣ��
		}
		//��Session
		else{
			//����Cookie
			Cookie[] cookies = request.getCookies();
			String password = null;
			if(cookies != null){
				for(Cookie cookie : cookies){
					//��ȡ�û�������
					if(cookie.getName().equals("j2eeworkname")){
						username = cookie.getValue();
						System.out.println("�����û����ˣ�"+username);
					}
					if(cookie.getName().equals("j2eeworksign")){
						password = cookie.getValue();
						System.out.println("���������ˣ�"+password);
					}
				}
			}
			if(username != null && password != null && util.check(username, password)){
				//��Cookie������֤ͨ��
				session.setAttribute("username", username);
				System.out.println("дSession��");
			}
			else{
				//��Cookie��������֤��ͨ��
				Cookie nmcookie = new Cookie("j2eeworkname",null);
				nmcookie.setMaxAge(0);
				nmcookie.setPath("/");
				response.addCookie(nmcookie);
				Cookie pwcookie = new Cookie("j2eeworksign",null);
				pwcookie.setMaxAge(0);
				pwcookie.setPath("/");
				response.addCookie(pwcookie);
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
