package mohsen.coder.personalcmsblogspring.application.port.in;

import mohsen.coder.personalcmsblogspring.adapter.web.model.CategoryModel;
import mohsen.coder.personalcmsblogspring.errors.NotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface GetCategoryUseCase {
    ResponseEntity<CategoryModel> getCategoryById(String id) throws NotFoundException;

    ResponseEntity<CategoryModel> getCategoryByTitle(String id) throws NotFoundException;

    ResponseEntity<Collection<CategoryModel>> getAllCategories(String status, int skip, int limit);
}
