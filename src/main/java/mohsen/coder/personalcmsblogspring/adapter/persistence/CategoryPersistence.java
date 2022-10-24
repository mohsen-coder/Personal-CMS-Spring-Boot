package mohsen.coder.personalcmsblogspring.adapter.persistence;

import lombok.extern.slf4j.Slf4j;
import mohsen.coder.personalcmsblogspring.adapter.persistence.model.CategoryEntity;
import mohsen.coder.personalcmsblogspring.application.port.out.CreateCategoryPort;
import mohsen.coder.personalcmsblogspring.application.port.out.DeleteCategoryPort;
import mohsen.coder.personalcmsblogspring.application.port.out.GetCategoryPort;
import mohsen.coder.personalcmsblogspring.application.port.out.UpdateCategoryPort;
import mohsen.coder.personalcmsblogspring.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CategoryPersistence implements CreateCategoryPort, UpdateCategoryPort, GetCategoryPort, DeleteCategoryPort {

    private final CategoryRepo repo;

    @Autowired
    public CategoryPersistence(CategoryRepo repo) {
        this.repo = repo;
    }

    @Override
    public Optional<Category> createCategory(Category category) {
        category.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.fromDomainModel(category);
        try {
            repo.save(categoryEntity);
            return Optional.of(category);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteCategory(String publicId) {
        return repo.deleteCategoryEntityByPublicId(publicId);
    }

    @Override
    public Optional<Category> getCategoryByPublicId(String publicId) {
        Optional<CategoryEntity> optionalCategory = repo.findCategoryEntityByPublicId(publicId);
        return optionalCategory.map(CategoryEntity::toDomainModel);
    }

    @Override
    public Optional<Category> getCategoryByTitle(String title) {
        Optional<CategoryEntity> optionalCategory = repo.findCategoryEntityByTitle(title);
        return optionalCategory.map(CategoryEntity::toDomainModel);
    }

    @Override
    public Collection<Category> getCategoriesByPagination(String status, Pageable pageable) {
        Collection<CategoryEntity> allByStatus = repo.findAllByStatus(status, pageable);
        return allByStatus.stream().map(CategoryEntity::toDomainModel).collect(Collectors.toList());
    }

    @Override
    public Optional<Category> updateCategory(Category category) {
        Optional<CategoryEntity> optionalCategoryEntity = repo.findCategoryEntityByPublicId(category.getId());
        if (optionalCategoryEntity.isPresent()){
            var categoryEntity = optionalCategoryEntity.get();
            categoryEntity.fromDomainModel(category);
            try{
                repo.save(categoryEntity);
                return Optional.of(category);
            }catch (Exception e){
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}
