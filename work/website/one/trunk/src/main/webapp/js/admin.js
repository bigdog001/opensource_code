/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 5/29/13
 * Time: 7:22 AM
 */

String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

/*

function del(id){
    $.ajax( {
        type : "POST",
        url : "/student.do?method=del&id=" + id,
        dataType: "json",
        success : function(data) {
            if(data.del == "true"){
                alert("删除成功！");
                $("#" + id).remove();
            }
            else{
                alert("删除失败！");
            }
        },
        error :function(){
            alert("网络连接出错！");
        }
    });
}*/
