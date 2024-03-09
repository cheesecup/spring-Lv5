package com.hh99.basket.service;

import com.hh99.basket.entity.BasketItem;
import com.hh99.basket.repository.BasketItemRepository;
import com.hh99.basket.request.BasketItemRequestDTO;
import com.hh99.basket.request.BasketItemUpdateRequestDTO;
import com.hh99.basket.response.BasketItemListResponseDTO;
import com.hh99.basket.response.BasketItemResponseDTO;
import com.hh99.global.exception.BadRequestException;
import com.hh99.global.exception.ErrorMsg;
import com.hh99.global.security.UserDetailsImpl;
import com.hh99.item.entity.Item;
import com.hh99.item.repository.ItemRepository;
import com.hh99.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BasketItemService {

    private final BasketItemRepository basketItemRepository;
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public BasketItemResponseDTO createBasketItem(BasketItemRequestDTO requestDTO, UserDetailsImpl userDetails) {
        Item item = itemRepository.findById(requestDTO.getItemId())
                .orElseThrow(() -> new BadRequestException(ErrorMsg.NOT_FOUND_ITEM));
        Member member = userDetails.getMember();
        
        // 장바구니에 해당 상품이 존재하면 장바구니에 상품 추가 X
        boolean existsCheck = basketItemRepository.existsByMemberAndItem(member, item);
        if (existsCheck) {
            return null;
        }

        BasketItem basketItem = basketItemRepository.save(new BasketItem(requestDTO, member, item));

        return modelMapper.map(basketItem, BasketItemResponseDTO.class);
    }

    @Transactional(readOnly = true)
    public BasketItemListResponseDTO getBasketItemList(UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        List<BasketItem> basketItemList = basketItemRepository.findAllByMember(member);

        // 장바구니 상품 총 합계
        long sumPrice = 0L;
        for (BasketItem basketItem : basketItemList){
            sumPrice += (long) basketItem.getBasketItemStock() * basketItem.getItem().getPrice();
        }

        List<BasketItemResponseDTO> basketItemResponseDTOList = basketItemList.stream()
                .map(basketItem -> modelMapper.map(basketItem, BasketItemResponseDTO.class)).toList();

        return new BasketItemListResponseDTO(sumPrice, basketItemResponseDTOList);
    }

    @Transactional
    public BasketItemResponseDTO updateBasketItem(BasketItemUpdateRequestDTO requestDTO, UserDetailsImpl userDetails) {
        BasketItem basketItem = basketItemRepository.findById(requestDTO.getBasketItemId())
                .orElseThrow(() -> new BadRequestException(ErrorMsg.NOT_FOUND_ITEM));
        validateBasketItem(basketItem, userDetails);

        basketItem.updateBasketItemStock(requestDTO.getBasketItemStock());

        return modelMapper.map(basketItem, BasketItemResponseDTO.class);
    }

    @Transactional
    public Long deleteBasketItem(Long basketItemId, UserDetailsImpl userDetails) {
        BasketItem basketItem = basketItemRepository.findById(basketItemId)
                .orElseThrow(() -> new BadRequestException(ErrorMsg.NOT_FOUND_BASKET_ITEM));
        validateBasketItem(basketItem, userDetails);

        basketItemRepository.delete(basketItem);

        return basketItemId;
    }

    // 장바구니에 상품을 저장한 회원과 로그인한 회원 일치 여부 체크
    private void validateBasketItem(BasketItem basketItem, UserDetailsImpl userDetails) {
        if (!basketItem.getMember().getMemberId().equals(userDetails.getMember().getMemberId())) {
            throw new BadRequestException(ErrorMsg.NOT_HAVE_PERMISSION);
        }
    }
}
