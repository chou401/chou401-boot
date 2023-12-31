package io.chou401.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * {@code @author}  chou401
 * {@code @date} 2023/6/18
 **/
@Data
@Schema(description = "设置角色权限")
public class RoleMenusDto {

    @Schema(description = "角色ID")
    @NotNull(message = "角色ID不能为空")
    private Long roleId;

    @Schema(description = "菜单ID集合")
    @NotNull(message = "菜单ID集合不能为空")
    private List<Long> menuIds;

}
