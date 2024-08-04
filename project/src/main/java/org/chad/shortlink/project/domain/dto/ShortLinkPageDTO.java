package org.chad.shortlink.project.domain.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.chad.shortlink.project.domain.po.ShortLink;

@Data
public class ShortLinkPageDTO extends Page<ShortLink> {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 排序标识
     */
    private String orderTag;
}