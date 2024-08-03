package org.chad.shortlink.admin.domain.dto;


import lombok.Data;

@Data
public class ShortLinkGroupSortDTO {

    /**
     * 分组ID
     */
    private String gid;

    /**
     * 排序
     */
    private Integer sortOrder;
}