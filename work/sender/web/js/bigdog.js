/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/26/12
 * Time: 8:07 PM
 * To change this template use File | Settings | File Templates.
 */
 function changework(){
    $.post("index.jsp", {swicth:'2'}, function
        (databk) {
        databk = databk.trim();
        alert(databk);

    });

}
 function refresh() {
     $.post("index.jsp", {swicth:'1'}, function
         (databk) {
         databk = databk.trim();
         $("#total_now").html(databk);
     });
 }