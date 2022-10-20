package com.example.shop_toy.interfaces.order;

import com.example.shop_toy.ShopToyApplication;
import com.example.shop_toy.domain.member.MemberService;
import com.example.shop_toy.domain.order.OrderService;
import com.example.shop_toy.interfaces.member.MemberDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ShopToyApplication.class)
@ExtendWith({SpringExtension.class })
public class OrderApiControllerTests {
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDtoMapper orderDtoMapper;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    // 회원가입 REQUEST DTO
    private MemberDto.RegisterMemberRequest newMemberDto() {
        return MemberDto.RegisterMemberRequest.builder()
                .memberId("test12345")
                .memberEmail("test12345@testEmail.com")
                .memberPassword("abcdf1234!@#$")
                .build();
    }

    // 로그인 REQUEST DTO
    private MemberDto.LoginMemberRequest loginMemberDto(MemberDto.RegisterMemberRequest member) {
        return MemberDto.LoginMemberRequest.builder()
                .memberId(member.getMemberId())
                .memberPassword(member.getMemberPassword())
                .build();
    }

    // 상품 주문 REQUEST DTO
    private OrderDto.RegisterOrderRequest newOrderDto() {
        List<OrderDto.RegisterOrderItemRequest> orderItems = new ArrayList<>();
        List<OrderDto.RegisterOrderItemOptionGroupRequest> orderItemOptionGroups = new ArrayList<>();
        List<OrderDto.RegisterOrderItemOptionRequest> orderItemOptions = new ArrayList<>();
        var orderItemOption = OrderDto.RegisterOrderItemOptionRequest.builder()
                .itemOptionName("a타입")
                .itemOptionPrice(0L)
                .ordering(1).build();
        orderItemOptions.add(orderItemOption);

        var orderItemOptionGroup = OrderDto.RegisterOrderItemOptionGroupRequest.builder()
                .itemOptionGroupName("종류")
                .ordering(1)
                .orderItemOptionList(orderItemOptions).build();
        orderItemOptionGroups.add(orderItemOptionGroup);

        var orderItem = OrderDto.RegisterOrderItemRequest.builder()
                .itemName("남성 스포츠웨어 반바지 3타입")
                .orderCount(1)
                .itemPrice(9800L)
                .itemToken("itm_dFaGqJiLTGCZhFzVYjkq")
                .orderItemOptionGroupList(orderItemOptionGroups).build();
        orderItems.add(orderItem);

        return OrderDto.RegisterOrderRequest.builder()
                .etcMessage("문 앞에 놓아주세요.")
                .receiverAddress1("서울시 강남구")
                .receiverAddress2("강남 아파트 101호")
                .receiverName("홍길동")
                .receiverPhone("010-0000-0000")
                .receiverZipcode("01234")
                .orderItemList(orderItems).build();

    }

    @Test
    @Transactional
    @DisplayName("상품 주문 - 정상적으로 데이터 입력")
    public void registerOrder() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var memberDto = newMemberDto();
        memberService.registerMember(memberDto.toCommand());
        var loginDto = loginMemberDto(memberDto);
        var tokenInfo = memberService.loginMember(loginDto.toCommand());
        var request = newOrderDto();

        this.mockMvc.perform(post("/api/v1/orders")
                        .header("content-type", "application/json")
                        .header("Authorization", "Bearer " + tokenInfo.getAccessToken())
                        .content(mapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("상품 주문 - 비로그인 상태일 때")
    public void registerOrderNoAuthorization() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var request = newOrderDto();

        this.mockMvc.perform(post("/api/v1/orders")
                        .header("content-type", "application/json")
                        .content(mapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("상품 주문 전체 조회 - 로그인 상태 조회")
    public void retrieveAllOrder() throws Exception {
        var memberDto = newMemberDto();
        memberService.registerMember(memberDto.toCommand());
        var loginDto = loginMemberDto(memberDto);
        var tokenInfo = memberService.loginMember(loginDto.toCommand());
        var orderToken = orderService.registerOrder(orderDtoMapper.of(newOrderDto()), tokenInfo.getAccessToken());

        this.mockMvc.perform(get("/api/v1/orders")
                        .header("content-type", "application/json")
                        .header("Authorization", "Bearer " + tokenInfo.getAccessToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("상품 주문 전체 조회 - 비 로그인 상태일 때")
    public void retrieveAllOrderNoAuthorization() throws Exception {
        this.mockMvc.perform(get("/api/v1/orders")
                        .header("content-type", "application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("상품 주문 개별 조회 - 로그인 상태일 때")
    public void retrieveOrder() throws Exception {
        var memberDto = newMemberDto();
        memberService.registerMember(memberDto.toCommand());
        var loginDto = loginMemberDto(memberDto);
        var tokenInfo = memberService.loginMember(loginDto.toCommand());
        var orderToken = orderService.registerOrder(orderDtoMapper.of(newOrderDto()), tokenInfo.getAccessToken());
        this.mockMvc.perform(get("/api/v1/orders/{orderToken}", orderToken)
                        .header("content-type", "application/json")
                        .header("Authorization", "Bearer " + tokenInfo.getAccessToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("상품 주문 개별 조회 - 비 로그인 상태일 때")
    public void retrieveOrderNoAuthorization() throws Exception {
        var memberDto = newMemberDto();
        memberService.registerMember(memberDto.toCommand());
        var loginDto = loginMemberDto(memberDto);
        var tokenInfo = memberService.loginMember(loginDto.toCommand());
        var orderToken = orderService.registerOrder(orderDtoMapper.of(newOrderDto()), tokenInfo.getAccessToken());
        this.mockMvc.perform(get("/api/v1/orders/{orderToken}", orderToken)
                        .header("content-type", "application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
