package mohsen.coder.personalcmsblogspring.application.port.in;

import mohsen.coder.personalcmsblogspring.adapter.web.model.AccountModel;
import mohsen.coder.personalcmsblogspring.domain.Account;
import mohsen.coder.personalcmsblogspring.errors.ConflictException;
import org.springframework.http.ResponseEntity;

public interface CreateAccountUseCase {
    ResponseEntity<AccountModel> createAccount(Account account) throws ConflictException;
}
