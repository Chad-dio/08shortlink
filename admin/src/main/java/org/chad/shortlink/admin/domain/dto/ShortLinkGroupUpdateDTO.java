package org.chad.shortlink.admin.domain.dto;

import lombok.Data;

@Data
public class ShortLinkGroupUpdateDTO {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 分组名
     */
    private String name;
}