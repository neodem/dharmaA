
package com.neodem.dharma.facebook;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

public class RequestBean {

	private String sigInCanvas;

	private String sigAdded;

	private String sigTime;

	private String sigApiKey;

	private String sig;

	private String sigUser;

	private String sigFriends;

	private String sigSessionKey;

	private String sigExpires;

	private String installed;

	private String authToken;

	public RequestBean(HttpServletRequest request) {
		setSigInCanvas((String) request.getParameter("fb_sig_in_canvas"));
		setSigAdded((String) request.getParameter("fb_sig_added"));
		setSigTime((String) request.getParameter("fb_sig_time"));
		setSigApiKey((String) request.getParameter("fb_sig_api_key"));
		setSig((String) request.getParameter("fb_sig"));
		setSigUser((String) request.getParameter("fb_sig_user"));
		setSigFriends((String) request.getParameter("fb_sig_friends"));
		setSigSessionKey((String) request.getParameter("fb_sig_session_key"));
		setSigExpires((String) request.getParameter("fb_sig_profile_update_time"));
		setInstalled((String) request.getParameter("installed"));
		setAuthToken((String) request.getParameter("auth_token"));
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * @return the sigInCanvas
	 */
	public String getSigInCanvas() {
		return sigInCanvas;
	}

	/**
	 * @param sigInCanvas
	 *            the sigInCanvas to set
	 */
	public void setSigInCanvas(String sigInCanvas) {
		this.sigInCanvas = sigInCanvas;
	}

	/**
	 * @return the sigAdded
	 */
	public String getSigAdded() {
		return sigAdded;
	}

	/**
	 * @param sigAdded
	 *            the sigAdded to set
	 */
	public void setSigAdded(String sigAdded) {
		this.sigAdded = sigAdded;
	}

	/**
	 * @return the sigTime
	 */
	public String getSigTime() {
		return sigTime;
	}

	/**
	 * @param sigTime
	 *            the sigTime to set
	 */
	public void setSigTime(String sigTime) {
		this.sigTime = sigTime;
	}

	/**
	 * @return the sigApiKey
	 */
	public String getSigApiKey() {
		return sigApiKey;
	}

	/**
	 * @param sigApiKey
	 *            the sigApiKey to set
	 */
	public void setSigApiKey(String sigApiKey) {
		this.sigApiKey = sigApiKey;
	}

	/**
	 * @return the sig
	 */
	public String getSig() {
		return sig;
	}

	/**
	 * @param sig
	 *            the sig to set
	 */
	public void setSig(String sig) {
		this.sig = sig;
	}

	/**
	 * @return the sigUser
	 */
	public String getSigUser() {
		return sigUser;
	}

	/**
	 * @param sigUser
	 *            the sigUser to set
	 */
	public void setSigUser(String sigUser) {
		this.sigUser = sigUser;
	}

	/**
	 * @return the sigFriends
	 */
	public String getSigFriends() {
		return sigFriends;
	}

	/**
	 * @param sigFriends
	 *            the sigFriends to set
	 */
	public void setSigFriends(String sigFriends) {
		this.sigFriends = sigFriends;
	}

	/**
	 * @return the sigSessionKey
	 */
	public String getSigSessionKey() {
		return sigSessionKey;
	}

	/**
	 * @param sigSessionKey
	 *            the sigSessionKey to set
	 */
	public void setSigSessionKey(String sigSessionKey) {
		this.sigSessionKey = sigSessionKey;
	}

	/**
	 * @return the sigExpires
	 */
	public String getSigExpires() {
		return sigExpires;
	}

	/**
	 * @param sigExpires
	 *            the sigExpires to set
	 */
	public void setSigExpires(String sigExpires) {
		this.sigExpires = sigExpires;
	}

	/**
	 * @return the installed
	 */
	public String getInstalled() {
		return installed;
	}

	/**
	 * @param installed
	 *            the installed to set
	 */
	public void setInstalled(String installed) {
		this.installed = installed;
	}

	/**
	 * @return the authToken
	 */
	public String getAuthToken() {
		return authToken;
	}

	/**
	 * @param authToken
	 *            the authToken to set
	 */
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	/**
	 * we determine this by checking the presense of the api key value
	 * 
	 * @return
	 */
	public boolean isUserInFacebook() {
		return StringUtils.isNotBlank(sigApiKey);
	}

	/**
	 * we determine this by checking the presense of a valid userID
	 * 
	 * @return
	 */
	public boolean isUserGrantedAccess() {
		return StringUtils.isNotBlank(sigUser);
	}

	/**
	 * if added, the sigAdded string will contain 1
	 * 
	 * @return
	 */
	public boolean hasUserAddedApp() {
		if (StringUtils.isNotBlank(sigAdded)) {
			int val = -1;
			try {
				val = Integer.parseInt(sigAdded);
			} catch (NumberFormatException e) {
				return false;
			}
			if (val == 1) {
				return true;
			}
		}

		return false;
	}

	public boolean isUserInCorrectApp(String apiKey) {
		return StringUtils.equals(apiKey, getSigApiKey());
	}

	/**
	 * there will be an auth token and installed == 1
	 * 
	 * @return
	 */
	public boolean isInstalled() {
		if (StringUtils.isNotBlank(installed)) {
			int val = -1;
			try {
				val = Integer.parseInt(installed);
			} catch (NumberFormatException e) {
				return false;
			}
			if (val == 1) {
				if (StringUtils.isNotBlank(authToken)) {
					return true;
				}
			}
		}
		return false;
	}
}
