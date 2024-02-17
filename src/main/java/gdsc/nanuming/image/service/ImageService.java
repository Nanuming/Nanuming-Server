package gdsc.nanuming.image.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

import gdsc.nanuming.image.entity.ItemImage;
import gdsc.nanuming.image.repository.ItemImageRepository;
import gdsc.nanuming.item.entity.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

	@Value("${sm://BUCKET_NAME}")
	private String bucketName;

	private final Storage storage;
	private final ItemImageRepository itemImageRepository;

	private final static String GOOGLE_STORAGE = "https://storage.googleapis.com/";
	private final static String SLASH = "/";
	private final static String POINT = ".";
	private final static String ITEM = "item";

	@Transactional
	public List<ItemImage> uploadItemImage(List<MultipartFile> multipartFileList, Item temporarySavedItem) {

		List<ItemImage> itemImageList = new ArrayList<>();
		for (int i = 0; i < multipartFileList.size(); i++) {
			try {
				MultipartFile itemImage = multipartFileList.get(i);
				String uuid = UUID.randomUUID().toString();
				String extension = itemImage.getContentType();
				extension = extension.replace(SLASH, POINT);
				String blobName = ITEM + SLASH + temporarySavedItem.getId() + SLASH + uuid + extension;

				BlobId blobId = BlobId.of(bucketName, blobName);
				BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
					.setContentType(extension)
					.build();

				storage.create(blobInfo, itemImage.getBytes());

				String uploadedImageUrl = GOOGLE_STORAGE + bucketName + SLASH + blobName;

				ItemImage savedItemImage = itemImageRepository.save(ItemImage.from(uploadedImageUrl, false));
				itemImageList.add(savedItemImage);
			} catch (IOException e) {
				// TODO: need custom exception handler here
				throw new RuntimeException("File upload Failure");
			}
		}
		return itemImageList;
	}

	@Transactional
	public ItemImage uploadConfirmItemImage(MultipartFile itemImage, Item temporarySavedItem) {
		String uuid = UUID.randomUUID().toString();
		String extension = itemImage.getContentType().replace(SLASH, POINT);
		String blobName = temporarySavedItem.getId() + SLASH + uuid + extension;

		BlobId blobId = BlobId.of(bucketName, blobName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
			.setContentType(extension)
			.build();

		try {
			storage.create(blobInfo, itemImage.getBytes());
		} catch (IOException e) {
			throw new IllegalArgumentException("Storage creation error");
		}

		String uploadedImageUrl = GOOGLE_STORAGE + bucketName + SLASH + ITEM + SLASH + blobName;

		return itemImageRepository.save(ItemImage.from(uploadedImageUrl, false));
	}

}
