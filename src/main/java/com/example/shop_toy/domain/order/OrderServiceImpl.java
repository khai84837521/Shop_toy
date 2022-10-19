package com.example.shop_toy.domain.order;


import com.example.shop_toy.common.exception.BaseException;
import com.example.shop_toy.common.exception.InvalidParamException;
import com.example.shop_toy.common.response.ErrorCode;
import com.example.shop_toy.common.util.jwt.JwtTokenProvider;
import com.example.shop_toy.interfaces.order.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderStore orderStore;
    private final OrderReader orderReader;
    private final OrderItemSeriesFactory orderItemSeriesFactory;
    private final OrderInfoMapper orderInfoMapper;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 상품 주문 서비스
     * @param requestOrder  상품 주문 DTO
     * @param accessToken   액세스 토큰
     * @return              주문 토큰
     */
    @Override
    @Transactional
    public String registerOrder(OrderCommand.RegisterOrder requestOrder, String accessToken) {
        if (!jwtTokenProvider.validateToken(accessToken)) throw new InvalidParamException(ErrorCode.MEMBER_FAIL_INVALID_TOKEN);
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        String memberId = authentication.getName();
        Order order = orderStore.store(requestOrder.toEntity(memberId));
        orderItemSeriesFactory.store(order, requestOrder);
        return order.getOrderToken();
    }

    /**
     * 주문 정보 열람 서비스
     * @param accessToken   액세스 토큰
     * @param orderToken    주문 토큰
     * @return              주문 정보 객체
     */
    @Override
    @Transactional(readOnly = true)
    public OrderInfo.Main retrieveOrder(String accessToken, String orderToken) {
        if (!jwtTokenProvider.validateToken(accessToken)) throw new InvalidParamException(ErrorCode.MEMBER_FAIL_INVALID_TOKEN);
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        String memberId = authentication.getName();
        var order = orderReader.getOrder(orderToken);
        if(!order.getMemberId().equals(memberId)) throw new BaseException(ErrorCode.MEMBER_BAD_PERMISSION_TOKEN);
        var orderItemList = order.getOrderItemList();
        return orderInfoMapper.of(order, orderItemList);
    }

    /**
     * 주문 정보 리스트 열람 서비스
     * @param accessToken   액세스 토큰
     * @param searchRequest 주문 검색 DTO
     * @param pageable      페이지 객체
     * @return              주문 리스트 정보 객체
     */
    @Override
    public OrderInfo.OrderList retrieveAllOrder(String accessToken, OrderDto.SearchOrderRequest searchRequest, Pageable pageable) {
        if (!jwtTokenProvider.validateToken(accessToken)) throw new InvalidParamException(ErrorCode.MEMBER_FAIL_INVALID_TOKEN);
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        String memberId = authentication.getName();
        var orderList = orderReader.getOrderList(memberId, searchRequest, pageable);
        var orderInfoList = orderList.map(order -> {
            var orderItemList = order.getOrderItemList();
            return orderInfoMapper.of(order, orderItemList);
        }).toList();

        return OrderInfo.OrderList.builder()
                .page(pageable.getPageNumber()+1)
                .totalPage(orderList.getTotalPages())
                .size(pageable.getPageSize())
                .status(searchRequest.getStatus())
                .startDate(searchRequest.getStartDate())
                .endDate(searchRequest.getEndDate())
                .orderInfoList(orderInfoList)
                .build();
    }
}
