package io.chou401.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.chou401.common.entity.sys.SysMenu;
import io.chou401.common.mapper.sys.SysMenuMapper;
import io.chou401.common.query.sys.SysMenuQuery;
import io.chou401.common.vo.sys.SysMenuTreeVo;
import io.chou401.common.vo.sys.SysMenuVo;
import io.chou401.common.vo.sys.SysNavMenuTreeVo;
import io.chou401.framework.exception.BusinessException;
import io.chou401.framework.utils.ObjectValueUtil;
import io.chou401.framework.utils.login.LoginUtil;
import io.chou401.system.dto.SysMenuDto;
import io.chou401.system.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统菜单 服务实现类
 *
 * {@code @author}  chou401
 * {@code @date} 2022-12-26
 */
@Slf4j
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addSysMenu(SysMenuDto dto) throws Exception {
        checkCodeExists(dto.getCode());
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(dto, sysMenu);
        return save(sysMenu);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysMenu(SysMenuDto dto) throws Exception {
        Long id = dto.getId();
        SysMenu sysMenu = getById(id);
        if (sysMenu == null) {
            throw new BusinessException("系统菜单不存在");
        }
        BeanUtils.copyProperties(dto, sysMenu);
        sysMenu.setUpdateTime(new Date());
        return updateById(sysMenu);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysMenu(Long id) throws Exception {
        return removeById(id);
    }

    @Override
    public SysMenuVo getSysMenuById(Long id) throws Exception {
        return sysMenuMapper.getSysMenuById(id);
    }

    @Override
    public List<SysMenuTreeVo> getAllSysMenuTreeList(SysMenuQuery query) throws Exception {
        List<SysMenuTreeVo> list = sysMenuMapper.getSysMenuTreeList(query);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        // 如果搜索条件有值，则直接返回普通列表
        boolean flag = ObjectValueUtil.isHaveValue(query);
        if (flag) {
            return list;
        }
        // 递归返回树形列表
        return recursionSysMenuTreeList(0L, list);
    }

    @Override
    public List<SysMenuTreeVo> getSysMenuTreeList() throws Exception {
        SysMenuQuery query = new SysMenuQuery();
        query.setStatus(true);
        List<SysMenuTreeVo> list = sysMenuMapper.getSysMenuTreeList(query);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        // 递归返回树形列表
        return recursionSysMenuTreeList(0L, list);
    }

    @Override
    public List<SysNavMenuTreeVo> getNavMenuTreeList() throws Exception {
        Long userId = LoginUtil.getUserId();
        // 如果是管理员，则查询所有可用菜单，否则获取当前用户所有可用的菜单
        boolean isAdmin = LoginUtil.isAdmin();
        List<SysNavMenuTreeVo> list;
        if (isAdmin) {
            list = sysMenuMapper.getNavMenuTreeAllList();
        } else {
            list = sysMenuMapper.getNavMenuTreeList(userId);
        }
        // 递归返回树形列表
        return recursionSysNavMenuTreeList(0L, list);
    }

    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId) throws Exception {
        return sysMenuMapper.getMenuIdsByRoleId(roleId);
    }

    @Override
    public void checkCodeExists(String code) throws Exception {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getCode, code);
        long count = count(wrapper);
        if (count > 0) {
            throw new BusinessException(code + "权限标识已经存在");
        }
    }

    /**
     * 递归设置树形菜单
     *
     * @param parentId
     * @param list
     * @return
     */
    private List<SysMenuTreeVo> recursionSysMenuTreeList(Long parentId, List<SysMenuTreeVo> list) {
        return list.stream()
                .filter(vo -> vo.getParentId().equals(parentId))
                .map(item -> {
                    item.setChildren(recursionSysMenuTreeList(item.getId(), list));
                    return item;
                })
                .collect(Collectors.toList());
    }

    /**
     * 递归设置树形导航菜单
     *
     * @param parentId
     * @param list
     * @return
     */
    private List<SysNavMenuTreeVo> recursionSysNavMenuTreeList(Long parentId, List<SysNavMenuTreeVo> list) {
        return list.stream()
                .filter(vo -> vo.getParentId().equals(parentId))
                .map(item -> {
                    item.setChildren(recursionSysNavMenuTreeList(item.getId(), list));
                    return item;
                })
                .collect(Collectors.toList());
    }

}
