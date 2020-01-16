//点击切换验证码
$("#vercode").click(function () {
    $(this).attr("src", "/captcha?id=" + new Date().getTime());
});
//layui
layui.use(['form'], function () {
    var $ = layui.$
        , setter = layui.setter
        , admin = layui.admin
        , form = layui.form
        , router = layui.router()
        , search = router.search;
    form.on('submit(LAY-user-login-submit)', function (obj) {
        $.ajax({
            type: 'post',
            url: '/login',
            dataType: 'json',
            data: obj.field,
            success: function (res) {
                if (res.code == 200) {
                    layer.msg('登入成功', {
                        offset: '15px'
                        , icon: 1
                        , time: 1000
                    }, function () {
                        window.location.href = '/index'; //后台主页
                    });
                } else {
                    layer.msg(res.msg, {
                        offset: '15px'
                        , icon: 2
                        , time: 1000
                    });
                }
            }
        });
    });
});