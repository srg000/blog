﻿

layui.define(['element', 'layer', 'form'], function (exports) {
    var form = layui.form;
    var $ = layui.jquery;
    //自定义验证
    form.verify({
        passWord: [/^[\S]{6,16}$/, '密码必须6到16位'],
        account: function (value) {
            if (value.length <= 0 || value.length > 10) {
                return "账号必须1到10位"
            }
            var reg = /^[a-zA-Z0-9]*$/;
            if (!reg.test(value)) {
                return "账号只能为英文或数字";
            }
        }
    });
    //监听登陆提交
    form.on('submit(login)', function (data) {
        var index = layer.load(1);
        setTimeout(function () {
            //模拟登陆
            layer.close(index);
            if (data.field.account != 'admin' || data.field.password != 'xiaoqianbai') {
                layer.msg('账号或者密码错误', { icon: 5 });
            } else {
                layer.msg('登陆成功，正在跳转......', { icon: 6 });
                layer.closeAll('page');
                setTimeout(function () {
                    location.href = "/admin/main";
                }, 1000);
            }
        }, 400);
        return false;
    });
    //检测键盘按下
    $('body').keydown(function (e) {
        if (e.keyCode == 13) {  //Enter键
            if ($('#layer-login').length <= 0) {
                login();
            } else {
                $('button[lay-filter=login]').click();
            }
        }
    });

    $('.enter').on('click', login);

    function login() {
        var loginHtml = ''; //静态页面只能拼接，这里可以用iFrame或者Ajax请求分部视图。html文件夹下有login.html

        loginHtml += '<br/>';
        loginHtml += '<br/>';
        loginHtml += '<form class="layui-form" action="">';
        loginHtml += '<div class="layui-form-item">';
        loginHtml += '<label class="layui-form-label">账号</label>';
        loginHtml += '<div class="layui-input-inline pm-login-input">';
        loginHtml += '<input type="text" name="account" lay-verify="account|required" placeholder="请输入账号"  autocomplete="off" class="layui-input">';
        loginHtml += '</div>';
        loginHtml += '</div>';
        loginHtml += '<div class="layui-form-item">';
        loginHtml += '<label class="layui-form-label">密码</label>';
        loginHtml += '<div class="layui-input-inline pm-login-input">';
        loginHtml += '<input type="password" name="password" lay-verify="passWord|required" placeholder="请输入密码"  autocomplete="off" class="layui-input">';
        loginHtml += '</div>';
        loginHtml += '</div>';
        loginHtml += '<div class="layui-form-item" style="margin-top:25px;margin-bottom:0;">';
        loginHtml += '<div class="layui-input-block">';
        loginHtml += ' <button class="layui-btn" style="width:230px;" lay-submit="" lay-filter="login">立即登录</button>';
        loginHtml += ' </div>';
        loginHtml += ' </div>';
        loginHtml += '</form>';

        layer.open({
            id: 'layer-login',
            type: 1,
            title: false,
            shade: 0.4,
            shadeClose: true,
            area: ['480px', '270px'],
            closeBtn: 0,
            anim: 1,
            skin: 'pm-layer-login',
            content: loginHtml
        });
        layui.form().render('checkbox');
    }

    exports('index', {});
});

