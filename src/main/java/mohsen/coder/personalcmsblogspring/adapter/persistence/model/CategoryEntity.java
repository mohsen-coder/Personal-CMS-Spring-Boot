package mohsen.coder.personalcmsblogspring.adapter.persistence.model;

import lombok.Data;
import mohsen.coder.personalcmsblogspring.domain.Category;
import mohsen.coder.personalcmsblogspring.domain.CategoryStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String publicId;

    @NotNull
    private String title;

    @NotNull
    private String status;

    @NotNull
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @NotNull
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity parent;

    @OneToMany(mappedBy = "parent")
    private Set<CategoryEntity> children;

    public Category toDomainModel() {
        Category category = new Category();
        category.setId(publicId);
        category.setTitle(title);
        if (parent != null)
            category.setParent(parent.toDomainModel());
        if (children != null && !children.isEmpty())
            category.setChildren(children.stream().map(CategoryEntity::toDomainModel).collect(Collectors.toSet()));
        category.setCreateAt(createAt);
        category.setUpdateAt(updateAt);

        switch (status) {
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
        if (category.getId() != null)
            this.publicId = category.getId();
        this.title = category.getTitle();
        this.status = category.getStatus().toString();
        this.createAt = category.getCreateAt() != null ? category.getCreateAt() : new Date();
        this.updateAt = category.getUpdateAt() != null ? category.getUpdateAt() : new Date();
        if (category.getParent() != null)
            this.parent.fromDomainModel(category.getParent());
        if (category.getChildren() != null && !category.getChildren().isEmpty())
            this.children = category.getChildren().stream().map(category1 -> {
                CategoryEntity categoryEntity = new CategoryEntity();
                categoryEntity.fromDomainModel(category1);
                return categoryEntity;
            }).collect(Collectors.toSet());
    }

}
