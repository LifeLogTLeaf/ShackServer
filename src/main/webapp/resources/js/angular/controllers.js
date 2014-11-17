'use strict';

function loginCtrl($scope,$http) {
	var dataforandroid;
    //ng-switch에서 사용.
    //커서가 어디쪽을 가리키는지 명시한다.
    //'login'과 'join' 둘중 하나이다.
    $scope.modeLogin = 'login';
//    console.log(getCookie('userId'));


    //로그인과 회원가입의 폼을 바꾸며 보여준다
    $scope.changeLoginMode = function(event) {
        if ($(event.target).hasClass('btn-success')) {
            // 현재 모드를 각인시켜서 폼을 변경시킨다
//            console.log($(event.target).attr('value'));
            $scope.modeLogin = $(event.target).attr('value');

            $('.bg-white').removeClass('bg-white').addClass(
                'btn-success');
            $(event.target).removeClass('btn-success')
                .addClass('bg-white');
        }

    }


    $scope.isSamePw = true;
    $scope.submitJoin = function (user) {
        var data = JSON.stringify(
            {
                email: user.email ,
                password:user.pw ,
                nickname:user.nickname ,
                gender: user.male ,
                age:user.age,
                code:user.code,
                appId:user.appid });
        var url = './oauth/signup';
        $http({method: 'POST',
            url: url,
            headers: {'Content-Type': 'application/json'},
            data: data

        }).success(function(data, status, headers, config) {
            console.log('회원가입 성공 succenss');
            
            //쿠키에 유저 id저장
            console.log(data);
            setCookie('userId',data.userId,5);
        }).
            error(function(data, status, headers, config) {

                console.log('회원가입 실패 fail');
                console.log(data);
                if(data.error == 'Email Already Exists'){
                    $('.row').append('<p class="text-red">이메일이 중복되었습니다</p>')
                }

            });

    };


    $scope.submitLogin = function (user) {
    	console.log(user);
        var data = JSON.stringify({ email: user.email , password:user.pw , code:user.code , appId:user.appid });
        $http({method: 'POST',
            url: './oauth/login',
            headers: {'Content-Type': 'application/json'},
            data: data

        }).success(function(data, status, headers, config) {
            console.log('로그인 성공');
            
            // callAndroid 에서 넘겨줄 변수
            dataforandroid = data;
            //paretnt Frame에 값 전
            parent.accesskey( data );
            //쿠키에 유저 id저장
            console.log(data);
            setCookie('accessKey',data.accessKey,5);
        }).
            error(function(data, status, headers, config) {

                console.log('회원가입 실패 fail');
                console.log(data);
                if(data.error == 'There is No Such User with the Email'){
                    $('.row').append('<p class="text-red">이메일 또는 비밀번호를 확인하세요</p>')
                }

            });
    }


    function callAndroid () {
    	console.log( dataforandroid );
    	window.androidJS.callAndroid( dataforandroid );
    }


    //쿠키를 생성합니다, key, value, 보관일수 순서의 파라미터
    function setCookie(cName, cValue, cDay){
        console.log('asdf');
        var expire = new Date();

        //시나 분 등으로 저장도 가능해보임
        expire.setDate(expire.getDate() + cDay);
        console.log('sdfds');
        var cookies;
        cookies = cName + '=' + escape(cValue) + '; path=/ '; // 한글 깨짐을 막기위해 escape(cValue)를 합니다.
//    console.log(cName + '=' + escape(cValue) + '; path=/ ');
        if(typeof cDay != 'undefined') cookies += ';expires=' + expire.toGMTString() + ';';
        document.cookie = cookies;
    }

// 쿠키 가져오기
    function getCookie(cName) {
        cName = cName + '=';
        var cookieData = document.cookie;
        var start = cookieData.indexOf(cName);
        var cValue = '';
        if(start != -1){
            start += cName.length;
            var end = cookieData.indexOf(';', start);
            if(end == -1)end = cookieData.length;
            cValue = cookieData.substring(start, end);
        }
        return unescape(cValue);
    }

}


