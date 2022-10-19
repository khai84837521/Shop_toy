package com.example.shop_toy.interfaces.item;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ItemDto {

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "상품 등록 요청 데이터", description = "상품 등록에 필요한 정보 입니다.")
    public static class RegisterItemRequest {
        @NotEmpty(message = "itemName 은 필수 값 입니다.")
        @ApiModelProperty(name = "itemName", example = "티셔츠", notes = "상품의 이름 입니다.", required = true)
        private String itemName;

        @NotNull(message = "itemPrice 은 필수 값 입니다.")
        @ApiModelProperty(name = "itemPrice", example = "10000", notes = "상품의 가격 입니다.", required = true)
        private Long itemPrice;

        @ApiModelProperty(name = "itemOptionGroupList", notes = "상품의 옵션 그룹 리스트 입니다.", required = true)
        private List<RegisterItemOptionGroupRequest> itemOptionGroupList;
    }

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "상품 옵션 그룹 등록 요청 데이터", description = "상품 옵션 그룹 등록에 필요한 정보 입니다.")
    public static class RegisterItemOptionGroupRequest {
        @ApiModelProperty(name = "ordering", example = "1", notes = "상품의 옵션 그룹의 정렬 순서 입니다.", required = true)
        private Integer ordering;

        @ApiModelProperty(name = "itemOptionGroupName", example = "색상", notes = "상품의 옵션 그룹 이름 입니다.", required = true)
        private String itemOptionGroupName;

        @ApiModelProperty(name = "itemOptionList", notes = "상품의 옵션 리스트 입니다.", required = true)
        private List<RegisterItemOptionRequest> itemOptionList;
    }

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "상품 옵션 등록 요청 데이터", description = "상품 옵션 등록에 필요한 정보 입니다.")
    public static class RegisterItemOptionRequest {
        @ApiModelProperty(name = "ordering", example = "1", notes = "상품의 옵션의 정렬 순서 입니다.", required = true)
        private Integer ordering;

        @ApiModelProperty(name = "itemOptionName", example = "RED", notes = "상품의 옵션 이름 입니다.", required = true)
        private String itemOptionName;

        @ApiModelProperty(name = "itemOptionPrice", example = "0", notes = "상품의 옵션 가격 입니다.", required = true)
        private Long itemOptionPrice;
    }

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "상품 등록 응답", description = "등록한 상품의 토큰 입니다.")
    public static class RegisterItemResponse {
        @ApiModelProperty(name = "itemToken", example = "itm_631eCfNg6g79g40V", notes = "상품의 토큰 입니다.")
        private final String itemToken;
    }

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "상품 리스트 조회 데이터", description = "상품 리스트 조회 데이터 입니다.")
    public static class ItemListResponse {
        @ApiModelProperty(name = "page", example = "1", notes = "현재 페이지 입니다.")
        private final int page;
        @ApiModelProperty(name = "totalPage", example = "10", notes = "전체 페이지 수 입니다.")
        private final int totalPage;
        @ApiModelProperty(name = "size", example = "50", notes = "현재 페이지 사이즈 입니다.")
        private final int size;
        @ApiModelProperty(name = "keyword", example = "티셔츠", notes = "검색 키워드 입니다.")
        private final String keyword;
        @ApiModelProperty(name = "itemInfoList", notes = "상품의 목록 입니다.")
        private final List<Main> itemInfoList;
    }
    
    @Getter
    @Builder
    @ToString
    @ApiModel(value = "상품 조회 데이터", description = "상품 조회 데이터 입니다.")
    public static class Main {
        @ApiModelProperty(name = "itemToken", example = "itm_631eCfNg6g79g40V", notes = "상품의 토큰 입니다.")
        private final String itemToken;

        @ApiModelProperty(name = "memberId", example = "test1234", notes = "상품을 등록한 회원.")
        private final String memberId;

        @ApiModelProperty(name = "itemName", example = "티셔츠", notes = "상품의 이름 입니다.")
        private final String itemName;

        @ApiModelProperty(name = "itemPrice", example = "10000", notes = "상품의 가격 입니다.")
        private final Long itemPrice;

        @ApiModelProperty(name = "itemOptionGroupList", notes = "상품의 옵션 그룹 리스트 입니다.")
        private final List<ItemOptionGroupInfo> itemOptionGroupList;
    }

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "상품 옵션 그룹 조회 데이터", description = "조회하는 상품의 옵션 그룹 데이터 입니다.")
    public static class ItemOptionGroupInfo {
        @ApiModelProperty(name = "ordering", notes = "상품의 옵션 그룹의 정렬 순서 입니다.")
        private final Integer ordering;

        @ApiModelProperty(name = "itemOptionGroupName", notes = "상품의 옵션 그룹 이름 입니다.")
        private final String itemOptionGroupName;

        @ApiModelProperty(name = "itemOptionList", notes = "상품의 옵션 리스트 입니다.")
        private final List<ItemOptionInfo> itemOptionList;
    }

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "상품 옵션 조회 데이터", description = "조회하는 상품의 옵션 데이터 입니다.")
    public static class ItemOptionInfo {
        @ApiModelProperty(name = "ordering", notes = "상품의 옵션의 정렬 순서 입니다.")
        private final Integer ordering;

        @ApiModelProperty(name = "itemOptionName", notes = "상품의 옵션 이름 입니다.")
        private final String itemOptionName;

        @ApiModelProperty(name = "itemOptionPrice", notes = "상품의 옵션 가격 입니다.")
        private final Long itemOptionPrice;
    }
}
