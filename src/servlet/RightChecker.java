package servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class RightChecker
 */

public class RightChecker implements Filter {

    /**
     * Default constructor. 
     */
    public RightChecker() {
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
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		String uri =  httpRequest.getRequestURI();
		
		if(uri.endsWith("/Login.jsp") || uri.endsWith("/Login")||uri.endsWith("/Index.jsp")||uri.endsWith("/Index")||uri.endsWith("CQUT/"))
		{
			chain.doFilter(request, response);
			return;
			
		}
		HttpSession httpSession = httpRequest.getSession();
		if(httpSession.getAttribute("username") == null)
		{
			HttpServletResponse httpServletResponse = (HttpServletResponse)response;
			httpServletResponse.sendRedirect("Login.jsp");
		}
		else
		{
			chain.doFilter(request, response);
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
