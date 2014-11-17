<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="login" class="bg-black" ng-controller="loginCtrl">
<head>
	<meta charset="UTF-8">
    <title>TLeaf :: Login</title>
    <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
    <!-- bootstrap 3.2.0 Latest compiled and minified CSS -->
    <link href="./resources/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <!-- font Awesome -->
    <link href="./resources/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <!-- Ionicons -->
    <link href="./resources/css/ionicons.min.css" rel="stylesheet" type="text/css" />
    <link href="./resources/css/icons-payment.css" rel="stylesheet" type="text/css" />
    <link href="./resources/css/flag-icon.css" rel="stylesheet" type="text/css" />
    <link href="./resources/css/flag-icon.css" rel="stylesheet" type="text/css" />
    <link href="./resources/css/typicons.css" rel="stylesheet" type="text/css" />
    <!-- Theme style -->
    <link href="./resources/css/bonanzooka.css" rel="stylesheet" type="text/css" />
    <link href="./resources/css/style.css" rel="stylesheet" type="text/css" />
    <link href="./resources/css/custom/login.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
    <!--컨트롤러 및 ng-app 추가 나중에 분리 되어 나갈 필요가 있을것이다-->
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.26/angular.min.js"></script>
    <script src="./resources/js/angular/angular-menu.js" type="text/javascript"></script>
    <script src="./resources/js/angular/controllers.js" type="text/javascript"></script>
    <script src="./resources/js/custom/match.js"></script>
</head>
<body class="bg-black" >

