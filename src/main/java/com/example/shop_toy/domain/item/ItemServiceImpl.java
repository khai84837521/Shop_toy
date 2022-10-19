package com.example.shop_toy.domain.item;


import com.example.shop_toy.common.exception.InvalidParamException;
import com.example.shop_toy.common.response.ErrorCode;
import com.example.shop_toy.common.util.jwt.JwtTokenProvider;
import com.example.shop_toy.domain.member.MemberReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final MemberReader memberReader;
    private final ItemStore itemStore;
    private final ItemReader itemReader;
    private final ItemOptionSeriesFactory itemOptionSeriesFactory;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 상품 등록 서비스
     * @param command       상품 등록 DTO
     * @param accessToken   엑세스 토큰
     * @return              상품 토큰
     */
    @Override
    @Transactional
    public String registerItem(ItemCommand.RegisterItemRequest command, String accessToken) {
        if (!jwtTokenProvider.validateToken(accessToken)) throw new InvalidParamException(ErrorCode.MEMBER_FAIL_INVALID_TOKEN);
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        String memberId = authentication.getName();

        var member = memberReader.getMemberById(memberId);
        var initItem = command.toEntity(member.getMemberId());
        var item = itemStore.store(initItem);
        itemOptionSeriesFactory.store(command, item);
        return item.getItemToken();
    }

    /**
     * 상품 정보 열람 서비스
     * @param itemToken     상품 토큰
     * @return              상품 정보 객체
     */
    @Override
    @Transactional(readOnly = true)
    public ItemInfo.Main retrieveItemInfo(String itemToken) {
        var item = itemReader.getItemBy(itemToken);
        var itemOptionGroupInfoList = itemReader.getItemOptionSeries(item);
        return new ItemInfo.Main(item, itemOptionGroupInfoList);
    }

    /**
     * 상품 정보 리스트 열람 서비스
     * @param keyword       검색 키워드
     * @param pageable      페이지 객체
     * @return              상품 리스트 정보 객체
     */
    @Override
    public ItemInfo.ItemList retrieveAllItemInfo(String keyword, Pageable pageable) {
        var itemList = itemReader.findItemByKeyword(keyword, pageable);
        var itemInfoList =  itemList.stream().map(item -> {
            var itemOptionGroupInfoList = itemReader.getItemOptionSeries(item);
            return new ItemInfo.Main(item, itemOptionGroupInfoList);
        }).collect(Collectors.toList());

        return ItemInfo.ItemList.builder()
                .page(pageable.getPageNumber()+1)
                .totalPage(itemList.getTotalPages())
                .size(pageable.getPageSize())
                .keyword(keyword)
                .itemInfoList(itemInfoList)
                .build();
    }

    /**
     * 상품 전체 삭제 서비스 (테스트 용)
     */
    @Deprecated
    @Override
    @Transactional
    public void deleteAllItem() {
        // 상품 일괄 삭제
        itemOptionSeriesFactory.deleteAll();
        itemStore.deleteAll();
    }
}
