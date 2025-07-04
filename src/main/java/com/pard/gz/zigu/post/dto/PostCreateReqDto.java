package com.pard.gz.zigu.post.dto;

import com.pard.gz.zigu.Image.entity.Image;
import com.pard.gz.zigu.post.entity.enums.Category;
import com.pard.gz.zigu.post.entity.enums.IsBorrowable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateReqDto {
    private IsBorrowable isBorrowable;
    private String itemName;
    private Long pricePerHour;
    private Long pricePerDay;
    private Category category;
    private String description;
    private String caution;
    private List<MultipartFile> images;

    public Category getCategory(){
        this.category = Category.ETC;
        return this.category;
    }
    public IsBorrowable getIsBorrowable() {
        this.isBorrowable = IsBorrowable.POSSIBLE;
        return isBorrowable;
    }
}
