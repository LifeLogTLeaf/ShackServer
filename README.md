ShackServer
===========
This is TLeaf API Server

###### author Swimyoung & Susu 

**To Use Shack API, contact us at tleafshack@gmail.com FIRST**

###### Once you are Registered, use can start using TLeaf API.

1. Make Login with TLeaf
--------
From now on, I will use "HOME" instead of "http://14.63.171.66:8081/tleafstructure"

Open up a new tab and call
```
HOME/oauth?appId=<your_application_id>
GET HTTP/1.0
```

Once the TLeaf User finishes Authrization, You will have the User's Access Key which looks like
```json
{
   "_id": "3dc93a3c4aeaebde2b910777a80016b1",
   "userId": "344bc889c8bb44dd6e4bb845d40007b9",
   "appId": "41bfdd372e1d60c37baba4cdec003b0f",
   "validFrom": "2014-11-15T11:56:33+09:00",
   "validTo": "2014-11-16T11:56:33+09:00",
   "valid": true
}
```

2. Use TLeaf API
--------
Now You can use the APIs below ( HOME/api/* ) by Setting custom headers with AccessKey.

Request,
```
GET /tleafstructure/api/user HTTP/1.1
Host: 14.63.171.66:8081
Connection: keep-alive
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36
Cache-Control: no-cache
x-tleaf-user-id: 344bc889c8bb44dd6e4bb845d40007b9
x-tleaf-application-id: 6b22f647ef8f2f3278a1322d8b000f81
x-tleaf-access-token: 6b22f647ef8f2f3278a1322d8b000210
Accept: */*
```

And You will get Something like this.
```json
{
    "hashId": "344bc889c8bb44dd6e4bb845d40007b9",
    "rev": "1-fbc25ffde83d3429f85ff22374661784",
    "nickname": "DevSusu",
    "email": "os1742@gmail.com",
    "password": "123456",
    "gender": "male",
    "age": 19,
    "services": null
}
```
as the Response. Piece of Cake! Isn't it?

Here is the sample Request
```javascript

var url = "http://14.63.171.66:8081/tleafstructure/api/user";
              
$.ajax({
	type : 'GET',
	beforeSend: function ( request )
	{
        	request.setRequestHeader( "X-Tleaf-User-Id", "344bc889c8bb44dd6e4bb845d40007b9" );
        	request.setRequestHeader( "X-Tleaf-Application-Id", "6b22f647ef8f2f3278a1322d8b000f81" );
        	request.setRequestHeader( "X-Tleaf-Access-Token", "6b22f647ef8f2f3278a1322d8b000210" );
	},
	url : url,
	async : true,
	contentType : 'application/json',
	success : function(response) {
		console.log(response);
		alert(response);
	},
	error : function(xhr) {
		alert('Error!  Status = ' + xhr.status + " Message = " + xhr.statusText);
	}
});
```

3. More APIs
--------
figure out more APIs below.
Would be easier to UnderStand if you use JSON formatter ( or Request with POSTMAN )

* http://14.63.171.66:8081/tleafstructure/api-docs
* http://14.63.171.66:8081/tleafstructure/api-docs/default/oauth-controller
* http://14.63.171.66:8081/tleafstructure/api-docs/default/rest-api-controller
