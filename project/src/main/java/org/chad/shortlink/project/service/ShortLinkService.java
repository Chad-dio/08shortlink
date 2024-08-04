package org.chad.shortlink.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.chad.shortlink.project.domain.dto.ShortLinkBatchCreateDTO;
import org.chad.shortlink.project.domain.dto.ShortLinkCreateDTO;
import org.chad.shortlink.project.domain.dto.ShortLinkUpdateDTO;
import org.chad.shortlink.project.domain.entity.Result;
import org.chad.shortlink.project.domain.po.ShortLink;
import org.chad.shortlink.project.domain.vo.ShortLinkCreateVO;

public interface ShortLinkService extends IService<ShortLink> {

    /**
     * 创建短链接
     *
     * @param requestParam 创建短链接请求参数
     * @return 短链接创建信息
     */
    Result<ShortLinkCreateVO> createShortLink(ShortLinkCreateDTO requestParam);

    /**
     * 批量创建短链接
     *
     * @param requestParam 批量创建短链接请求参数
     * @return 批量创建短链接返回参数
     */
    Result batchCreateShortLink(ShortLinkBatchCreateDTO requestParam);

    /**
     * 修改短链接
     *
     * @param requestParam 修改短链接请求参数
     */
    Result updateShortLink(ShortLinkUpdateDTO requestParam);
//
//    /**
//     * 分页查询短链接
//     *
//     * @param requestParam 分页查询短链接请求参数
//     * @return 短链接分页返回结果
//     */
//    IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO requestParam);
//
//    /**
//     * 查询短链接分组内数量
//     *
//     * @param requestParam 查询短链接分组内数量请求参数
//     * @return 查询短链接分组内数量响应
//     */
//    List<ShortLinkGroupCountQueryRespDTO> listGroupShortLinkCount(List<String> requestParam);
//
//    /**
//     * 短链接跳转
//     *
//     * @param shortUri 短链接后缀
//     * @param request  HTTP 请求
//     * @param response HTTP 响应
//     */
//    void restoreUrl(String shortUri, ServletRequest request, ServletResponse response);
//
//    /**
//     * 短链接统计
//     *
//     * @param fullShortUrl         完整短链接
//     * @param gid                  分组标识
//     * @param shortLinkStatsRecord 短链接统计实体参数
//     */
//    void shortLinkStats(String fullShortUrl, String gid, ShortLinkStatsRecordDTO shortLinkStatsRecord);
}