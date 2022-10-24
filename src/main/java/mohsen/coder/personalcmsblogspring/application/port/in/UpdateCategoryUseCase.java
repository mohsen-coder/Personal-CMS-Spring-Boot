package mohsen.coder.personalcmsblogspring.application.port.in;

import mohsen.coder.personalcmsblogspring.adapter.web.model.CategoryModel;
import mohsen.coder.personalcmsblogspring.domain.Category;
import mohsen.coder.personalcmsblogspring.errors.NotFoundException;
import mohsen.coder.personalcmsblogspring.errors.UpdateItemException;
import org.springframework.http.ResponseEntity;

public interface UpdateCategoryUseCase {
    ResponseEntity<CategoryModel> updateCategory(Category category) throws NotFoundException, UpdateItemException;
}
