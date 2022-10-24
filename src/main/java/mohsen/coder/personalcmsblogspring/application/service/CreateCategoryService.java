package mohsen.coder.personalcmsblogspring.application.service;

import mohsen.coder.personalcmsblogspring.adapter.web.model.CategoryModel;
import mohsen.coder.personalcmsblogspring.application.port.in.CreateCategoryUseCase;
import mohsen.coder.personalcmsblogspring.application.port.out.CreateCategoryPort;
import mohsen.coder.personalcmsblogspring.domain.Category;
import mohsen.coder.personalcmsblogspring.errors.ConflictException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateCategoryService implements CreateCategoryUseCase {

    private final CreateCategoryPort repo;

    public CreateCategoryService(CreateCategoryPort repo) {
        this.repo = repo;
    }

    @Override
    public ResponseEntity<CategoryModel> createCategory(Category category) throws ConflictException {
        category.setId(null);
        var optionalCategory = repo.createCategory(category);
        if (optionalCategory.isEmpty()) {
            throw new ConflictException("خطا در ثبت دسته بندی!");
        }
        var categoryResponseModel = new CategoryModel();
        categoryResponseModel.fromDomainModel(optionalCategory.get());
        return new ResponseEntity<>(categoryResponseModel, new HttpHeaders(), HttpStatus.CREATED);
    }
}
