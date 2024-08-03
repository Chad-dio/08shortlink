package org.chad.shortlink.admin.controller;

import lombok.RequiredArgsConstructor;
import org.chad.shortlink.admin.domain.dto.ShortLinkGroupSaveDTO;
import org.chad.shortlink.admin.domain.dto.ShortLinkGroupSortDTO;
import org.chad.shortlink.admin.domain.dto.ShortLinkGroupUpdateDTO;
import org.chad.shortlink.admin.domain.entity.Result;
import org.chad.shortlink.admin.service.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupController {

    private final GroupService groupService;

    /**
     * 新增短链接分组
     */
    @PostMapping
    public Result<Void> save(@RequestBody ShortLinkGroupSaveDTO requestParam) {
        return groupService.saveGroup(requestParam.getName());
    }

    /**
     * 查询短链接分组集合
     */
    @GetMapping
    public Result listGroup() {
        return groupService.listGroup();
    }

    /**
     * 修改短链接分组名称
     */
    @PutMapping
    public Result updateGroup(@RequestBody ShortLinkGroupUpdateDTO requestParam) {
        return groupService.updateGroup(requestParam);
    }

    /**
     * 删除短链接分组
     */
    @DeleteMapping
    public Result updateGroup(@RequestParam String gid) {
        return groupService.deleteGroup(gid);
    }

    /**
     * 短链接分组排序
     */
    @PostMapping("/sort")
    public Result sortGroup(@RequestBody List<ShortLinkGroupSortDTO> requestParam) {
        return groupService.sortGroup(requestParam);
    }
}
