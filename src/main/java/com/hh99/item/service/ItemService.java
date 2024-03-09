package com.hh99.item.service;

import com.hh99.global.exception.BadRequestException;
import com.hh99.global.exception.ErrorMsg;
import com.hh99.item.entity.Item;
import com.hh99.item.repository.ItemRepository;
import com.hh99.item.request.ItemRequestDTO;
import com.hh99.item.response.ItemResponseDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public ItemResponseDTO createItem(ItemRequestDTO requestDTO) {
        Item item = itemRepository.save(new Item(requestDTO));

        return modelMapper.map(item, ItemResponseDTO.class);
    }

    @Transactional(readOnly = true)
    public ItemResponseDTO getItem(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMsg.NOT_FOUND_ITEM));

        return modelMapper.map(item,ItemResponseDTO.class);
    }

    @Transactional(readOnly = true)
    public List<ItemResponseDTO> getItemList(String sort, String direction, int pageNum) {
        Sort.Direction sortDirection = (direction.equalsIgnoreCase("ASC")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        if (!sort.equals("itemName") && !sort.equals("price")) { // 상품명, 가격 기준 정렬 요청이 아닌 경우 상품아이디를 기준으로 정렬
            sort = "itemId";
        }

        Pageable pageable = PageRequest.of(pageNum - 1, 5, Sort.by(sortDirection, sort));

        return itemRepository.findAll(pageable).stream().map(item -> modelMapper.map(item, ItemResponseDTO.class)).toList();
    }
}
