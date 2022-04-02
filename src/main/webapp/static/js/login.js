var Login = new Vue({
    el: "#login",
    data: {
        user: {
            username: "",
            password: "",
            captcha: "",
        },
        easyCaptcha: "/MyNetDisk/captcha" + "?d=" + new Date()
    },
    methods: {
        toLogon: function () {
            window.location.href = "/MyNetDisk/logon";
        },
        getCaptcha: function () {
            this.easyCaptcha = "/MyNetDisk/captcha" + "?d=" + new Date();
        },
        loginClick: function () {
            var that = this;
            if (this.user.username == "" || this.user.password == "" || this.user.captcha == "") {
                alert("输入框不能为空！");
            } else {
                axios({
                    method: "post",
                    url: "/MyNetDisk/userLogin",
                    data: that.user
                }).then(function (response) {
                    if (response.data.result == 1) {
                        alert(response.data.message);
                        window.location.href = "/MyNetDisk";
                        window.location.reload();
                    } else {
                        alert(response.data.message);
                        that.easyCaptcha = "/MyNetDisk/captcha" + "?d=" + new Date();
                    }
                }).catch(function (error) {
                    console.log(error);
                })
            }
        }
    }
})
