This is the Simple API Manual for TLeaf
===========

HOME = http://14.63.171.66:8081/tleafstructure 

Login API
--------
* Request URL : HOME/oauth
* Request Method : GET
* Result Paramater : ( required, value = "appId" )
* Result : TLeaf Login Page.

![See Screenshot](https://github.com/LifeLogTLeaf/ShackServerTest/blob/master/example_oauth_GET.png)


User API
--------
##### Fetching UserInfo
* Request URL : HOME/api/user
* Request Method : GET
* Request Paramater : ( required, value = "appId" )
* Result : UserInfo Object

##### Fetching User Application Statistic ( Number of Logs )
* Request URL : HOME/api/user/statistic
* Request Method : GET
* Result : User Application Statistic ( How much logs each application has )

##### Uploading File Data ( Image, )
* Request URL : HOME/api/user/file
* Request Method : POST , MULTIPART_FORM_DATA_VALUE
* Request Body : String docId, String docRev, File[] files
curl -F "docId=document_id" -F "docRev=document_rev" -F "file=@/Users/susu/Desktop/example.png" -F "file=@/ ...
* Request Header : x-tleaf-application-id , x-tleaf-user-id, x-tleaf-access-token
* Result : Changed Revision of the document

![See Screenshot](https://github.com/LifeLogTLeaf/ShackServerTest/blob/master/example_api-user-file_POST.png)

##### Deleting File Data ( Image, )
* Request URL : HOME/api/user/file
* Request Method : DELETE
* Request Paramater : String docId, String docRev
* Request Header : x-tleaf-application-id , x-tleaf-user-id, x-tleaf-access-token
* Result : Changed Revision of the document

##### Fetching Specific User Log
* Request URL : HOME/api/user/log
* Request Method : GET
* Request Parameter : String rawDataId
* Request Header : x-tleaf-application-id , x-tleaf-user-id, x-tleaf-access-token
* Result : The Specific User Log

##### Deleting Specific User Log
* Request URL : HOME/api/user/log
* Request Method : DELETE
* Request Parameter : String rawDataId
* Request Header : x-tleaf-application-id , x-tleaf-user-id, x-tleaf-access-token
* Result : Deleted _rev of the Specific User Log

##### Updating User Log
* Request URL : HOME/api/user/log
* Request Method : POST
* Request BODY : RawData Object ( id, rev is ESSENTIAL )
```json
{
   "id" : "document_id",
   "rev" : "document_rev",
   "data" : { "content" : "I used TLeaf API Today, and it was Awesome!!" , "date" : "2014-10-30T21:03:54+09:00" }
}
```
* Request Header : x-tleaf-application-id , x-tleaf-user-id, x-tleaf-access-token
* Result : Changed _rev of the user document

##### Fetching Specific User Log within the given time
* Request URL : HOME/api/user/date
* Request Method : GET
* Request Parameter : String[] date
* Request Header : x-tleaf-application-id , x-tleaf-user-id, x-tleaf-access-token
* Result : The Specific User Log

![See Screenshot](https://github.com/LifeLogTLeaf/ShackServerTest/blob/master/example_api-user-date.png)
