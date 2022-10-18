package mohsen.coder.personalcmsblogspring.adapter.web;

import lombok.extern.slf4j.Slf4j;

import mohsen.coder.personalcmsblogspring.adapter.web.response.AccountResponseModel;
import mohsen.coder.personalcmsblogspring.adapter.web.request.RegisterRequestModel;
import mohsen.coder.personalcmsblogspring.adapter.web.request.UpdateAccountRequestModel;
import mohsen.coder.personalcmsblogspring.application.port.in.CreateAccountUseCase;
import mohsen.coder.personalcmsblogspring.application.port.in.DeleteAccountUseCase;
import mohsen.coder.personalcmsblogspring.application.port.in.GetAccountUseCase;
import mohsen.coder.personalcmsblogspring.application.port.in.UpdateAccountUseCase;
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
    private final UpdateAccountUseCase updateAccountUseCase;
    private final DeleteAccountUseCase deleteAccountUseCase;

    @Autowired
    public AccountController(CreateAccountUseCase createAccountUseCase, GetAccountUseCase getAccountUseCase, UpdateAccountUseCase updateAccountUseCase, DeleteAccountUseCase deleteAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
        this.getAccountUseCase = getAccountUseCase;
        this.updateAccountUseCase = updateAccountUseCase;
        this.deleteAccountUseCase = deleteAccountUseCase;
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

    private void requestValidation(Errors errors) throws InvalidFieldException {
        if (errors.hasErrors()) {
            List<Map<String, String>> errorFields = new ArrayList<>();
            ArrayList<String> globalErrors = new ArrayList<>();

            for (var error : errors.getGlobalErrors()){
                globalErrors.add(error.getDefaultMessage());
            }

            for (var error : errors.getFieldErrors()) {
                errorFields.add(Map.of(error.getField(), error.getDefaultMessage() != null ? error.getDefaultMessage() : ""));
            }

            throw new InvalidFieldException(errorFields, globalErrors);
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Account> registerUser(@Valid @RequestBody RegisterRequestModel requestModel, Errors errors) throws InvalidFieldException, ConflictException {
        if (errors.hasErrors()) {
            requestValidation(errors);
        }
        return createAccountUseCase.createAccount(requestModel.mapToAccountDomainModel());
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<AccountResponseModel> updateAccount(@Valid @RequestBody UpdateAccountRequestModel accountModel, Errors errors) throws InvalidFieldException, NotFoundException {
        if (errors.hasErrors()) {
            requestValidation(errors);
        }
        return updateAccountUseCase.updateAccount(accountModel.mapToAccountDomainModel());
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Object> deleteAccount(@PathVariable("accountId") String accountId){
        return deleteAccountUseCase.deleteAccount(accountId);
    }
}
