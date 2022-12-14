package mohsen.coder.personalcmsblogspring.adapter.web;

import lombok.extern.slf4j.Slf4j;
import mohsen.coder.personalcmsblogspring.adapter.web.model.CategoryModel;
import mohsen.coder.personalcmsblogspring.application.port.in.CreateCategoryUseCase;
import mohsen.coder.personalcmsblogspring.application.port.in.DeleteCategoryUseCase;
import mohsen.coder.personalcmsblogspring.application.port.in.GetCategoryUseCase;
import mohsen.coder.personalcmsblogspring.application.port.in.UpdateCategoryUseCase;
import mohsen.coder.personalcmsblogspring.errors.ConflictException;
import mohsen.coder.personalcmsblogspring.errors.DeleteItemException;
import mohsen.coder.personalcmsblogspring.errors.InvalidFieldException;
import mohsen.coder.personalcmsblogspring.errors.NotFoundException;
import mohsen.coder.personalcmsblogspring.errors.UpdateItemException;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "/category", produces = "application/json")
public class CategoryController {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetCategoryUseCase getCategoryUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;

    public CategoryController(CreateCategoryUseCase createCategoryUseCase, GetCategoryUseCase getCategoryUseCase,
            UpdateCategoryUseCase updateCategoryUseCase, DeleteCategoryUseCase deleteCategoryUseCase) {
        this.createCategoryUseCase = createCategoryUseCase;
        this.getCategoryUseCase = getCategoryUseCase;
        this.updateCategoryUseCase = updateCategoryUseCase;
        this.deleteCategoryUseCase = deleteCategoryUseCase;
    }

    private void requestValidation(Errors errors) throws InvalidFieldException {
        if (errors.hasErrors()) {
            List<Map<String, String>> errorFields = new ArrayList<>();
            ArrayList<String> globalErrors = new ArrayList<>();

            for (var error : errors.getGlobalErrors()) {
                globalErrors.add(error.getDefaultMessage());
            }

            for (var error : errors.getFieldErrors()) {
                errorFields.add(
                        Map.of(error.getField(), error.getDefaultMessage() != null ? error.getDefaultMessage() : ""));
            }

            throw new InvalidFieldException(errorFields, globalErrors);
        }
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryModel> getCategoryById(@PathVariable("categoryId") String categoryId)
            throws NotFoundException {
        return getCategoryUseCase.getCategoryById(categoryId);
    }

    @GetMapping(params = { "title" })
    public ResponseEntity<CategoryModel> getCategoryByTitle(@RequestParam String title) throws NotFoundException {
        return getCategoryUseCase.getCategoryByTitle(title);
    }

    @GetMapping(params = { "status", "skip", "limit" })
    public ResponseEntity<Collection<CategoryModel>> getCategoryByStatusAndPagination(@RequestParam String status,
            @RequestParam int skip, @RequestParam int limit) {
        return getCategoryUseCase.getAllCategories(status, skip, limit);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<CategoryModel> createCategory(@Valid @RequestBody CategoryModel request,
            Errors errors)
            throws InvalidFieldException, ConflictException {

        if (errors.hasErrors()) {
            requestValidation(errors);
        }

        return createCategoryUseCase.createCategory(request.toDomainModel());
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<CategoryModel> updateCategory(@Valid @RequestBody CategoryModel request, Errors errors)
            throws NotFoundException, UpdateItemException, InvalidFieldException {
        if (request.getId() == null || request.getId().isEmpty()) {
            errors.rejectValue("id", "id", "id is required");
        }

        if (errors.hasErrors()) {
            requestValidation(errors);
        }

        return updateCategoryUseCase.updateCategory(request.toDomainModel());
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable("categoryId") String categoryId) throws DeleteItemException {
        return deleteCategoryUseCase.deleteCategory(categoryId);
    }

}
