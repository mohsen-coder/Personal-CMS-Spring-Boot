package mohsen.coder.personalcmsblogspring.application.port.out;

import mohsen.coder.personalcmsblogspring.domain.Category;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Optional;

public interface GetCategoryPort {
    Optional<Category> getCategoryByPublicId(String publicId);

    Optional<Category> getCategoryByTitle(String title);

    Collection<Category> getCategoriesByPagination(String Status, Pageable pageable);
}
