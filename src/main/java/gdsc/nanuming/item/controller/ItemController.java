package gdsc.nanuming.item.controller;

import static gdsc.nanuming.common.code.CommonCode.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gdsc.nanuming.common.response.BaseResponseWithData;
import gdsc.nanuming.item.dto.request.AddItemRequest;
import gdsc.nanuming.item.dto.response.AddItemResponse;
import gdsc.nanuming.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;

	@PostMapping
	public BaseResponseWithData<AddItemResponse> addItem(@RequestBody AddItemRequest addItemRequest) {
		log.info(">>> Run ItemController addItem()");
		return BaseResponseWithData.of(RESPONSE_SUCCESS, itemService.addItem(addItemRequest));
	}

}
