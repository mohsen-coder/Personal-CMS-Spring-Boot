package mohsen.coder.personalcmsblogspring.adapter.persistence;

import mohsen.coder.personalcmsblogspring.adapter.persistence.model.CategoryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends PagingAndSortingRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findCategoryEntityByPublicId(String publicId);

    Optional<CategoryEntity> findCategoryEntityByTitle(String title);

    List<CategoryEntity> findAllByStatus(String status, Pageable pageable);
}
