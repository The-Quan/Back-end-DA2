package org.example.doanki2.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class NewsBookService {

    @Autowired
    private BookRepository newsBookRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=most popular books and what are the benefits of books&maxResults=25&key=AIzaSyBy3nQtKO4EpjSHtZyAwjhW_OCDQe9mrZs";

    public void fetchAndSaveBooks() {
        ApiResponseDTO response = restTemplate.getForObject(apiUrl, ApiResponseDTO.class);
        if (response != null && response.getItems() != null) {
            // Xóa tất cả các sách hiện có trong cơ sở dữ liệu
            newsBookRepository.deleteAll();

            // Lưu trữ các sách mới từ API
            for (VolumeDTO volumeDTO : response.getItems()) {
                VolumeInfoDTO volumeInfo = volumeDTO.getVolumeInfo();
                News_Book newsBook = new News_Book();
                newsBook.setTitle(volumeInfo.getTitle());
                newsBook.setDescription(volumeInfo.getDescription());
                newsBook.setThumbnail(volumeInfo.getImageLinks() != null ? volumeInfo.getImageLinks().getThumbnail() : null);
                newsBook.setPreview_Link(volumeInfo.getPreviewLink());
                newsBook.setInfo_Link(volumeInfo.getInfoLink());
                newsBook.setCanonical_Volume_Link(volumeInfo.getCanonicalVolumeLink());
                newsBook.setApproved(volumeInfo.getApproved());
                newsBookRepository.save(newsBook);
            }
        }
    }

    public ResponseEntity<List<News_Book>> getAll(){
        List<News_Book> getAll = newsBookRepository.findByApproved();
        return ResponseEntity.ok().body(getAll);
    }
    public ResponseEntity<List<News_Book>> getAllFalse(){
        List<News_Book> getAll = newsBookRepository.findByApprovedFalse();
        return ResponseEntity.ok().body(getAll);
    }
    public ResponseEntity<News_Book> deleteById(int id){
        newsBookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    public ResponseEntity<News_Book> update(int id, News_Book newsBook){
        Optional<News_Book> newsBook1 = newsBookRepository.findById(id);
        if (!newsBook1.isPresent()){
            throw new IllegalArgumentException("id news book not found");
        }
        newsBook.setId(newsBook1.get().getId());
        newsBookRepository.save(newsBook);

        return ResponseEntity.ok().body(newsBook);
    }
}