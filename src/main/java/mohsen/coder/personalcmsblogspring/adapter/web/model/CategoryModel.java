package mohsen.coder.personalcmsblogspring.adapter.web.model;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import mohsen.coder.personalcmsblogspring.domain.Category;
import mohsen.coder.personalcmsblogspring.domain.CategoryStatus;

@Data
public class CategoryModel {
    private String id;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String status;

    private CategoryModel parent;

    private List<CategoryModel> children;

    public Category toDomainModel() {
        var category = new Category();

        if (this.id != null && !this.id.isEmpty())
            category.setId(this.id);
        category.setTitle(this.title);
        if (this.parent != null)
            category.setParent(this.parent.toDomainModel());
        if (this.children != null && !this.children.isEmpty())
            category.setChildren(this.children.stream().map(CategoryModel::toDomainModel).collect(Collectors.toSet()));

        switch (this.status) {
            case "suspend":
                category.setStatus(CategoryStatus.suspend);
                break;
            case "publish":
                category.setStatus(CategoryStatus.publish);
                break;
            default:
                category.setStatus(CategoryStatus.none);
        }

        return category;
    }

    public void fromDomainModel(Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
        this.status = category.getStatus().toString();
        if (category.getParent() != null)
            this.parent.fromDomainModel(category.getParent());
        if (category.getChildren() != null && !category.getChildren().isEmpty())
            this.children = category.getChildren().stream().map(categoryArg -> {
                var categoryRequestModel = new CategoryModel();
                categoryRequestModel.fromDomainModel(categoryArg);
                return categoryRequestModel;
            }).collect(Collectors.toList());
    }
}
