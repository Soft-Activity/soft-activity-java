package homework.soft.activity.entity.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜单表(Menu)实体类
 *
 * @author jscomet
 * @since 2024-12-20 15:55:42
 */
@Data
public class Menu implements Serializable {
    @Serial
    private static final long serialVersionUID = 364111766783783551L;
    /**
     * 菜单ID
     */
    @Schema(description = "菜单ID")    
    @TableId(value = "menu_id",type = IdType.AUTO)
    private Long menuId;


    /**
     * 父菜单ID
     */
    @Schema(description = "父菜单ID")    
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 路由名称
     */
    @Schema(description = "路由名称")    
    @TableField(value = "name")
    private String name;

    /**
     * 路由路径
     */
    @Schema(description = "路由路径")    
    @TableField(value = "path")
    private String path;

    /**
     * 组件路径
     */
    @Schema(description = "组件路径")    
    @TableField(value = "component")
    private String component;

    /**
     * 重定向地址
     */
    @Schema(description = "重定向地址")    
    @TableField(value = "redirect")
    private String redirect;

    /**
     * 菜单标题
     */
    @Schema(description = "菜单标题")    
    @TableField(value = "title")
    private String title;

    /**
     * 图标
     */
    @Schema(description = "图标")    
    @TableField(value = "icon")
    private String icon;

    /**
     * 排序
     */
    @Schema(description = "排序")    
    @TableField(value = "sort_order")
    private Integer sortOrder;

    /**
     * 是否隐藏
     */
    @Schema(description = "是否隐藏")    
    @TableField(value = "hidden")
    private Boolean hidden;

    /**
     * 总是显示
     */
    @Schema(description = "总是显示")    
    @TableField(value = "always_show")
    private Boolean alwaysShow;

    /**
     * 是否缓存
     */
    @Schema(description = "是否缓存")    
    @TableField(value = "keep_alive")
    private Boolean keepAlive;

    /**
     * 是否显示面包屑
     */
    @Schema(description = "是否显示面包屑")    
    @TableField(value = "breadcrumb")
    private Boolean breadcrumb;

    /**
     * 激活菜单
     */
    @Schema(description = "激活菜单")    
    @TableField(value = "active_menu")
    private String activeMenu;

    /**
     * 不显示标签页
     */
    @Schema(description = "不显示标签页")    
    @TableField(value = "no_tags_view")
    private Boolean noTagsView;

    /**
     * 是否可跳转
     */
    @Schema(description = "是否可跳转")    
    @TableField(value = "can_to")
    private Boolean canTo;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")    
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")    
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    /**
     * 0禁用 ，1启用
     */
    @Schema(description = "0禁用 ，1启用")    
    @TableField(value = "status")
    private Integer status;


}
