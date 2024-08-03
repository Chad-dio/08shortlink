package org.chad.shortlink.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.chad.shortlink.admin.domain.po.Group;

@Mapper
public interface GroupMapper extends BaseMapper<Group> {
}
