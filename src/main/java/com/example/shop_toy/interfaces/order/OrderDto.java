package com.example.shop_toy.interfaces.order;


import com.example.shop_toy.domain.order.Order;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

public class OrderDto {

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "상품 주문 요청 데이터", description = "상품 주문에 필요한 정보 입니다.")
    public static class RegisterOrderRequest {
        @NotBlank(message = "receiverName 는 필수값입니다")
        @ApiModelProperty(name = "receiverName", example = "홍길동", notes = "주문자 이름 입니다.", required = true)
        private String receiverName;

        @NotBlank(message = "receiverPhone 는 필수값입니다")
        @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식에 맞지 않습니다.")
        @ApiModelProperty(name = "receiverPhone", example = "010-0000-0000", notes = "주문자 전화번호 입니다.", required = true)
        private String receiverPhone;

        @NotBlank(message = "receiverZipcode 는 필수값입니다")
        @ApiModelProperty(name = "receiverZipcode", example = "01234", notes = "배송지 우편번호 입니다.", required = true)
        private String receiverZipcode;

        @NotBlank(message = "receiverAddress1 는 필수값입니다")
        @ApiModelProperty(name = "receiverAddress1", example = "서울시 강남구", notes = "배송지 주소 입니다.", required = true)
        private String receiverAddress1;

        @NotBlank(message = "receiverAddress2 는 필수값입니다")
        @ApiModelProperty(name = "receiverAddress2", example = "강남 아파트 101호", notes = "배송지 상세 주소 입니다.", required = true)
        private String receiverAddress2;

        @NotBlank(message = "etcMessage 는 필수값입니다")
        @ApiModelProperty(name = "etcMessage", example = "문 앞에 놓아주세요.", notes = "배송 메시지 입니다.", required = true)
        private String etcMessage;

        @ApiModelProperty(name = "orderItemList", notes = "주문할 상품의 리스트 입니다.", required = true)
        private List<RegisterOrderItemRequest> orderItemList;

    }

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "주문 상품 요청 데이터", description = "주문한 상품에 필요한 정보 입니다.")
    public static class RegisterOrderItemRequest {
        @NotNull(message = "orderCount 는 필수값입니다")
        @ApiModelProperty(name = "orderCount", example = "1", notes = "주문할 상품의 개수 입니다.", required = true)
        private Integer orderCount;

        @NotBlank(message = "itemToken 는 필수값입니다")
        @ApiModelProperty(name = "itemToken", example = "itm_dFaGqJiLTGCZhFzVYjkq", notes = "상품의 토큰 입니다.", required = true)
        private String itemToken;

        @NotBlank(message = "itemName 는 필수값입니다")
        @ApiModelProperty(name = "itemToken", example = "남성 스포츠웨어 반바지 3타입", notes = "상품의 이름 입니다.", required = true)
        private String itemName;

        @NotNull(message = "itemPrice 는 필수값입니다")
        @ApiModelProperty(name = "itemPrice", example = "9800", notes = "상품의 가격 입니다.", required = true)
        private Long itemPrice;

        @ApiModelProperty(name = "orderItemOptionGroupList", notes = "주문 상품의 옵션 그룹 리스트 입니다.", required = true)
        private List<RegisterOrderItemOptionGroupRequest> orderItemOptionGroupList;
    }

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "주문 상품 옵션 그룹 등록 요청 데이터", description = "주문 상품 옵션 그룹 등록에 필요한 정보 입니다.")
    public static class RegisterOrderItemOptionGroupRequest {
        @NotNull(message = "ordering 는 필수값입니다")
        @ApiModelProperty(name = "ordering", example = "1", notes = "주문 상품의 옵션 그룹의 정렬 순서 입니다.", required = true)
        private Integer ordering;

        @NotBlank(message = "itemOptionGroupName 는 필수값입니다")
        @ApiModelProperty(name = "itemOptionGroupName", example = "종류", notes = "주문 상품의 옵션 그룹 이름 입니다.", required = true)
        private String itemOptionGroupName;

        @ApiModelProperty(name = "orderItemOptionList", notes = "주문 상품의 옵션 리스트 입니다.", required = true)
        private List<RegisterOrderItemOptionRequest> orderItemOptionList;
    }

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "주문 상품 옵션 등록 요청 데이터", description = "주문 상품 옵션 등록에 필요한 정보 입니다.")
    public static class RegisterOrderItemOptionRequest {
        @NotNull(message = "ordering 는 필수값입니다")
        @ApiModelProperty(name = "ordering", example = "1", notes = "주문 상품의 옵션의 정렬 순서 입니다.", required = true)
        private Integer ordering;

        @NotBlank(message = "itemOptionName 는 필수값입니다")
        @ApiModelProperty(name = "itemOptionName", example = "a타입", notes = "주문 상품의 옵션 이름 입니다.", required = true)
        private String itemOptionName;

        @NotNull(message = "itemOptionPrice 는 필수값입니다")
        @ApiModelProperty(name = "itemOptionPrice", example = "0", notes = "주문 상품의 옵션 가격 입니다.", required = true)
        private Long itemOptionPrice;
    }

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "상품 주문 응답 데이터", description = "주문 토큰을 반환 합니다.")
    public static class RegisterResponse {
        @ApiModelProperty(name = "orderToken", example = "ord_631eCfNg6g79g40V", notes = "주문 상품의 토큰 입니다.")
        private final String orderToken;
    }

    // 조회
    @Getter
    @Builder
    @ToString
    @ApiModel(value = "주문 조회 응답 데이터", description = "주문 정보를 반환 합니다.")
    public static class Main {
        @ApiModelProperty(name = "orderToken", example = "ord_631eCfNg6g79g40V", notes = "주문 상품의 토큰 입니다.")
        private final String orderToken;
        @ApiModelProperty(name = "memberId", example = "test1324", notes = "주문자 아이디 입니다.")
        private final String memberId;
        @ApiModelProperty(name = "totalAmount", example = "120000", notes = "주문한 상품들의 총 가격 입니다.")
        private final Long totalAmount;
        @ApiModelProperty(name = "deliveryInfo", notes = "배송지 정보 입니다.")
        private final DeliveryInfo deliveryInfo;
        @ApiModelProperty(name = "orderedAt", notes = "주문 일시 입니다.")
        private final String orderedAt;
        @ApiModelProperty(name = "status", example = "INIT", notes = "주문 상태 입니다.")
        private final String status;
        @ApiModelProperty(name = "statusDescription", example = "주문시작", notes = "주문 상태 설명 입니다.")
        private final String statusDescription;
        @ApiModelProperty(name = "orderItemList", notes = "주문 상품 리스트 입니다.")
        private final List<OrderItem> orderItemList;
    }

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "배송지 정보", description = "주문자 배송지 데이터 입니다.")
    public static class DeliveryInfo {
        @ApiModelProperty(name = "receiverName", example = "홍길동", notes = "주문자 이름 입니다.")
        private final String receiverName;
        @ApiModelProperty(name = "receiverPhone", example = "010-0000-0000", notes = "주문자 전화번호 입니다.")
        private final String receiverPhone;
        @ApiModelProperty(name = "receiverZipcode", example = "01234", notes = "배송지 우편번호 입니다.")
        private final String receiverZipcode;
        @ApiModelProperty(name = "receiverAddress1", example = "서울시 강남구", notes = "배송지 주소 입니다.")
        private final String receiverAddress1;
        @ApiModelProperty(name = "receiverAddress2", example = "강남 아파트 101호", notes = "배송지 상세 주소 입니다.")
        private final String receiverAddress2;
        @ApiModelProperty(name = "etcMessage", example = "문 앞에 놓아주세요.", notes = "배송 메시지 입니다.")
        private final String etcMessage;
    }

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "주문 상품 데이터", description = "주문 상품 데이터 입니다.")
    public static class OrderItem {

        @ApiModelProperty(name = "orderCount", example = "1", notes = "주문할 상품의 개수 입니다.")
        private final Integer orderCount;
        @ApiModelProperty(name = "memberId", example = "test1324", notes = "주문자 아이디 입니다.")
        private final String memberId;
        @ApiModelProperty(name = "itemToken", example = "itm_DvPyvXTvyoxPsoglmFUx", notes = "상품의 토큰 입니다.")
        private final String itemToken;
        @ApiModelProperty(name = "itemName", example = "티셔츠", notes = "상품의 이름 입니다.")
        private final String itemName;
        @ApiModelProperty(name = "totalAmount", example = "120000", notes = "주문한 상품들의 총 가격 입니다.")
        private final Long totalAmount;
        @ApiModelProperty(name = "itemPrice", example = "10000", notes = "상품의 가격 입니다.")
        private final Long itemPrice;
        @ApiModelProperty(name = "deliveryStatus", example = "BEFORE_DELIVERY", notes = "배송 상태 입니다.")
        private final String deliveryStatus;
        @ApiModelProperty(name = "deliveryStatusDescription", example = "배송전", notes = "배송 상태 설명 입니다.")
        private final String deliveryStatusDescription;
        @ApiModelProperty(name = "orderItemOptionGroupList", notes = "주문 상품의 옵션 그룹 리스트 입니다.")
        private final List<OrderItemOptionGroup> orderItemOptionGroupList;
    }

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "주문 상품 옵션 그룹 데이터", description = "주문 상품 옵션 그룹 데이터 입니다.")
    public static class OrderItemOptionGroup {
        @ApiModelProperty(name = "ordering", example = "1", notes = "주문 상품의 옵션 그룹의 정렬 순서 입니다.")
        private final Integer ordering;

        @ApiModelProperty(name = "itemOptionGroupName", example = "색상", notes = "주문 상품의 옵션 그룹 이름 입니다.")
        private final String itemOptionGroupName;

        @ApiModelProperty(name = "orderItemOptionList", notes = "주문 상품의 옵션 리스트 입니다.")
        private final List<OrderItemOption> orderItemOptionList;
    }

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "주문 상품 옵션 데이터", description = "주문 상품 옵션 데이터 입니다.")
    public static class OrderItemOption {
        @ApiModelProperty(name = "ordering", example = "1", notes = "주문 상품의 옵션의 정렬 순서 입니다.", required = true)
        private final Integer ordering;

        @ApiModelProperty(name = "itemOptionName", example = "RED", notes = "주문 상품의 옵션 이름 입니다.", required = true)
        private final String itemOptionName;

        @ApiModelProperty(name = "itemOptionPrice", example = "0", notes = "주문 상품의 옵션 가격 입니다.", required = true)
        private final Long itemOptionPrice;
    }

    @Getter
    @ToString
    @ApiModel(value = "주문 검색 요청 데이터", description = "조회할 주문 요청 데이터 입니다.")
    public static class SearchOrderRequest {
        @Pattern(regexp = "(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])", message = "날짜 형식은 YYYY-MM-DD 형태로 작성 해야 합니다.")
        @ApiModelProperty(name = "startDate", example = "2022-08-01", notes = "조회할 시작 날짜 입니다.")
        private final String startDate;

        @Pattern(regexp = "(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])", message = "날짜 형식은 YYYY-MM-DD 형태로 작성 해야 합니다.")
        @ApiModelProperty(name = "endDate", example = "2022-08-31", notes = "조회할 마지막 날짜 입니다.")
        private final String endDate;

        @ApiModelProperty(name = "status", notes = "조회할 주문 상태 입니다.")
        private final Order.Status status;

        public SearchOrderRequest(String startDate, String endDate, Order.Status status) {
            if (StringUtils.isBlank(startDate)) {
                LocalDate firstDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
                startDate = firstDate.format(DateTimeFormatter.ISO_DATE);
            }
            if (StringUtils.isEmpty(endDate)) {
                LocalDate firstDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
                endDate = firstDate.format(DateTimeFormatter.ISO_DATE);
            }
            if (status == null) {
                status = Order.Status.INIT;
            }
            this.startDate = startDate;
            this.endDate = endDate;
            this.status = status;
        }
    }

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "주문 리스트 조회 데이터", description = "주문 리스트 조회 데이터 입니다.")
    public static class OrderListResponse {
        @ApiModelProperty(name = "page", example = "1", notes = "현재 페이지 입니다.")
        private final int page;
        @ApiModelProperty(name = "totalPage", example = "10", notes = "전체 페이지 수 입니다.")
        private final int totalPage;
        @ApiModelProperty(name = "size", example = "50", notes = "현재 페이지 사이즈 입니다.")
        private final int size;
        @ApiModelProperty(name = "status", notes = "조회한 주문 상태 입니다.")
        private final Order.Status status;
        @ApiModelProperty(name = "startDate", example = "2022-08-01", notes = "조회한 시작 날짜 입니다.")
        private final String startDate;
        @ApiModelProperty(name = "endDate", example = "2022-08-31", notes = "조회한 마지막 날짜 입니다.")
        private final String endDate;
        @ApiModelProperty(name = "orderInfoList", notes = "주문 목록 입니다.")
        private final List<Main> orderInfoList;
    }
}
