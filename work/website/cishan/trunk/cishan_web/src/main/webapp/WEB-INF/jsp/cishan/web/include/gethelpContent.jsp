<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:include page="jeasyui.jsp"/>
<div style="margin:10px 0;"></div>
<div class="easyui-tabs" data-options="tabWidth:112" style="width:960px;height:350px">
    <div title="资助对象" style="padding:10px">         
        <p class="content2"> &nbsp;&nbsp;&nbsp;尊敬的用户,网站主张的受助对象基本都属于社会弱势群体,他们可能不会上网,更不知道如何从
            这个网站获取帮助,您的使命之一就是发现并向我们提供这些受助人员的基本信息,我们会有专人负责资助接洽事宜!</p><br/>
        <p class="content2" style="font-size: 20px">&nbsp;&nbsp;&nbsp;资助对象的构成:  </p>

        <!-- 资助对象的构成======================== -->   
        <div class="easyui-accordion" data-options="multiple:true" style="height1:300px;margin-top: 15px">
            <div title="贫困儿童"  style="overflow:auto;padding:10px;">
                <p> 贫困儿童    </p>
            </div>
            <div title="贫困老人" style="padding:10px;">
                <p> 贫困老人</p>
            </div>
            <div title="贫困母亲" style="padding:10px;">
                <p>   贫困母亲 </p>
            </div>
            <div title="城市流浪者" style="padding:10px;">
                <p> 城市流浪者.</p>
            </div>
            
        </div>
        <!-- ========================资助对象的构成 -->   


    </div>
    
    <div title="资助类型" style="padding:10px">
        <p> 资助类型  .</p>
        
         <!-- 资助类型======================== -->   
        <div class="easyui-accordion" data-options="multiple:true" style="height1:300px;margin-top: 15px">
            <div title="书籍文具"  style="overflow:auto;padding:10px;">
                <p> 书籍    </p>
            </div>
            <div title="衣物" style="padding:10px;">
                <p> 衣物</p>
            </div>
            <div title="食物" style="padding:10px;">
                <p>   食物 </p>
            </div>
            <div title="现金" style="padding:10px;">
                <p> 现金.</p>
            </div>
            
        </div>
        <!-- ========================资助类型 -->
    </div>
    <div title="开始" style="padding:10px">
        <p>Maps Content.</p>
    </div>



</div>
