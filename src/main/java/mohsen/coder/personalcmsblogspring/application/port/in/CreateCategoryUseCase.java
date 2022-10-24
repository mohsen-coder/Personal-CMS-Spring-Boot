package mohsen.coder.personalcmsblogspring.application.port.in;

import mohsen.coder.personalcmsblogspring.adapter.web.model.CategoryModel;
import mohsen.coder.personalcmsblogspring.domain.Category;
import mohsen.coder.personalcmsblogspring.errors.ConflictException;
import org.springframework.http.ResponseEntity;

public interface CreateCategoryUseCase {
    ResponseEntity<CategoryModel> createCategory(Category category) throws ConflictException;
}
