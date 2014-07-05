(function(exports) {
    var _gaq = _gaq || [];
    _gaq.push(['_setAccount', 'UA-39250830-1']);
    _gaq.push(['_trackPageview']);

    (function() {
        var ga = document.createElement('script');
        ga.type = 'text/javascript';
        ga.async = true;
        ga.src = ('https:' == document.location.protocol ? 'https://ssl': 'http://www') + '.google-analytics.com/ga.js';
        var s = document.getElementsByTagName('script')[0];
        s.parentNode.insertBefore(ga, s);
    })();
    var nn = 1;
    var first = 1;
    var tt = null;
    $('.page_sizea').click(function() {
        nn = this.id.slice( - 1);
        window.clearTimeout(tt);
        change_img();
    })

    function change_img() {
        if (first == 0) {
            $('.pic_paly').fadeOut(2000);
            $('#url' + nn).fadeIn(2000);
        }
        first = 0;
        $('.page_sizea').removeClass('change_on');
        $('#changen' + nn).addClass('change_on');
        nn++;
        if (nn > 5) {
            nn = 1;
        }
        tt = setTimeout(change_img, 4000);
    }
    change_img();
    (function(){
        var fLoginFail = function(){
            $('#lwm').attr('title','换一张');
            var oldShowErrs = QHPass.loginUtils.showErrs;
            QHPass.loginUtils.showErrs = function(iptId,errMsg){
                return oldShowErrs('',errMsg);
            };
        };
        var fLoginCallBack = function(){
            window.location.href = sDefaultUrl+'&'+Math.random().toString().substring(2);
        };
        QHPass.resConfig.src="pcw_adsystem";
        QHPass.showLogin(fLoginCallBack,{afterRender:fLoginFail,checkPharse:true,thirdLogin:[],type:'nomal',wrap:'login',cookie_domains:["1360|qihoo|woxihuan|so|360pay"]});
    })();
})(this)

