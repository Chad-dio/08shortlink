package org.chad.shortlink.project.controller;

import lombok.RequiredArgsConstructor;
import org.chad.shortlink.project.domain.dto.ShortLinkCreateDTO;
import org.chad.shortlink.project.domain.entity.Result;
import org.chad.shortlink.project.domain.vo.ShortLinkCreateVO;
import org.chad.shortlink.project.service.ShortLinkService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShortLinkController {
    private final ShortLinkService shortLinkService;

//    /**
//     * 短链接跳转原始链接
//     */
//    @GetMapping("/{short-uri}")
//    public void restoreUrl(@PathVariable("short-uri") String shortUri, ServletRequest request, ServletResponse response) {
//        shortLinkService.restoreUrl(shortUri, request, response);
//    }

    /**
     * 创建短链接
     */
    @PostMapping("/api/short-link/v1/create")
    public Result<ShortLinkCreateVO> createShortLink(@RequestBody ShortLinkCreateDTO requestParam) {
        return shortLinkService.createShortLink(requestParam);
    }

//
//    /**
//     * 批量创建短链接
//     */
//    @PostMapping("/api/short-link/v1/create/batch")
//    public Result<ShortLinkBatchCreateRespDTO> batchCreateShortLink(@RequestBody ShortLinkBatchCreateReqDTO requestParam) {
//        return Results.success(shortLinkService.batchCreateShortLink(requestParam));
//    }
//
//    /**
//     * 修改短链接
//     */
//    @PostMapping("/api/short-link/v1/update")
//    public Result<Void> updateShortLink(@RequestBody ShortLinkUpdateReqDTO requestParam) {
//        shortLinkService.updateShortLink(requestParam);
//        return Results.success();
//    }
//
//    /**
//     * 分页查询短链接
//     */
//    @GetMapping("/api/short-link/v1/page")
//    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam) {
//        return shortLinkService.pageShortLink(requestParam);
//    }
//
//    /**
//     * 查询短链接分组内数量
//     */
//    @GetMapping("/api/short-link/v1/count")
//    public Result<List<ShortLinkGroupCountQueryRespDTO>> listGroupShortLinkCount(@RequestParam("requestParam") List<String> requestParam) {
//        return Results.success(shortLinkService.listGroupShortLinkCount(requestParam));
//    }
}
