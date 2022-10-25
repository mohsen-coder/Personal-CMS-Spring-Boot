package mohsen.coder.personalcmsblogspring.domain;

import lombok.Data;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Data
public class Category {
    private String id;
    private String title;
    private CategoryStatus status;
    private Date createAt;
    private Date updateAt;
    private Category parent;
    private Set<Category> children;
}
