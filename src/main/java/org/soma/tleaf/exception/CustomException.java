/**
 * 
 */
package org.soma.tleaf.exception;

/**
 * Created with Eclipse IDE
 * Author : RichardJ 
 * Date   : Oct 22, 2014 8:56:44 PM
 * Description : 
 */
public abstract class CustomException extends Exception{
	/**
	 * added Because it Causes build error from Jenkins 
	 */
	private static final long serialVersionUID = 2794121859091305294L;

	public abstract String getExceptionName();
}
