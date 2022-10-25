package mohsen.coder.personalcmsblogspring.application.port.in;

import mohsen.coder.personalcmsblogspring.adapter.web.model.AccountModel;
import mohsen.coder.personalcmsblogspring.domain.Account;
import mohsen.coder.personalcmsblogspring.errors.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface UpdateAccountUseCase {
    ResponseEntity<AccountModel> updateAccount(Account account) throws NotFoundException;
}
