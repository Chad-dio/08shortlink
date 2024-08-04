package org.chad.shortlink.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.chad.shortlink.project.domain.po.ShortLink;

@Mapper
public interface ShortLinkMapper extends BaseMapper<ShortLink> {
}