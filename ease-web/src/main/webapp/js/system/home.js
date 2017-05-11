/**
 * Created by webinglin on 2017/4/25.
 */


$(function () {
    $(".content").width($("body").width()-195);


    $("a.link").click(function(){
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