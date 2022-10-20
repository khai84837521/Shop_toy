package com.example.shop_toy.interfaces.item;

import com.example.shop_toy.ShopToyApplication;
import com.example.shop_toy.domain.item.ItemService;
import com.example.shop_toy.domain.member.MemberService;
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
public class ItemApiControllerTests {
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemDtoMapper itemDtoMapper;

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

    // 상품 등록 REQUEST DTO
    private ItemDto.RegisterItemRequest newItemDto() {
        List<ItemDto.RegisterItemOptionGroupRequest> itemOptionGroups = new ArrayList<>();
        List<ItemDto.RegisterItemOptionRequest> itemOptions1 = new ArrayList<>();
        var itemSizeOption1 = ItemDto.RegisterItemOptionRequest.builder()
                .itemOptionName("SMALL")
                .ordering(1)
                .itemOptionPrice(0L)
                .build();
        var itemSizeOption2 = ItemDto.RegisterItemOptionRequest.builder()
                .itemOptionName("MEDIUM")
                .ordering(2)
                .itemOptionPrice(0L)
                .build();
        var itemSizeOption3 = ItemDto.RegisterItemOptionRequest.builder()
                .itemOptionName("LARGE")
                .ordering(3)
                .itemOptionPrice(0L)
                .build();
        itemOptions1.add(itemSizeOption1);
        itemOptions1.add(itemSizeOption2);
        itemOptions1.add(itemSizeOption3);
        
        var itemOptionGroup1 = ItemDto.RegisterItemOptionGroupRequest.builder()
                .itemOptionGroupName("사이즈")
                .ordering(1)
                .itemOptionList(itemOptions1)
                .build();
        itemOptionGroups.add(itemOptionGroup1);

        List<ItemDto.RegisterItemOptionRequest> itemOptions2 = new ArrayList<>();
        var itemColorOption1 = ItemDto.RegisterItemOptionRequest.builder()
                .itemOptionName("GREEN")
                .ordering(1)
                .itemOptionPrice(0L)
                .build();
        var itemColorOption2 = ItemDto.RegisterItemOptionRequest.builder()
                .itemOptionName("BLUE")
                .ordering(2)
                .itemOptionPrice(0L)
                .build();
        var itemColorOption3 = ItemDto.RegisterItemOptionRequest.builder()
                .itemOptionName("BLACK")
                .ordering(3)
                .itemOptionPrice(5000L)
                .build();
        itemOptions2.add(itemColorOption1);
        itemOptions2.add(itemColorOption2);
        itemOptions2.add(itemColorOption3);

        var itemOptionGroup2 = ItemDto.RegisterItemOptionGroupRequest.builder()
                .itemOptionGroupName("색상")
                .ordering(2)
                .itemOptionList(itemOptions2)
                .build();
        itemOptionGroups.add(itemOptionGroup2);


        return ItemDto.RegisterItemRequest.builder()
                .itemName("티셔츠")
                .itemPrice(25000L)
                .itemOptionGroupList(itemOptionGroups)
                .build();
    }


    @Test
    @Transactional
    @DisplayName("상품 등록 - 정상적으로 데이터를 입력")
    public void registerItem() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var memberDto = newMemberDto();
        memberService.registerMember(memberDto.toCommand());
        var loginDto = loginMemberDto(memberDto);
        var tokenInfo = memberService.loginMember(loginDto.toCommand());
        var request = newItemDto();

        this.mockMvc.perform(post("/api/v1/items")
                        .header("content-type", "application/json")
                        .header("Authorization", "Bearer " + tokenInfo.getAccessToken())
                        .content(mapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("상품 등록 - 비로그인 상태일 때")
    public void registerItemNoAuthorization() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var request = newItemDto();

        this.mockMvc.perform(post("/api/v1/items")
                        .header("content-type", "application/json")
                        .content(mapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("상품 전체 조회 - 정상적으로 데이터를 입력")
    public void retrieveAllItem() throws Exception {
        this.mockMvc.perform(get("/api/v1/items")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("상품 개별 조회 - 정상적으로 데이터를 입력")
    public void retrieveItem() throws Exception {
        var memberDto = newMemberDto();
        memberService.registerMember(memberDto.toCommand());
        var loginDto = loginMemberDto(memberDto);
        var tokenInfo = memberService.loginMember(loginDto.toCommand());
        var itemToken = itemService.registerItem(itemDtoMapper.of(newItemDto()), tokenInfo.getAccessToken());

        this.mockMvc.perform(get("/api/v1/items/{itemToken}", itemToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }


}
