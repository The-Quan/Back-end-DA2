package org.example.doanki2.news;

import lombok.Data;

import java.util.List;

@Data
public class VolumeInfoDTO {
    private String title;
    private String description;
    private ImageLinksDTO imageLinks;
    private String previewLink;
    private String infoLink;
    private String canonicalVolumeLink;
    private Boolean approved = false;

}

@Data
class ImageLinksDTO {
    private String smallThumbnail;
    private String thumbnail;
}

@Data
class VolumeDTO {
    private VolumeInfoDTO volumeInfo;
}

@Data
 class ApiResponseDTO {
    private List<VolumeDTO> items;
}
