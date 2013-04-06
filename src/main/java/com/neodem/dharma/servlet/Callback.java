
package com.neodem.dharma.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

import com.facebook.api.FacebookException;
import com.facebook.api.FacebookRestClient;
import com.facebook.api.ProfileField;
import com.neodem.dharma.facebook.RequestBean;

/**
 * Servlet implementation class for Servlet: Login
 * 
 */
public class Callback extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(Callback.class.getName());

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public Callback() {
		super();
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doGet()");
		
		RequestBean bean = new RequestBean(request);
		log.debug(bean);
		
		if(bean.isInstalled()) {
			// the app has just been installed
		} else {
			// ignore
		}
		
		log.debug("doGet() - complete");
	}

	private void playWithFacebookAPI(HttpServletRequest request, FacebookRestClient facebookClient) {
		try {
			int myId = facebookClient.users_getLoggedInUser();

			EnumSet<ProfileField> fields = EnumSet.of(com.facebook.api.ProfileField.NAME,
					com.facebook.api.ProfileField.PIC);

			Collection<Integer> users = new ArrayList<Integer>();
			users.add(new Integer(myId));

			Document d = facebookClient.users_getInfo(users, fields);
			String name = d.getElementsByTagName("name").item(0).getTextContent();
			String picture = d.getElementsByTagName("pic").item(0).getTextContent();

			d = facebookClient.friends_get();
			int numOfFriends = d.getChildNodes().getLength();

			request.setAttribute("name", name);
			request.setAttribute("picture", picture);
			request.setAttribute("friends", new Integer(numOfFriends));

		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		log.debug("doPost()");

		RequestBean bean = new RequestBean(request);
		log.debug(bean);

		String apiKey = getServletConfig().getInitParameter("API_KEY");
		String secretKey = getServletConfig().getInitParameter("SECRET_KEY");

		String sessionKey = bean.getSigSessionKey();
		log.debug("sessionKey = '" + sessionKey + "'");

		FacebookRestClient facebookClient = null;

		if (bean.isUserInFacebook()) {
			if (bean.isUserInCorrectApp(apiKey)) {
				if (bean.isUserGrantedAccess()) {
					if (bean.hasUserAddedApp()) {
						facebookClient = new FacebookRestClient(bean.getSigApiKey(), secretKey, bean.getSigSessionKey());
					} else {
						// prompt user to add app
					}
				} else {
					// user not granted access
				}
			} else {
				// return some error page
			}
		} else {
			// 4) The user is not logged in to Facebook.
			log.debug("user not logged in to facebook.. redirecting to login page");
			response.sendRedirect("http://www.facebook.com/login.php?api_key=" + apiKey + "&v=1.0");
			return;
		}

		log.debug("getting facebook data");
		playWithFacebookAPI(request, facebookClient);

		log.debug("sending to callback.jsp");
		request.getRequestDispatcher("callback.jsp").forward(request, response);
		
		log.debug("doPost() - complete");
	}

}