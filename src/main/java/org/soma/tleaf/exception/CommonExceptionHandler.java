package org.soma.tleaf.exception;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tleaf.domain.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created with Eclipse IDE
 * Author : RichardJ
 * Date : Oct 22, 2014 7:38:30 PM
 * Description : 서버 전체에 대한 예외를 처리하는 컨트롤러 클래스입니다.
 */
@ControllerAdvice
public class CommonExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger( CommonExceptionHandler.class );
	
	/**
	 * Author : RichardJ
	 * Date : Oct 22, 2014 10:14:23 PM
	 * Description : 인증키에 관한 오류를 처리하는 메소드입니다.
	 */
	@ExceptionHandler({ ExpiredAccessKeyException.class, InvalidAccessKeyException.class, WrongAuthenticationInfoException.class,
			NoSuchUserException.class, AuthInfoInsufficientException.class, InvalidAppIdException.class, LoginAccessCodeNotFoundException.class , LoginAccessKeyIncorrectException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public ErrorResponse handleExpiredAccessKeyException(CustomException e, HttpServletResponse response) {

		logger.error(e.getExceptionName());
		ErrorResponse errorLog = new ErrorResponse();
		errorLog.setError(e.getExceptionName());
		errorLog.setReason(e.getMessage());
		return errorLog;
	}

	/**
	 * Author : RichardJ
	 * Date : Oct 22, 2014 10:14:26 PM
	 * Description : 반드시 들어와야하는 요청파라미터를 생략했을때를 발생하는 오류를 처리하는 메소드입니다.
	 */
	@ExceptionHandler({ ParameterInsufficientException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleMissingServletRequestParameterException(CustomException e, HttpServletResponse response) {

		logger.error(e.getExceptionName());
		ErrorResponse errorLog = new ErrorResponse();
		errorLog.setError(e.getExceptionName());
		errorLog.setReason(e.getMessage());
		return errorLog;
	}

	/**
	 * 
	 * @author susu
	 *         Date Oct 26, 2014 1:58:02 PM
	 */
	@ExceptionHandler({ EmailAlreadyExistException.class })
	@ResponseStatus(HttpStatus.SEE_OTHER)
	@ResponseBody
	public ErrorResponse handleUnacceptableUserRequestException(CustomException e, HttpServletResponse response) {

		logger.error(e.getExceptionName());
		ErrorResponse errorLog = new ErrorResponse();
		errorLog.setError(e.getExceptionName());
		errorLog.setReason(e.getMessage());
		return errorLog;
	}
	
	/**
	 * Handles Internal Error Exceptions
	 * @author susu
	 * Date Nov 15, 2014 11:31:53 AM
	 * @param e
	 * @return
	 */
	@ExceptionHandler( {DatabaseConnectionException.class } )
	@ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
	@ResponseBody
	public ErrorResponse handleInternalServerErrorException( CustomException e ) {
		
		logger.error(e.getExceptionName());
		ErrorResponse errorLog = new ErrorResponse();
		errorLog.setError(e.getExceptionName());
		errorLog.setReason(e.getMessage());
		
		return errorLog;
		
	}

	/**
	 * Author : RichardJ
	 * Date : Oct 28, 2014 9:13:02 PM
	 * Description :
	 * 
	 * @param e 우리가 해결하지 모한 예외
	 * @param response
	 * @return
	 */
//	@ExceptionHandler()
//	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//	@ResponseBody
//	public ErrorResponse handleInternalException(Exception e, HttpServletResponse response) {
//		ErrorResponse errorLog = new ErrorResponse();
//		errorLog.setError("Internal Error");
//		errorLog.setReason("this is internal error. we will fix soon");
//		return errorLog;
//	}

}
