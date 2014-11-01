package org.soma.tleaf.exception;

/**
 * Makes Exceptions out of Enum Values
 * @author susu
 * Date : Oct 26, 2014 2:06:49 PM
 */
public class CustomExceptionFactory {

	public CustomException createCustomException( CustomExceptionValue customException ) {
		
		switch ( customException ) {
		
		case Database_Connection_Exception : return new DatabaseConnectionException();
		case Email_Already_Exist_Exception : return new EmailAlreadyExistException();
		case Expired_AccessKey_Exception : return new ExpiredAccessKeyException();
		case Invalid_AccessKey_Exception : return new InvalidAccessKeyException();
		case No_Such_User_Exception : return new NoSuchUserException();
		case Parameter_Insufficient_Exception : return new ParameterInsufficientException();
		case Wrong_Authentication_Exception : return new WrongAuthenticationInfoException();
		case Auth_Info_Insufficient_Exception : return new AuthInfoInsufficientException();
		case No_Such_Document_Exception : return new NoSuchDocumentException();
		
		}
		
		// Just for Default value, not the real problem
		return new DatabaseConnectionException();
		
	}
	
}