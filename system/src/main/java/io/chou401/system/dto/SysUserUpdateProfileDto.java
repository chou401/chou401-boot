package io.chou401.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 修改系统用户个人信息参数
 *
 * {@code @author}  chou401
 * {@code @date} 2022-12-26
 */
@Data
@Schema(description = "修改系统用户个人信息参数" )
public class SysUserUpdateProfileDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键" )
    @NotNull(message = "主键不能为空" )
    private Long id;

    @Schema(description = "昵称" )
    @NotBlank(message = "昵称不能为空" )
    @Length(max = 20, message = "昵称长度超过限制" )
    private String nickname;

    @Schema(description = "手机号码" )
    @Length(max = 11, message = "手机号码长度超过限制" )
    private String phone;

    @Schema(description = "电子邮件" )
    @Length(max = 255, message = "电子邮件长度超过限制" )
    private String email;

    @Schema(description = "性别，0：未知，1：男，2：女，默认0" )
    private Integer gender;

    @Schema(description = "头像")
    private String head;

}


