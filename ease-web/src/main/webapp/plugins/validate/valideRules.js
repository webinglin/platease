
/**
 * 验证规则
 * @author linwb
 * @since 2015-10-13
 */
var rules = {
	wi : [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ], // 加权因子   
	valideCode : [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ], // 身份证验证位值.10代表X   
	
	/*
	 * 身份证规则
	 */
	idcard:function(gets,obj,curform,regex){
		var idCard = rules.trim(gets.replace(/ /g, ""));               //去掉字符串头尾空格                     
		if (idCard.length == 15) {   
			return rules.isValidityBrithBy15IdCard(idCard);       //进行15位身份证的验证    
		} else if (idCard.length == 18) {   
			var a_idCard = idCard.split("");                // 得到身份证数组   
			if(rules.isValidityBrithBy18IdCard(idCard)&&rules.isTrueValidateCodeBy18IdCard(a_idCard)){   //进行18位身份证的基本验证和第18位的验证
				return true;   
			}else {   
				return false;   
			}   
		}
		
		return false;   
	},

	// 判断身份证号码为18位时最后的验证位是否正确  
	isTrueValidateCodeBy18IdCard : function (a_idCard) {   
	    var sum = 0;                             // 声明加权求和变量   
	    if (a_idCard[17].toLowerCase() == 'x') {   
	        a_idCard[17] = 10;                    // 将最后位为x的验证码替换为10方便后续操作   
	    }   
	    for ( var i = 0; i < 17; i++) {   
	        sum += rules.wi[i] * a_idCard[i];            // 加权求和   
	    }   
	    valCodePosition = sum % 11;                // 得到验证码所位置   
	    if (a_idCard[17] == rules.valideCode[valCodePosition]) {   
	        return true;   
	    } else {   
	        return false;   
	    }   
	},
	
	// 验证18位数身份证号码中的生日是否是有效生日
	isValidityBrithBy18IdCard: function (idCard18){   
	    var year =  idCard18.substring(6,10);   
	    var month = idCard18.substring(10,12);   
	    var day = idCard18.substring(12,14);   
	    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
	    // 这里用getFullYear()获取年份，避免千年虫问题   
	    if(temp_date.getFullYear()!=parseFloat(year)   
	          ||temp_date.getMonth()!=parseFloat(month)-1   
	          ||temp_date.getDate()!=parseFloat(day)){   
	            return false;   
	    }else{   
	        return true;   
	    }   
	},
	
	// 验证15位数身份证号码中的生日是否是有效生日
	isValidityBrithBy15IdCard: function (idCard15){   
		var year =  idCard15.substring(6,8);   
		var month = idCard15.substring(8,10);   
		var day = idCard15.substring(10,12);   
		var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
    	// 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法   
		if(temp_date.getYear()!=parseFloat(year)   
				||temp_date.getMonth()!=parseFloat(month)-1   
  	            ||temp_date.getDate()!=parseFloat(day)){   
			return false;   
		}else{   
            return true;   
        }   
	},
	
	//去掉字符串头尾空格   
	trim: function (str) {   
	    return str.replace(/(^\s*)|(\s*$)/g, "");   
	},

	// 通过身份证判断是男是女  
	maleOrFemalByIdCard: function (idCard){   
	    idCard = rules.trim(idCard.replace(/ /g, ""));        // 对身份证号码做处理。包括字符间有空格。   
	    if(idCard.length==15){   
	        if(idCard.substring(14,15)%2==0){   
	            return 'female';   
	        }else{   
	            return 'male';   
	        }   
	    }else if(idCard.length ==18){   
	        if(idCard.substring(14,17)%2==0){   
	            return 'female';   
	        }else{   
	            return 'male';   
	        }   
	    }else{   
	        return null;   
	    }   
	},


    /* ************ 以下规则试用于 validform.js ****************** */

    /* 证件号码必填验证 */
    idcardRequired:function(gets,obj,curform,regex){
        if($.trim(gets)==='') {
            return false;
        }

        return rules.idcard(gets,obj,curform,regex);
    },


    /* 证件号码非必须的验证（有输入才验证）　*/
    idcardNoRequired:function(gets,obj,curform,regex){
        if($.trim(gets)==='') {
            return true;
        }

        return rules.idcard(gets,obj,curform,regex);
    },

    /* 手机号码必填验证 */
    phoneRequired:function(gets,obj,curform,regex){
        if($.trim(gets)==='') {
            return false;
        }
        return gets.match(/^[\d|-]{7,32}$/)!==null;
    },

    /* 手机号码非必填验证 */
    phoneNoRequired:function(gets,obj,curform,regex){
        if($.trim(gets)==='') {
            return true;
        }

        return gets.match(/^[\d|-]{7,32}$/)!==null;
    },

    /* QQ号码非必填验证 */
    qqNoRequired:function(gets,obj,curform,regex){
        if($.trim(gets)==='') {
            return true;
        }
        return gets.match(/^\d*$/)!==null;
    },

    /* 浮点数，只有一位小数点的 非必填验证 */
    floatNoRequired:function(gets,obj,curform,regex){
        if($.trim(gets)==='') {
            return true;
        }
        var p = /^\d+(\.\d{1})?$/;
        return p.test(gets);
    }
};


