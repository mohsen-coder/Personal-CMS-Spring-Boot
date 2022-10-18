package mohsen.coder.personalcmsblogspring.application.port.in;

import mohsen.coder.personalcmsblogspring.adapter.web.response.AccountResponseModel;
import mohsen.coder.personalcmsblogspring.domain.Account;
import mohsen.coder.personalcmsblogspring.errors.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface UpdateAccountUseCase {
    ResponseEntity<AccountResponseModel> updateAccount(Account account) throws NotFoundException;
}
