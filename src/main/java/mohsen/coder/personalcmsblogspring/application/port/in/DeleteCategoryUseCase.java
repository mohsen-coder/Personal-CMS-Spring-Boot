package mohsen.coder.personalcmsblogspring.application.port.in;

import mohsen.coder.personalcmsblogspring.errors.DeleteItemException;
import org.springframework.http.ResponseEntity;

public interface DeleteCategoryUseCase {
    ResponseEntity<Object> deleteCategory(String id) throws DeleteItemException;
}
