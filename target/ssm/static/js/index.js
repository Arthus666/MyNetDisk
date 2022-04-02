let Header = new Vue({
    el: "#header",
    data: {
        person: "<ul class=\"nav navbar-nav navbar-right\">\n" +
            "            <li class=\"dropdown\">\n" +
            "              <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">个人中心<span class=\"caret\"></span></a>\n" +
            "              <ul class=\"dropdown-menu\">\n" +
            "                <li><a href=\"#\" onclick='getUserInformation()' style='text-align: center;font-size: small'>账户信息</a></li>\n" +
            "                <li role=\"separator\" class=\"divider\"></li>\n" +
            "                <li><a href=\"#\" onclick='exitLogin()' style='text-align: center;font-size: small'>退出登录</a></li>\n" +
            "              </ul>\n" +
            "            </li>\n" +
            "          </ul>",
        canNot: false,
        uploadProcess: "上传"
    },
    methods: {
        upload: function () {

            var that = this;
            that.uploadProcess = "上传中...";
            that.canNot = true;

            let formData = new FormData();
            formData.append("file", this.$refs.file.files[0]);
            axios({
                method: "post",
                url: "/MyNetDisk/fileUpload",
                headers: {
                    'Content-Type': 'multipart/form-data'
                },
                data: formData
            }).then(function (response) {
                if (response.data.result == 1) {

                    alert(response.data.message);

                    that.uploadProcess = "上传";
                    that.canNot = false;

                    document.getElementById('dismissUpload').click();

                    //将列表中的文件刷新
                    getUserAllFiles(Operate);

                } else {
                    that.uploadProcess = "上传";
                    that.canNot = false;
                    alert(response.data.message);
                }
            }).catch(function (error) {
                console.log(error);
            })
        }
    }
})

function exitLogin() {

    axios({
        method: "post",
        url: "/MyNetDisk/exitLogin",
    }).then(function (response) {
        alert(response.data.message);
        window.location.reload();
    }).catch(function (error) {
        console.log(error);
    });

}

function getUserInformation() {
    axios({
        method: "post",
        url: "/MyNetDisk/getUserInformation",
    }).then(function (response) {
        if (response.data.result == 1) {
            console.log(response.data.message);
            alert("\n" + "用户名：" + response.data.user.username + "\n\n" + "邮箱：" + response.data.user.email + "\n");
        } else {
            alert(response.data.message);
        }
    }).catch(function (error) {
        console.log(error);
    });
}

function getUserAllFiles(that) {

    axios({
        method: "post",
        url: "/MyNetDisk/getFiles"
    }).then(function (response) {

        // that.arr=response.data;

        //后台传来的数据
        that.data = response.data;

        // 根据后台数据的条数和每页显示数量算出一共几页,得0时设为1 ;
        that.pageNum = Math.ceil(that.data.length / that.pageSize) || 1;

        for (let i = 0; i < that.pageNum; i++) {
            // 每一页都是一个数组 形如 [['第一页的数据'],['第二页的数据'],['第三页数据']]
            // 根据每页显示数量 将后台的数据分割到 每一页,假设pageSize为5， 则第一页是1-5条，即slice(0,5)，第二页是6-10条，即slice(5,10)...
            that.totalPage[i] = that.data.slice(that.pageSize * i, that.pageSize * (i + 1))
        }
        // 获取到数据后显示第一页内容
        that.dataShow = that.totalPage[that.currentPage];
        that.arr = that.dataShow;

    }).catch(function (error) {
        console.log(error);
    })

}

function fileDownload(link) {

    //获取原文件名
    let originalFilename = link.substring(link.lastIndexOf("=") + 1);

    axios({
        method: "get",
        url: link,
        // 表明服务器返回的数据类型为二进制类型
        responseType: "blob"
    }).then(function (response) {
        console.log(response.data);

        const content = response.data;
        const blob = new Blob([content]);
        const fileName =originalFilename;

        if ('download' in document.createElement('a')) { // 非IE下载
            const elink = document.createElement('a')
            elink.download = fileName
            elink.style.display = 'none'
            elink.href = URL.createObjectURL(blob)
            document.body.appendChild(elink)
            elink.click()
            URL.revokeObjectURL(elink.href) // 释放URL 对象
            document.body.removeChild(elink)
        } else { // IE10+下载
            navigator.msSaveBlob(blob, fileName)
        }

        console.log("下载成功！");
    }).catch(function (error) {
        console.log(error);
    });
}

function fileRemove(link){

    let theLink=link.replace("fileDownload","removeFile");

    console.log(theLink);

    axios({
        method: "get",
        url: theLink,
    }).then(function (response) {

        if (response.data.result == 1) {
            getUserAllFiles(Operate);
            alert(response.data.message);
        } else {
            alert(response.data.message);
        }

    }).catch(function (error) {
        console.log(error);
    });
}

let Operate = new Vue({
    el: "#operate",
    data: {
        optionfiles: [],
        //展示在页面的文件
        arr: [],
        // 后台传来的数组
        data: [],
        // 所有页面的数据
        totalPage: [],
        // 每页显示数量
        pageSize: 5,
        // 共几页
        pageNum: 1,
        // 当前显示的数据
        dataShow: "",
        // 默认当前显示第一页
        currentPage: 0
    },
    methods: {
        getNextPage: function () {
            if (this.currentPage === this.pageNum - 1) {
                return;
            } else {
                this.dataShow = this.totalPage[++this.currentPage];
                this.arr = this.dataShow;

            }
        },
        getPrePage: function () {
            if (this.currentPage === 0) {
                return;
            } else {
                this.dataShow = this.totalPage[--this.currentPage];
                this.arr = this.dataShow;
            }
        },
        skipPage: function (num) {
            this.currentPage = num;
            this.dataShow = this.totalPage[this.currentPage];
            this.arr = this.dataShow;
        },
        downloadUserFiles: function () {
            if (this.optionfiles.length == 0) {
                alert("请选择下载的文件！");
            } else {
                for (let i = 0; i < this.optionfiles.length; i++) {
                    fileDownload(this.optionfiles[i]);
                }
                alert("已添加下载！");
            }
        },
        removeUserFiles:function () {
            if (this.optionfiles.length == 0) {
                alert("请选择删除的文件！");
            } else {
                for (let i = 0; i < this.optionfiles.length; i++) {
                    fileRemove(this.optionfiles[i]);
                }
            }
        }
    },
    mounted: function () {
        const that = this;
        console.log("获取用户所有文件");
        getUserAllFiles(that);
    }
})
