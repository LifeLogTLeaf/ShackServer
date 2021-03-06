package org.soma.tleaf.exception;

/**
 * Makes Exceptions out of CustomExceptionValue Enum Values
 * @author susu
 * @returns CustomException
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
		case Too_Much_File_Upload_Exception : return new TooMuchFileUploadedException();
		case Invalid_App_Id_Exception : return new InvalidAppIdException();
		case Json_Data_Mapping_Exception : return new JsonDataMappingException();
		case Login_Access_Code_Not_Found_Exception : return new LoginAccessCodeNotFoundException();
		case Login_Access_Code_Incorrect_Exception : return new LoginAccessKeyIncorrectException();
		
		}
		
		// Just for Default value, not the real problem
		return new DatabaseConnectionException();
		
	}
	
}