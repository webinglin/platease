/**
 * Created by webinglin on 2017/4/25.
 */


$(function () {
    $(".content").width($("body").width()-55);


    $("a.link").click(function(){
        var url = $(this).attr("data-url");
        $.post(url, function(data){
            $("#content").empty();
            $("#content").html(data);
        });
    });
});