<div class="form-box" id="login-box">
    <div class="header bg-green">
        <div class="logo">
            <strong>TLeaf</strong>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-6 col-sm-6 col-xs-6 btn btn-flat bg-white" id="login" ng-class="login" value="login" ng-model="modeLogin" ng-mouseover="changeLoginMode($event)">
            <b>로그인</b>
        </div>
        <div class="col-lg-6 col-sm-6 col-xs-6 btn btn-flat btn-success" id="join" ng-class="join" value="join" ng-model="modeLogin" ng-mouseover="changeLoginMode($event)">
            <b>회원가입</b>
        </div>
    </div>
    <div  ng-switch="modeLogin">

        <!--로그인에 관한 -->
        <form ng-submit="submitLogin(user)" name="formLogin"  ng-switch-when="login">
            <div class="body bg-white">

                <div class="input-group">
                        <span class="input-group-btn">
                            <div class="btn bg-green" ><i class="fontello-user-1"></i>
                            </div>
                        </span>
                    <input type="email" name="email1" ng-model="user.email" placeholder="E-Mail" class="form-control" required>
                    <!--<i class="form-invalid fa fa-fw fa-times" ng-show="formLogin.email1.$invalid"></i>-->
                    <!--체크 아이콘 표시-->
                    <i class="form-valid fa fa-fw fa-check" ng-show="formLogin.email1.$valid"></i>
                </div>

                <br>

                <!--비밀번호-->
                <div class="input-group">
                        <span class="input-group-btn">
                            <div class="btn bg-green"><i class="fontello-lock"></i>
                            </div>
                        </span>
                    <input type="password" name="pw" ng-model="user.pw" placeholder="Password"  class="form-control"required>
                    <!--<i class="form-invalid fa fa-fw fa-times" ng-show="formLogin.pw.$invalid"></i>-->
                    <i class="form-valid fa fa-fw fa-check" ng-show="formLogin.pw.$valid"></i>
                </div>
                <div class="input-group">
                	<input type="hidden" name="code" ng-model="user.code" ng-init="user.code='${code}'" id="token" class="form-control" />
                	<input type="hidden" name="app" ng-model="user.appid" ng-init="user.appid='${appId}'" id="token" class="form-control" />
                </div>
                <br>
                <button type="submit" class="pull-right btn bg-green btn-success "ng-show="formLogin.$valid">Sign In</button>

                <div class="form-group">
                    <input type="checkbox" name="remember_me" />&nbsp; 자동로그인 사용
                </div>
                <hr class="timeline-hr">
            </div>

            <div class="footer">


                <!--  <p><a href="#">I forgot my password</a>
                </p> -->

                <p class="text-center login-with">Developed by <b>TLeaf</b>
                </p>




            </div>
        </form>
        <!--회원가입에 관한 폼-->
        <form name="form" ng-submit="submitJoin(user)" ng-switch-when="join">
            <div class="body bg-white">
                <!--이메일-->
                <div class="input-group">
                        <span class="input-group-btn">
                            <div class="btn bg-green" ><i class="fontello-user-1"></i>
                            </div>
                        </span>

                    <input type="email" name="email" ng-model="user.email" placeholder="E-Mail" class="form-control" required>
                    <!--<i class="form-invalid fa fa-fw fa-times" ng-show="form.email1.$invalid"></i>-->
                    <i class="form-valid fa fa-fw fa-check" ng-show="form.email.$valid"></i>

                </div>
                <br>
                <!--닉네임-->
                <div class="input-group">
                        <span class="input-group-btn">
                            <div class="btn bg-green" ><i class="fontello-user-1"></i>
                            </div>
                        </span>
                    <input type="text" name="nickname" ng-model="user.nickname" ng-minlength="3" class="form-control" placeholder="Nick Name (3자 이상)" required/>
                    <!--<i class="form-invalid fa fa-fw fa-times" ng-show="form.nickname.$invalid"></i>-->
                    <i class="form-valid fa fa-fw fa-check" ng-show="form.nickname.$valid"></i>
                </div>

                <br>
                <!--패스워드-->
                <div class="input-group">
                        <span class="input-group-btn">
                            <div class="btn bg-green" ><i class="fontello-lock"></i>
                            </div>
                        </span>
                    <input type="password" name="pw" ng-model="user.pw" placeholder="Password"  class="form-control"required>
                    <!--<i class="form-invalid fa fa-fw fa-times" ng-show="form.pw.$invalid"></i>-->
                    <i class="form-valid fa fa-fw fa-check" ng-show="form.pw.$valid"></i>
                </div>
                <br>
                <!--패스워드 검사-->
                <div class="input-group" >
                        <span class="input-group-btn">
                            <div class="btn bg-green" ><i class="fontello-lock"></i>
                            </div>
                        </span>
                    <input type="password" name="pw2" ng-model="user.pw2" data-match="user.pw" placeholder="Password again" class="form-control" required>
                    <!--<i class="form-invalid fa fa-fw fa-times" ng-show="form.pw2.$error.match"></i>-->
                    <i class="form-valid fa fa-fw fa-check" ng-show="!form.pw2.$error.match"></i>
                </div>

                <br>
                <!--나이-->
                <div class="input-group">
                        <span class="input-group-btn">
                            <div class="btn bg-green" ><i class="fontello-lock"></i>
                            </div>
                        </span>
                    <input type="text" name="age" ng-model="user.age" ng-pattern="/^[0-9]{1,2}$/" placeholder="age" class="form-control" required>
                    <!--<i class="form-invalid fa fa-fw fa-times" ng-show="form.age.$error.pattern"></i>-->
                    <i class="form-valid fa fa-fw fa-check" ng-show="!form.age.$error.pattern"></i>
                </div>
                
				<div class="input-group">
                	<input type="hidden" name="code" ng-model="user.code" ng-init="user.code='${code}'" id="token" class="form-control" />
                	<input type="hidden" name="app" ng-model="user.appid" ng-init="user.appid='${appId}'" id="token" class="form-control" />
                </div>
                <br>

                <button type="submit" class="pull-right btn bg-green btn-success" ng-show="form.$valid">Join in</button>
                <br>
                <br>
                <br>

                <div>
                    <hr class="timeline-hr">
                </div>
                <div class="footer">


                    <!--  <p><a href="#">I forgot my password</a>
                    </p> -->

                    <p class="text-center login-with">Developed by <b>TLeaf</b>
                    </p>


                </div>

            </div>


        </form>


    </div>
</div>


<!-- jQuery 2.0.2 -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
<!-- Bootstrap -->
<script src="./resources/js/bootstrap.min.js" type="text/javascript"></script>

</body>
</html>