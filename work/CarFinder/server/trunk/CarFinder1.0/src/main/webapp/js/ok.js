/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 5/29/13
 * Time: 7:22 AM
 * To change this template use File | Settings | File Templates.
 */

String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}


function sendAdd(id_item){
    var id_items= id_item.trim();
    $.post("/mumAction_addorder.action",{item_id:id_items},function(databack){
        databack = databack.trim();
         alert("success");
    });

}