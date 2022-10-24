package mohsen.coder.personalcmsblogspring.application.service;

import mohsen.coder.personalcmsblogspring.adapter.web.model.CategoryModel;
import mohsen.coder.personalcmsblogspring.application.port.in.GetCategoryUseCase;
import mohsen.coder.personalcmsblogspring.application.port.out.GetCategoryPort;
import mohsen.coder.personalcmsblogspring.errors.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class GetCategoryService implements GetCategoryUseCase {

    private final GetCategoryPort repo;

    public GetCategoryService(GetCategoryPort repo) {
        this.repo = repo;
    }

    @Override
    public ResponseEntity<CategoryModel> getCategoryById(String id) throws NotFoundException {
        var optionalCategory = repo.getCategoryByPublicId(id);
        if (optionalCategory.isEmpty())
            throw new NotFoundException("دسته بندی یافت نشد!");
        var categoryResponseModel = new CategoryModel();
        categoryResponseModel.fromDomainModel(optionalCategory.get());
        return new ResponseEntity<>(categoryResponseModel, new HttpHeaders(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoryModel> getCategoryByTitle(String title) throws NotFoundException {
        var optionalCategory = repo.getCategoryByTitle(title);
        if (optionalCategory.isEmpty())
            throw new NotFoundException("دسته بندی یافت نشد!");
        var categoryResponseModel = new CategoryModel();
        categoryResponseModel.fromDomainModel(optionalCategory.get());
        return new ResponseEntity<>(categoryResponseModel, new HttpHeaders(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Collection<CategoryModel>> getAllCategories(String status, int skip, int limit) {
        Pageable pageable = PageRequest.of(skip, limit);
        var categories = repo.getCategoriesByPagination(status, pageable);
        var responseModelCategories = categories.stream().map(category -> {
            var categoryResponseModel = new CategoryModel();
            categoryResponseModel.fromDomainModel(category);
            return categoryResponseModel;
        }).collect(Collectors.toSet());

        return new ResponseEntity<>(responseModelCategories, new HttpHeaders(), HttpStatus.OK);
    }
}
