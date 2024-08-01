package gdsc.nanuming.image.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import gdsc.nanuming.image.entity.ItemImage;
import gdsc.nanuming.image.repository.ItemImageRepository;
import gdsc.nanuming.item.entity.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

	@Value("${AWS_S3_BUCKET_NAME}")
	private String bucketName;

	private final AmazonS3 amazonS3;
	private final ItemImageRepository itemImageRepository;

	private final static String S3_URL = "https://s3.amazonaws.com/";
	private final static String SLASH = "/";
	private final static String POINT = ".";
	private final static String ITEM = "item";
	private final static String CONFIRM = "confirm";

	@Transactional
	public List<ItemImage> uploadItemImage(List<MultipartFile> multipartFileList, Item temporarySavedItem) {

		List<ItemImage> itemImageList = new ArrayList<>();
		for (MultipartFile itemImage : multipartFileList) {
			try {
				String uuid = UUID.randomUUID().toString();
				String extension = extractExtension(itemImage.getContentType());
				String objectKey = ITEM + SLASH + temporarySavedItem.getId() + SLASH + uuid + extension;

				ObjectMetadata metadata = new ObjectMetadata();
				metadata.setContentType(itemImage.getContentType());
				metadata.setContentLength(itemImage.getSize());

				amazonS3.putObject(new PutObjectRequest(bucketName, objectKey, itemImage.getInputStream(), metadata)
					.withCannedAcl(CannedAccessControlList.PublicRead));

				String uploadedImageUrl = S3_URL + bucketName + SLASH + objectKey;

				ItemImage savedItemImage = itemImageRepository.save(ItemImage.from(uploadedImageUrl, false));
				itemImageList.add(savedItemImage);
			} catch (IOException e) {
				throw new RuntimeException("File upload Failure", e);
			}
		}
		return itemImageList;
	}

	@Transactional
	public ItemImage uploadConfirmItemImage(MultipartFile itemImage, Item temporarySavedItem) {
		try {
			String uuid = UUID.randomUUID().toString();
			String extension = extractExtension(itemImage.getContentType());
			String objectKey = CONFIRM + SLASH + temporarySavedItem.getId() + SLASH + uuid + extension;

			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(itemImage.getContentType());
			metadata.setContentLength(itemImage.getSize());

			amazonS3.putObject(new PutObjectRequest(bucketName, objectKey, itemImage.getInputStream(), metadata)
				.withCannedAcl(CannedAccessControlList.PublicRead));

			String uploadedImageUrl = S3_URL + bucketName + SLASH + objectKey;

			return itemImageRepository.save(ItemImage.from(uploadedImageUrl, false));
		} catch (IOException e) {
			throw new IllegalArgumentException("Storage creation error", e);
		}
	}

	private String extractExtension(String contentType) {
		String extension = "";
		if (contentType != null && contentType.contains(SLASH)) {
			extension = POINT + contentType.split(SLASH)[1];
		}
		return extension;
	}
}
