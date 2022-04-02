var Logon = new Vue({
    el: "#logon",
    data: {
        user: {
            username: "",
            password: "",
            confirm: "",
            email: "",
            captcha: "",
        },
        canNot: false,
        forToken: "获取验证码"
    },
    methods: {
        getToken: function () {
            var that = this;

            if (that.user.email == "") {
                alert("邮箱不能为空");
            } else {

                axios({
                    method: "post",
                    url: "/MyNetDisk/getEmailToken",
                    data: that.user
                }).then(function (response) {
                    if (response.data.result == 1) {
                        console.log(response.data.message);

                        //将按钮置灰倒计时
                        that.canNot = true;
                        var time = 60;
                        var timer = setInterval(function () {
                            if (time == -1) {
                                clearInterval(timer);
                                that.canNot = false;
                                that.forToken = "获取验证码";
                            } else {
                                that.forToken = "获取验证码(" + time + "秒)";
                                time--;
                            }
                        }, 1000);

                    } else {
                        alert(response.data.message);
                    }
                }).catch(function (error) {
                    console.log(error);
                });
            }
            ;
        },
        logonClick: function () {
            var that = this;
            if (that.user.username == "" || that.user.password == "" || that.user.confirm == "" || that.user.email == "" || that.user.captcha == "") {
                alert("输入框不能为空！");
            } else {
                axios({
                    method: "post",
                    url: "/MyNetDisk/userLogon",
                    data: that.user
                }).then(function (response) {
                    if (response.data.result == 1) {
                        alert(response.data.message);
                        window.location.href = "/MyNetDisk/login";
                    } else {
                        alert(response.data.message);
                    }
                }).catch(function (error) {
                    console.log(error);
                })
            }
        },
        toLogin: function () {
            window.location.href = "/MyNetDisk";
        }
    }
})
