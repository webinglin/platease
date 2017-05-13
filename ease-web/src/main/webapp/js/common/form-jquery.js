/*
 * 基于jquery的通用表单工具类
 * Created by webinglin on 2017/5/12.
 */

web.ns("form");
web.form = {
    /**
     * 返回object类型
     * @param $form 表单对象
     */
    getValues: function ($form) {
        var formArray = $form.serializeArray();
        var obj = {};
        for (var i = 0; i < formArray.length; i++) {
            obj[formArray[i]['name']] = formArray[i]['value'];
        }
        return obj;
    },

    /**
     * 将对象的值设置到表单或者其他容器中
     * @param selector 数据填充容器ID
     * @param data   表单数据
     */
    setValues: function (selector, data) {
        var $parent, $temp;
        if ( typeof selector === "string" ) {
            if(selector.indexOf("#")==-1){
                selector = "#" + selector;
            }
            $parent = $(selector);
        }
        if ( selector.selector !== undefined ) {
            $parent = selector;
        }
        if (!$parent) {
            return false;
        }

        $.each(data, function (name, value) {
            $temp = $parent.find("[name=" + name + "]");
            if ($temp.is(":radio")) {
                value = value === true ? 1 : value === false ? 0 : value;
                $temp.filter("[value=" + value + "]").prop("checked", true);
            } else if ($temp.is(":checkbox")) {
                if (value === true) {
                    $temp.prop("checked", true);
                } else {
                    $temp.filter("[value=" + value + "]").prop("checked", true);
                }
            } else {
                $temp.val(value);
            }
        });
    },

    setTexts: function (selector, data) {
        var $parent, $temp;
        if ( typeof selector === "string" ) {
            if(selector.indexOf("#")==-1){
                selector = "#" + selector;
            }
            $parent = $(selector);
        }
        if ( selector.selector !== undefined ) {
            $parent = selector;
        }
        if (!$parent) {
            return false;
        }

        $.each(data, function (name, value) {
            if(null==value){
                return true;
            }
            $temp = $parent.find("[name=" + name + "]");
            if ($temp.is(":radio")) {
                value = value === true ? 1 : value === false ? 0 : value;
                $temp.filter("[value=" + value + "]").prop("checked", true);
            } else if ($temp.is(":checkbox")) {
                if (value === true) {
                    $temp.prop("checked", true);
                } else {
                    $temp.filter("[value=" + value + "]").prop("checked", true);
                }
            } else {
                $temp.text(value);
            }
        });
    },

    /**
     * 将input，textarea 的值都清空
     * @param selector  选择器
     * @returns {boolean}
     */
    reset:function (selector) {
        var $parent ;
        if ( typeof selector === "string" ) {
            if(selector.indexOf("#")==-1){
                selector = "#" + selector;
            }
            $parent = $(selector);
        }
        if ( selector.selector !== undefined ) {
            $parent = selector;
        }
        if (!$parent) {
            return false;
        }

        $parent.find("input, textarea").val("");
    }

};