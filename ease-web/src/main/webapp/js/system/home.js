/**
 * Created by webinglin on 2017/4/25.
 */


$(function () {
    $(".content").width($("body").width()-195);

    var activeCss = { "color":"#fff", "background-color": "#007FFF", "text-shadow": "0 0 0" };
    var nativeCss = {"color":"#959595", "background-color":'',"text-shadow": "1px 1px 1px #fff" };

    $("a.link").click(function(){

        $("a.link").css(nativeCss);
        $(this).css(activeCss);


        var url = $(this).attr("data-url");
        $.post(url, function(data){
            $("#content").empty();
            $("#content").html(data);
        });
    });


    // 如何判断是否是刷新?
    // $(window).unload(function () {
    //     $.ajax({
    //         type: 'POST',
    //         async: false,
    //         url: basePath+'/logout'
    //     });
    // });
});