<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<p>

<div>
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>
    <style>
        #accordion-resizer-2 {
            padding: 10px;
            width: 1290px;
            height: 550px;
        }
    </style>
    <script>
        $(function () {
            $("#accordion-2").accordion({
                heightStyle: "fill"
            });
        });
        $(function () {
            $("#accordion-resizer-2").resizable({
                minHeight: 140,
                minWidth: 500,
                resize: function () {
                    $("#accordion-2").accordion("refresh");
                }
            });
        });
    </script>

    <div id="accordion-resizer-2" class="ui-widget-content">
        <div id="accordion-2">
            <h3>单页文章管理</h3>

            <div>
                <style>
                    h1 {
                        font-size: 1.2em;
                        margin: .6em 0;
                    }

                    div#users-contain {
                        width: 100%;
                        margin: 5px 0;
                    }

                    div#users-contain table {
                        margin: 1em 0;
                        border-collapse: collapse;
                        width: 100%;
                    }

                    div#users-contain table td, div#users-contain table th {
                        border: 1px solid #eee;
                        padding: .2em 2px;
                        text-align: left;
                    }

                    .ui-dialog .ui-state-error {
                        padding: .3em;
                    }

                    .validateTips {
                        border: 1px solid transparent;
                        padding: 0.3em;
                    }
                </style>
                <div id="users-contain" class="ui-widget">
                    <button id="opener_add" onclick="go(0)">增加文章</button>
                    <button id="reload_article" onclick="firstPage()">第一页</button>
                    <button id="reload_article_previous" onclick="previous()">上一页</button>
                    (<span id="currentPage">1</span>/<span>${articleCounter}</span>)
                    <button id="reload_article_next" onclick="nextOne(${articleCounter})">下一页</button>
                    <button id="reload_article_last" onclick="lastPage(${articleCounter})">最后一页</button>
                    <button id="reload_article_thatone" onclick="goToPage()">去第 </button> <input type="text" id="whichpage" value="1"/> 页
                    <img id="loadingimg" src="./image/t014042de8ea12aaadb.gif" width="30px" height="30px"
                         style="display:none">
                    <table id="users" class="ui-widget ui-widget-content">
                        <thead>
                        <tr class="ui-widget-header ">
                            <th>ID</th>
                            <th>title</th>
                            <th>发表时间</th>
                            <th>描述</th>
                            <th>排序</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                        <thead>
                        <tr class="ui-widget-header ">
                            <th>---</th>
                            <th>---</th>
                            <th>---</th>
                            <th>---</th>
                            <th>---</th>
                            <th>---</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
            <h3>注册用户2</h3>

            <div>
                <p>Sed non urna. Donec et ante. Phasellus eu ligula. Vestibulum sit amet purus. Vivamus hendrerit, dolor
                    at aliquet laoreet, mauris turpis porttitor velit, faucibus interdum tellus libero ac justo. Vivamus
                    non quam. In suscipit faucibus urna. </p>
            </div>
            <h3>文件</h3>

            <div>
                <p>just upload picture ,zip or some other files.... </p>
                <ul>
                    <li>
                        <button onclick="openFileBrowser()">管理服务器目录</button>
                    </li>
                    <li>
                        <button onclick="openUpload()">上传文件</button>
                    </li>
                    <li>List item three</li>
                </ul>
            </div>
            <h3>Section 4</h3>

            <div>
                <p>Cras dictum. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis
                    egestas. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae;
                    Aenean lacinia mauris vel est. </p>

                <p>Suspendisse eu nisl. Nullam ut libero. Integer dignissim consequat lectus. Class aptent taciti
                    sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. </p>
            </div>
        </div>
    </div>

</div>
</p>
