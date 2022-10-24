package mohsen.coder.personalcmsblogspring.adapter.web;

import lombok.extern.slf4j.Slf4j;
import mohsen.coder.personalcmsblogspring.adapter.web.model.CategoryModel;
import mohsen.coder.personalcmsblogspring.application.port.in.CreateCategoryUseCase;
import mohsen.coder.personalcmsblogspring.errors.ConflictException;
import mohsen.coder.personalcmsblogspring.errors.InvalidFieldException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "/category", produces = "application/json")
public class CategoryController {

    private final CreateCategoryUseCase createCategoryUseCase;

    public CategoryController(CreateCategoryUseCase createCategoryUseCase) {
        this.createCategoryUseCase = createCategoryUseCase;
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

    @PostMapping(consumes = "application/json")
    public ResponseEntity<CategoryModel> createCategory(@Valid @RequestBody CategoryModel request,
            Errors errors)
            throws InvalidFieldException, IOException, ConflictException {

        if (errors.hasErrors()) {
            requestValidation(errors);
        }

        return createCategoryUseCase.createCategory(request.toDomainModel());
    }

}
