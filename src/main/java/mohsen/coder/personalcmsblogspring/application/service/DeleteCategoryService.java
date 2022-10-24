package mohsen.coder.personalcmsblogspring.application.service;

import mohsen.coder.personalcmsblogspring.application.port.in.DeleteCategoryUseCase;
import mohsen.coder.personalcmsblogspring.application.port.out.DeleteCategoryPort;
import mohsen.coder.personalcmsblogspring.errors.DeleteItemException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DeleteCategoryService implements DeleteCategoryUseCase {

    private final DeleteCategoryPort repo;

    public DeleteCategoryService(DeleteCategoryPort repo) {
        this.repo = repo;
    }

    @Override
    public ResponseEntity<Object> deleteCategory(String id) throws DeleteItemException {
        if (!repo.deleteCategory(id)){
            throw new DeleteItemException("خطا در حذف دسته بندی!");
        }
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }
}
