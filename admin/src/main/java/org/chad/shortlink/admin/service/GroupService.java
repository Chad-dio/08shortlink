package org.chad.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.chad.shortlink.admin.domain.dto.ShortLinkGroupSortDTO;
import org.chad.shortlink.admin.domain.dto.ShortLinkGroupUpdateDTO;
import org.chad.shortlink.admin.domain.entity.Result;
import org.chad.shortlink.admin.domain.po.Group;

import java.util.List;

public interface GroupService extends IService<Group> {
    /**
     * 新增短链接分组
     *
     * @param groupName 短链接分组名
     */
    Result saveGroup(String groupName);

    /**
     * 新增短链接分组
     *
     * @param username  用户名
     * @param groupName 短链接分组名
     */
    Result saveGroup(String username, String groupName);

    /**
     * 查询用户短链接分组集合
     *
     * @return 用户短链接分组集合
     */
    Result listGroup();

    /**
     * 修改短链接分组
     *
     * @param requestParam 修改链接分组参数
     */
    Result updateGroup(ShortLinkGroupUpdateDTO requestParam);

    /**
     * 删除短链接分组
     *
     * @param gid 短链接分组标识
     */
    Result deleteGroup(String gid);

    /**
     * 短链接分组排序
     *
     * @param requestParam 短链接分组排序参数
     */
    Result sortGroup(List<ShortLinkGroupSortDTO> requestParam);
}
