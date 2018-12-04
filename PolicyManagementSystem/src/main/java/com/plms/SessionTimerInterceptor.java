package com.plms;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SessionTimerInterceptor extends HandlerInterceptorAdapter {

	private static Logger log = LoggerFactory.getLogger(SessionTimerInterceptor.class);

	private static final long MAX_INACTIVE_SESSION_TIME = 10 * 10000;

	/**
	 * @Autowired private HttpSession session;
	 */

	/**
	 * Executed before actual handler is executed
	 **/

	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
			throws Exception {
		long startTime = System.currentTimeMillis();
		request.setAttribute("executionTime", startTime);
		HttpSession session = request.getSession();
		if (System.currentTimeMillis() - session.getLastAccessedTime() > MAX_INACTIVE_SESSION_TIME) {
			log.warn("Logging out, due to inactive session");
			request.logout();
			request.setAttribute("loginError", "Session Expired due to Inactivity. Please login again");

			RequestDispatcher dispatcher =

					request.getRequestDispatcher("/login");
			if (dispatcher != null)
				dispatcher.forward(request, response);

		}
		return true;
	}

	/**
	 * Executed before after handler is executed
	 **/
	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
			final ModelAndView model) throws Exception {
	}
}
