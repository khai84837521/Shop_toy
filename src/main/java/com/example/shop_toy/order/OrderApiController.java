package com.example.shop_toy.order;


import com.example.shop_toy.application.order.OrderFacade;
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
@Api(tags = {"C. 주문 API"})
@RequestMapping("/api/v1/orders")
public class OrderApiController {
    private final OrderFacade orderFacade;
    private final OrderDtoMapper orderDtoMapper;

    @PostMapping
    @ApiOperation(value = "상품 주문", notes = "전달 받은 정보로 상품을 주문 합니다 회원 로그인이 필요한 API 입니다.")
    @ApiResponse(code = 200, message = "성공 시 주문 토큰을 반환 합니다.")
    public CommonResponse registerOrder(@RequestBody @Valid OrderDto.RegisterOrderRequest request,
                                        @ApiParam(value = "회원 엑세스 토큰", example = "Bearer {ACCESS_TOKEN}")
                                        @RequestHeader(value = "Authorization", defaultValue = "") String authorization) {
        String token = TokenGenerator.getToken(authorization);
        var orderCommand = orderDtoMapper.of(request);
        var orderToken = orderFacade.registerOrder(orderCommand, token);
        var response = orderDtoMapper.of(orderToken);
        return CommonResponse.success(response);
    }

    @GetMapping("/{orderToken}")
    @ApiOperation(value = "회원 주문 개별 조회", notes = "회원 주문 상품을 개별 조회 합니다 회원 로그인이 필요한 API 입니다.")
    @ApiResponse(code = 200, message = "성공 시 주문 토큰을 반환 합니다.")
    public CommonResponse retrieveOrder(@PathVariable("orderToken") String orderToken,
                                        @ApiParam(value = "회원 엑세스 토큰", example = "Bearer {ACCESS_TOKEN}")
                                        @RequestHeader(value = "Authorization", defaultValue = "") String authorization) {
        String token = TokenGenerator.getToken(authorization);
        var orderResult = orderFacade.retrieveOrder(token, orderToken);
        var response = orderDtoMapper.of(orderResult);
        return CommonResponse.success(response);
    }

    @GetMapping
    @ApiOperation(value = "회원 주문 내역 조회", notes = "회원 주문 상품을 전체 조회 합니다 회원 로그인이 필요한 API 입니다.")
    @ApiResponse(code = 200, message = "성공 시 주문 토큰을 반환 합니다.")
    public CommonResponse retrieveAllOrder(@Valid OrderDto.SearchOrderRequest searchRequest,
                                            @ApiParam(value = "회원 엑세스 토큰", example = "Bearer {ACCESS_TOKEN}")
                                            @RequestHeader(value = "Authorization", defaultValue = "") String authorization,
                                            Pageable pageable) {
        String token = TokenGenerator.getToken(authorization);
        var orderlistInfo = orderFacade.retrieveAllOrder(token, searchRequest, pageable);
        var response = orderDtoMapper.of(orderlistInfo);
        return CommonResponse.success(response, "주문 목록 조회 성공");
    }

}
