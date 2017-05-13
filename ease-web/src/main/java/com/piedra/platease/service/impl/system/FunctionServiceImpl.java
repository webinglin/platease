package com.piedra.platease.service.impl.system;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.piedra.platease.constants.Constants;
import com.piedra.platease.dao.system.FunctionDao;
import com.piedra.platease.dto.FunctionDTO;
import com.piedra.platease.model.Page;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.service.impl.BaseServiceImpl;
import com.piedra.platease.service.system.FunctionService;
import com.piedra.platease.utils.BeanMapUtil;
import com.piedra.platease.utils.UUIDUtil;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.apache.bcel.classfile.Constant;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 功能函数实体业务实现层
 * @author webinglin
 * @since 2017-05-03
 */
@Service
@Transactional
public class FunctionServiceImpl extends BaseServiceImpl<Function> implements FunctionService {

    @Autowired
    private FunctionDao funcDao;
    @Autowired
    public void setBaseDao(FunctionDao funcDao) {
        super.setBaseDao(funcDao);
    }


    @Override
    public void updateFunction(FunctionDTO functionDTO) throws Exception {
        Function func = new Function();
        BeanUtils.copyProperties(functionDTO,func);
        funcDao.updateFunc(func);
    }

    @Override
    public Page<Function> queryFunctionList(Page<Function> page, FunctionDTO functionDTO) throws Exception {
        Map<String,Object> params = BeanMapUtil.trans2Map(functionDTO);
        return funcDao.queryByNameWithTotal(page, "SysFunc.queryFuncListCnt", "SysFunc.queryFuncList", params);
    }


    /**
     * 根据ID删除菜单权限
     * 如果是 0.....[32] 为顶级节点ID，提示抛出异常，不允许删除，只能通过数据库脚本删除
     * 如果ID不是0....0 那么可以删除，删除的时候除了删除自身，还需要将parentId为该ID的也一起删除
     * @param funcId    菜单ID
     * @throws Exception    异常上抛
     */
    @Override
    public void delFunction(String funcId) throws Exception {
        if(StringUtils.isBlank(funcId)){
            throw new Exception("权限ID为空，无法删除");
        }
        if(Constants.PARENT_ID.equals(funcId)){
            throw new Exception("顶级节点不允许删除[删了会将所有的菜单都删除], 如果执意要删除，请通过数据库脚本删除");
        }

        List<Function> subFuncs = funcDao.getList("parentId", funcId);
        // 删除 角色-权限 关联表的数据
        List<String> funcIds =  new ArrayList<>();
        funcIds.add(funcId);
        subFuncs.forEach(function -> funcIds.add(function.getId()));
        Map<String,Object> params = Collections.singletonMap("funcIds", funcIds);
        funcDao.executeQueryByName("SysFunc.delRoleFuncByFuncIds",params);

        // 删除自身节点
        funcDao.delete(funcId);

        // 删除所有子节点
        funcDao.deleteByParentId(funcId);
    }

    @Override
    public Function addFunc(FunctionDTO functionDTO) throws Exception {
        Function func = new Function();
        BeanUtils.copyProperties(functionDTO, func);

        String pid = func.getParentId();
        if(StringUtils.isBlank(pid)) {
            throw new Exception("父节点ID不能为空");
        }

        // 1. 查询ID为parentId的权限的 顺序字符串
        String pOrderStr = StringUtils.EMPTY;
        if(!Constants.PARENT_ID.equals(pid)) {
            Function parentFunc = funcDao.get(pid);
            pOrderStr = parentFunc.getOrderStr();
        }
        List<Function> parentFuncList = funcDao.getList("parentId",pid);
        if(StringUtils.isBlank(pOrderStr)) {
            Set<String> orderStrSet = new HashSet<>();
            parentFuncList.forEach(parentFunc -> orderStrSet.add(parentFunc.getOrderStr()));
            pOrderStr = nextUseableChar(orderStrSet);
        } else {
            int cnt = parentFuncList.size();
            if(cnt>Constants.CHAR.length()){
                cnt = Constants.CHAR.length();
            }
            pOrderStr = pOrderStr + Character.toString(Constants.CHAR.charAt(cnt));
        }
        func.setOrderStr(pOrderStr);

        func.setId(UUIDUtil.generateUUID());
        funcDao.save(func);

        return func;
    }


    private String nextUseableChar(Set<String> orderStrSet) {
        String newOrderStr = StringUtils.EMPTY;
        for(int i = 0, len = Constants.CHAR.length(); i<len; i++){
            String tmpStr = Character.toString(Constants.CHAR.charAt(i));
            if(orderStrSet.contains(tmpStr)){
                continue ;
            }
            newOrderStr = tmpStr;
            i=len;
        }

        // 如果最后还是为空 那么就随机生成5个字符, 因为orderStr有8个字符的长度
        if(StringUtils.isBlank(newOrderStr)){
            newOrderStr = "f" + RandomStringUtils.random(5);
        }
        return newOrderStr;
    }
}
