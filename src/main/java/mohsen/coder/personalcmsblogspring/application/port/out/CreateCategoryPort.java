package mohsen.coder.personalcmsblogspring.application.port.out;

import mohsen.coder.personalcmsblogspring.domain.Category;

import java.util.Optional;

public interface CreateCategoryPort {
    Optional<Category> createCategory(Category category);
}
