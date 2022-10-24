package mohsen.coder.personalcmsblogspring.application.service;

import mohsen.coder.personalcmsblogspring.adapter.web.model.CategoryModel;
import mohsen.coder.personalcmsblogspring.application.port.in.UpdateCategoryUseCase;
import mohsen.coder.personalcmsblogspring.application.port.out.GetCategoryPort;
import mohsen.coder.personalcmsblogspring.application.port.out.UpdateCategoryPort;
import mohsen.coder.personalcmsblogspring.domain.Category;
import mohsen.coder.personalcmsblogspring.errors.NotFoundException;
import mohsen.coder.personalcmsblogspring.errors.UpdateItemException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UpdateCategoryService implements UpdateCategoryUseCase {

    private final UpdateCategoryPort repo;
    private final GetCategoryPort getCategory;

    public UpdateCategoryService(UpdateCategoryPort repo, GetCategoryPort getCategory) {
        this.repo = repo;
        this.getCategory = getCategory;
    }

    @Override
    public ResponseEntity<CategoryModel> updateCategory(Category categoryArg) throws NotFoundException, UpdateItemException {
        var optionalCategory = getCategory.getCategoryByPublicId(categoryArg.getId());
        if (optionalCategory.isEmpty()){
            throw new NotFoundException("دسته بندی یافت نشد!");
        }

        var category = optionalCategory.get();
        
        if (categoryArg.getTitle() != null && !categoryArg.getTitle().isEmpty())
            category.setTitle(categoryArg.getTitle());
        if (categoryArg.getStatus() != category.getStatus())
            category.setStatus(categoryArg.getStatus());
        if (categoryArg.getParent() != null)
            category.setParent(categoryArg.getParent());
        if (categoryArg.getChildren() != null && categoryArg.getChildren().size() > 0)
            category.setChildren(categoryArg.getChildren());
        if (categoryArg.getCreateAt() != null)
            category.setCreateAt(categoryArg.getCreateAt());
        if (categoryArg.getUpdateAt() != null)
            category.setUpdateAt(categoryArg.getUpdateAt());
        
        var optionalUpdatedCategory = repo.updateCategory(category);

        if (optionalUpdatedCategory.isEmpty())
            throw new UpdateItemException("خطا در بروز رسانی دسته بندی!");

        var categoryResponse = new CategoryModel();
            categoryResponse.fromDomainModel(optionalUpdatedCategory.get());
            return new ResponseEntity<>(categoryResponse, new HttpHeaders(), HttpStatus.OK);
    }
}
