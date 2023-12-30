package io.chou401.common.vo.sys;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * {@code @author}  chou401
 * {@code @date} 2023/6/18
 **/
@Data
@Schema(description = "用户导航菜单树形列表VO")
public class SysNavMenuTreeVo {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "菜单名称")
    private String name;

    @Schema(description = "父id")
    private Long parentId;

    @Schema(description = "菜单唯一编码")
    private String code;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "菜单类型，1：菜单，2：外链，3：权限")
    private Integer type;

    @Schema(description = "状态，0：禁用，1：启用")
    private Boolean status;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "前端路由地址")
    private String routeUrl;

    @Schema(description = "路由名称")
    private String routeName;

    @Schema(description = "重定向")
    private String routeRedirect;

    @Schema(description = "组件路径")
    private String componentPath;

    @Schema(description = "是否显示,0：不显示，1：显示")
    private Boolean isShow;

    @Schema(description = "是否缓存，0：否 1：是")
    private Boolean isCache;

    @Schema(description = "是否外链，0：否 1：是")
    private Boolean isLink;

    @Schema(description = "是否首页 0：否，1：是")
    private Boolean isHome;

    @Schema(description = "链接地址")
    private String linkUrl;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "子菜单集合")
    private List<SysNavMenuTreeVo> children;

}
