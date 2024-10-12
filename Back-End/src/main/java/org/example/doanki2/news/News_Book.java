package org.example.doanki2.news;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class News_Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description; // Nội dung
    private String thumbnail; // Hình ảnh nhỏ
    private String preview_Link; // Liên kết xem trước
    private String info_Link; // Liên kết thông tin
    private String canonical_Volume_Link; // Liên kết chính thức
    private Boolean approved = false;
}
