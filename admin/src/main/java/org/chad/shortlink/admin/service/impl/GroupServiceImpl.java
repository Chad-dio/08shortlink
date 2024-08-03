package org.chad.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.chad.shortlink.admin.common.user.UserContext;
import org.chad.shortlink.admin.domain.dto.ShortLinkGroupSortDTO;
import org.chad.shortlink.admin.domain.dto.ShortLinkGroupUpdateDTO;
import org.chad.shortlink.admin.domain.entity.Result;
import org.chad.shortlink.admin.domain.po.Group;
import org.chad.shortlink.admin.domain.vo.ShortLinkGroupVO;
import org.chad.shortlink.admin.mapper.GroupMapper;
import org.chad.shortlink.admin.service.GroupService;
import org.chad.shortlink.admin.utils.RandomGenerator;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.chad.shortlink.admin.common.constant.RedisCacheConstant.LOCK_GROUP_CREATE_KEY;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupService {
    private final RedissonClient redissonClient;

    @Value("${short-link.group.max-num}")
    private Integer groupMaxNum;

    @Override
    public Result saveGroup(String groupName) {
        saveGroup(UserContext.getUsername(), groupName);
        return Result.success();
    }

    @Override
    public Result saveGroup(String username, String groupName) {
        RLock lock = redissonClient.getLock(String.format(LOCK_GROUP_CREATE_KEY, username));
        lock.lock();
        try {
            LambdaQueryWrapper<Group> queryWrapper = Wrappers.lambdaQuery(Group.class)
                    .eq(Group::getUsername, username)
                    .eq(Group::getDelFlag, 0);
            List<Group> GroupList = baseMapper.selectList(queryWrapper);
            if (CollUtil.isNotEmpty(GroupList) && GroupList.size() == groupMaxNum) {
                return Result.error("超出最大短链接分组数目");
            }
            String gid;
            do {
                gid = RandomGenerator.generateRandom();
            } while (!hasGid(username, gid));
            Group group = Group.builder()
                    .gid(gid)
                    .sortOrder(0)
                    .username(username)
                    .name(groupName)
                    .build();
            baseMapper.insert(group);
        } finally {
            lock.unlock();
        }
        return Result.success();
    }

    @Override
    public Result listGroup() {
        LambdaQueryWrapper<Group> queryWrapper = Wrappers.lambdaQuery(Group.class)
                .eq(Group::getDelFlag, 0)
                .eq(Group::getUsername, UserContext.getUsername())
                .orderByDesc(Group::getSortOrder, Group::getUpdateTime);
        List<ShortLinkGroupVO> list = baseMapper.selectList(queryWrapper)
                .stream()
                .map(group -> BeanUtil.copyProperties(group, ShortLinkGroupVO.class))
                .collect(Collectors.toList());
        return Result.success(list);
    }

    @Override
    public Result updateGroup(ShortLinkGroupUpdateDTO requestParam) {
        LambdaUpdateWrapper<Group> updateWrapper = Wrappers.lambdaUpdate(Group.class)
                .eq(Group::getUsername, UserContext.getUsername())
                .eq(Group::getGid, requestParam.getGid())
                .eq(Group::getDelFlag, 0);
        Group Group = new Group();
        Group.setName(requestParam.getName());
        baseMapper.update(Group, updateWrapper);
        return Result.success();
    }

    @Override
    public Result deleteGroup(String gid) {
        LambdaUpdateWrapper<Group> updateWrapper = Wrappers.lambdaUpdate(Group.class)
                .eq(Group::getUsername, UserContext.getUsername())
                .eq(Group::getGid, gid)
                .eq(Group::getDelFlag, 0);
        Group Group = new Group();
        Group.setDelFlag(1);
        baseMapper.update(Group, updateWrapper);
        return Result.success();
    }

    @Override
    public Result sortGroup(List<ShortLinkGroupSortDTO> requestParam) {
        requestParam.forEach(each -> {
            Group group = Group.builder()
                    .sortOrder(each.getSortOrder())
                    .build();
            LambdaUpdateWrapper<Group> updateWrapper = Wrappers.lambdaUpdate(Group.class)
                    .eq(Group::getUsername, UserContext.getUsername())
                    .eq(Group::getGid, each.getGid())
                    .eq(Group::getDelFlag, 0);
            baseMapper.update(group, updateWrapper);
        });
        return Result.success();
    }

    private boolean hasGid(String username, String gid) {
        LambdaQueryWrapper<Group> queryWrapper = Wrappers.lambdaQuery(Group.class)
                .eq(Group::getGid, gid)
                .eq(Group::getUsername, Optional.ofNullable(username).orElse(UserContext.getUsername()));
        Group hasGroupFlag = baseMapper.selectOne(queryWrapper);
        return hasGroupFlag == null;
    }
}
