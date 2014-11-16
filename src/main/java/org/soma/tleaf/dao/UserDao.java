package org.soma.tleaf.dao;

import org.soma.tleaf.accesskey.AccessKey;
import org.soma.tleaf.domain.UserInfo;
import org.soma.tleaf.exception.CustomException;

public interface UserDao {
	
	/** Creates user Database, User HashId
	 * 
	 * @author susu
	 * Date Nov 3, 2014 3:52:12 PM
	 * @param userInfo
	 * @return JSON String about the SignUp Process 
	 * @throws CustomException If Email Already Exists or Failed to Connect to Database
	 */
	public String userSignUp( UserInfo userInfo ) throws CustomException;
	
	/** Creates an Access Token for the Specific User
	 * 
	 * @author susu
	 * Date Nov 3, 2014 4:07:45 PM
	 * @param email String ( full email )
	 * @param password String ( full password )
	 * @return AccessKey AccessKey Object of the Specific User.
	 * @throws CustomException If Authentication Info is Wrong or Failed to Connect to Database
	 */
	public AccessKey userLogin( String email, String password, String appId ) throws CustomException;
	
	/** Deletes User Database, UserInfo Data
	 * 
	 * @author susu
	 * Date Nov 3, 2014 4:10:17 PM
	 * @param email String ( full email )
	 * @param password String ( full password )
	 * @return JSON String about the SignUp Process 
	 * @throws CustomException If Authentication Info is Wrong or Failed to Connect to Database
	 */
	public String userSignOut( String email, String password ) throws CustomException;
	

}