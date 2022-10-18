package mohsen.coder.personalcmsblogspring.adapter.web;

import lombok.extern.slf4j.Slf4j;

import mohsen.coder.personalcmsblogspring.adapter.web.request.RegisterRequestModel;
import mohsen.coder.personalcmsblogspring.application.port.in.CreateAccountUseCase;
import mohsen.coder.personalcmsblogspring.application.port.in.GetAccountUseCase;
import mohsen.coder.personalcmsblogspring.domain.Account;
import mohsen.coder.personalcmsblogspring.errors.ConflictException;
import mohsen.coder.personalcmsblogspring.errors.InvalidFieldException;
import mohsen.coder.personalcmsblogspring.errors.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping(path = "/account", produces = "application/json")
public class AccountController {

    private final CreateAccountUseCase createAccountUseCase;
    private final GetAccountUseCase getAccountUseCase;

    @Autowired
    public AccountController(CreateAccountUseCase createAccountUseCase, GetAccountUseCase getAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
        this.getAccountUseCase = getAccountUseCase;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Account> registerUser(@Valid @RequestBody RegisterRequestModel requestModel, Errors errors) throws InvalidFieldException, ConflictException {
        if (requestModel.getPassword() != null && requestModel.getConfirmPassword() != null && !requestModel.getPassword().equals(requestModel.getConfirmPassword()))
            errors.rejectValue("confirmPassword", "confirmPassword", "confirm password don't match!");

        if (errors.hasErrors()) {
            List<Map<String, String>> errorFields = new ArrayList<>();
            for (var error : errors.getFieldErrors()) {
                errorFields.add(Map.of(error.getField(), error.getDefaultMessage() != null ? error.getDefaultMessage() : ""));
            }
            log.info("throw InvalidFieldException");
            throw new InvalidFieldException(errorFields);
        }

        return createAccountUseCase.createAccount(requestModel.mapToAccountDomainModel());
    }

    @GetMapping(params = {"id"})
    public ResponseEntity<Account> getAccountById(@RequestParam String id) throws NotFoundException {
        return getAccountUseCase.getAccountById(id);
    }

    @GetMapping(params = {"username"})
    public ResponseEntity<Account> getAccountByUsername(@RequestParam String username) throws NotFoundException {
        return getAccountUseCase.getAccountByUsername(username);
    }

    @GetMapping(params = {"email"})
    public ResponseEntity<Account> getAccountByEmail(@RequestParam String email) throws NotFoundException {
        return getAccountUseCase.getAccountByEmail(email);
    }

    @GetMapping(params = {"role", "skip", "limit"})
    public ResponseEntity<Collection<Account>> getAccountsByRole(@RequestParam String role, @RequestParam Optional<Integer> skip, @RequestParam int limit) {
        return getAccountUseCase.getAccountsByRole(role, skip.orElse(0), limit);
    }

    @GetMapping(params = {"skip", "limit"})
    public ResponseEntity<Collection<Account>> getAllAccountsByPagination(@RequestParam Optional<Integer> skip, @RequestParam int limit) {
        return getAccountUseCase.getAccountsByPagination(skip.orElse(0), limit);
    }
}
