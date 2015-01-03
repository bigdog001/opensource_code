/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 5/29/13
 * Time: 7:22 AM
 */
var editor;

var filepath;

String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}
     
function go(aid) {  
    if (aid == 0) {
        window.open('openarticle.action', '', 'height=600,width=800,top=90,left=50,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
    } else {
        window.open('openarticle.action?id=' + aid, '', 'height=600,width=800,top=90,left=50,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
    }

}

function openFileBrowser() {
    window.open('openFileBrowser.action', '', '');
}

function openUpload() {
    window.open('openupload.action', '', 'height=300,width=800,top=90,left=50,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
}

function persistArticle(aid) {
    var url_;
    if (aid == 0) {
        url_ = "articleAdd.action";
    } else {
        url_ = "articleUpdate.action";
    }

    var title = $("#title").val();
    var imgname = $("#imgname").val();
    var catagory_order = $("#catagory_order").val();
    var description = $("#description").val();
    var content = editor.getData();

    $.ajax({url: url_,
        type: 'POST',
        data: {id: aid, title: title, catagory_order: catagory_order, content: content, imgname: imgname, description: description},
        dataType: 'text',
        timeout: 3000,
        error: function () {
            alert('Error loading document');
        },
        success: function (result) {
            var obj = jQuery.parseJSON(result);
            if (obj.result_code == 1) {
                alert("操作成功!");
            }
        }

    });
}

var startRecord = 0, recordSize = 10, currentPage = 1;
function firstPage(){
    currentPage = 1;
    reloadA();
}
function lastPage(totals){
    currentPage = totals;
    reloadA();
}
function goToPage(){
    var whichpage = $("#whichpage").val();
    if (whichpage == currentPage) {
        alert("当前页面即是第"+whichpage+"页!");
        return;
    }
    currentPage = whichpage;
    reloadA();
}
function previous(){
    if (currentPage == 1) {
        alert("已经到达第一页.");
        return;
    }
    currentPage = currentPage - 1;
    reloadA();
}
function nextOne(totals){
    if (currentPage == totals) {
        alert("已经到达最后一页.");
        return;
    }
    currentPage = currentPage + 1;
    reloadA();

}
function reloadA() {
    $("#currentPage").html(currentPage);
    $("#reload_article").attr({"disabled": "disabled"});
    $("#loadingimg").attr({"display": "block"});
    var url_ = "articleLoad.action";
    if (currentPage <= 0) {
        startRecord = 0;
    } else {
        startRecord = (currentPage - 1) * recordSize;
    }
    $.ajax({url: url_,
        type: 'POST',
        data: {startRecord: startRecord, recordSize: recordSize, ajaxTrue: 1},
        dataType: 'text',
        timeout: 3000,
        error: function () {
            alert('Error loading document');
        },
        success: function (result) {
            var obj = jQuery.parseJSON(result);
            $("#users tbody").html("");
            $.each(obj, function (k, v) {
                var id = v.id;
                var title = v.title;
                var createTime = v.createTime;
                var description = v.description;
                var catagory_order = v.catagory_order;

                var go_ = "go(" + id + ")";
                var del_ = "del(" + id + ")";
                $("#users tbody").append("<tr><td>" + id + "</td><td>" + title + "</td><td>" + createTime + "</td><td>" + description + "</td><td>" + catagory_order + "</td><td><button onclick=" + go_ + ">编辑</button><button onclick=" + del_ + ">删除</button></td></tr>");
            });
            $("#reload_article").removeAttr("disabled");
            $("#loadingimg").attr({"display": "none"});
        }

    });
}

function del(aid) {
    if (aid == 0) {
        return;
    }
    var url_ = "articleDel.action";
    $.ajax({url: url_,
        type: 'POST',
        data: {id: aid},
        dataType: 'text',
        timeout: 3000,
        error: function () {
            alert('Error loading document');
        },
        success: function (result) {
            var obj = jQuery.parseJSON(result);
            if (obj.result_code == 1) {
                alert("删除成功!");
                reloadA();
            }


        }

    });
}



