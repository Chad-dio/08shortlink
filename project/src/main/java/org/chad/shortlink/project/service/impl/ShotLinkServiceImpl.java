package org.chad.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.StrBuilder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.protobuf.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chad.shortlink.project.domain.dto.ShortLinkBatchCreateDTO;
import org.chad.shortlink.project.domain.dto.ShortLinkCreateDTO;
import org.chad.shortlink.project.domain.dto.ShortLinkUpdateDTO;
import org.chad.shortlink.project.domain.entity.Result;
import org.chad.shortlink.project.domain.po.ShortLink;
import org.chad.shortlink.project.domain.vo.ShortLinkCreateVO;
import org.chad.shortlink.project.mapper.ShortLinkMapper;
import org.chad.shortlink.project.service.ShortLinkService;
import org.chad.shortlink.project.util.HashUtil;
import org.redisson.api.RBloomFilter;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShotLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLink> implements ShortLinkService {

    private final RBloomFilter<String> shortUriCreateCachePenetrationBloomFilter;

    @Override
    public Result<ShortLinkCreateVO> createShortLink(ShortLinkCreateDTO requestParam) throws Exception {
        String shortLinkSuffix = generateSuffix(requestParam);
        String fullShortUrl = StrBuilder.create(requestParam.getDomain())
                .append("/")
                .append(shortLinkSuffix)
                .toString();
        ShortLink shortLink = ShortLink.builder()
                .domain(requestParam.getDomain())
                .originUrl(requestParam.getOriginUrl())
                .gid(requestParam.getGid())
                .createdType(requestParam.getCreatedType())
                .validDateType(requestParam.getValidDateType())
                .validDate(requestParam.getValidDate())
                .describe(requestParam.getDescribe())
                .shortUri(shortLinkSuffix)
                .enableStatus(0)
                .fullShortUrl(fullShortUrl)
                .build();
        try{
            baseMapper.insert(shortLink);
        }catch (DuplicateKeyException e){
            LambdaQueryWrapper<ShortLink> queryWrapper = Wrappers.lambdaQuery(ShortLink.class)
                    .eq(ShortLink::getFullShortUrl, fullShortUrl);
            ShortLink hasShortLink = baseMapper.selectOne(queryWrapper);
            if(BeanUtil.isNotEmpty(hasShortLink)){
                log.info("key重复");
                throw new ServiceException("短链接重复");
            }
        }
        shortUriCreateCachePenetrationBloomFilter.add(shortLinkSuffix);
        ShortLinkCreateVO shortLinkCreateVO = ShortLinkCreateVO.builder()
                .fullShortUrl(shortLink.getFullShortUrl())
                .gid(requestParam.getGid())
                .originUrl(requestParam.getOriginUrl())
                .build();
        return Result.success(shortLinkCreateVO);
    }

    @Override
    public Result batchCreateShortLink(ShortLinkBatchCreateDTO requestParam) {
        return null;
    }

    @Override
    public Result updateShortLink(ShortLinkUpdateDTO requestParam) {
        return null;
    }

    private String generateSuffix(ShortLinkCreateDTO requestParam) throws Exception {
        int customGenerateCount = 0;
        String shorUri;
        while (true) {
            if (customGenerateCount > 10) {
                throw new Exception("短链接频繁生成，请稍后再试");
            }
            String originUrl = requestParam.getOriginUrl();
            originUrl += System.currentTimeMillis();
            originUrl += UUID.randomUUID().toString();
            shorUri = HashUtil.hashToBase62(originUrl);
            if (!shortUriCreateCachePenetrationBloomFilter.contains(requestParam.getDomain() + "/" + shorUri)) {
                break;
            }
            customGenerateCount++;
        }
        return shorUri;
    }
}
