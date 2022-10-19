package com.example.shop_toy.interfaces.item;


import com.example.shop_toy.application.item.ItemFacade;
import com.example.shop_toy.common.response.CommonResponse;
import com.example.shop_toy.common.util.TokenGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = {"B. 상품 API"})
@RequestMapping("/api/v1/items")
public class ItemApiController {
    private final ItemFacade itemFacade;
    private final ItemDtoMapper itemDtoMapper;

    @PostMapping
    @ApiOperation(value = "상품 등록", notes = "전달 받은 정보로 상품을 등록 합니다 회원 로그인이 필요한 API 입니다.")
    @ApiResponse(code = 200, message = "성공 시 상품 토큰을 반환 합니다.")
    public CommonResponse<?> registerItem(@RequestBody @Valid ItemDto.RegisterItemRequest request,
                                          @ApiParam(value = "회원 엑세스 토큰", example = "Bearer {ACCESS_TOKEN}")
                                          @RequestHeader(value="Authorization", defaultValue = "") String authorization) {
        String token = TokenGenerator.getToken(authorization);
        var itemCommand = itemDtoMapper.of(request);
        var itemToken = itemFacade.registerItem(itemCommand, token);
        var response = itemDtoMapper.of(itemToken);
        return CommonResponse.success(response, "상품 등록 성공");
    }

    @GetMapping("/{itemToken}")
    @ApiOperation(value = "상품 개별 조회", notes = "상품의 정보를 조회 합니다.")
    @ApiResponse(code = 200, message = "성공 시 상품 정보를 반환 합니다.")
    public CommonResponse<?> retrieve(@PathVariable("itemToken") String itemToken) {
        var itemInfo = itemFacade.retrieveItemInfo(itemToken);
        var response = itemDtoMapper.of(itemInfo);
        return CommonResponse.success(response, "상품 조회 성공");
    }

    @GetMapping
    @ApiOperation(value = "상품 전체 조회", notes = "상품의 전체 정보를 조회 합니다.")
    @ApiResponse(code = 200, message = "성공 시 상품 목록을 반환 합니다.")
    public CommonResponse<?> retrieveAll(Pageable pageable,
                                         @ApiParam(value = "상품 이름 검색 키워드", example = "티셔츠")
                                         @RequestParam(value = "query", defaultValue = "") String keyword) {
        var itemInfoList = itemFacade.retrieveAllItemInfo(keyword, pageable);
        var response = itemDtoMapper.of(itemInfoList);
        return CommonResponse.success(response, "상품 목록 조회 성공");
    }

//    @DeleteMapping
//    @ApiOperation(value = "상품 일괄 삭제", notes = "등록된 상품을 일괄 삭제 합니다 (테스트용).")
//    public CommonResponse<?> deleteAll() {
//        itemFacade.deleteAllItem();
//        return CommonResponse.success("OK");
//    }
}
