package mohsen.coder.personalcmsblogspring.application.port.in;

import mohsen.coder.personalcmsblogspring.adapter.web.model.AccountModel;
import mohsen.coder.personalcmsblogspring.errors.NotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface GetAccountUseCase {
    ResponseEntity<AccountModel> getAccountById(String accountId) throws NotFoundException;

    ResponseEntity<AccountModel> getAccountByUsername(String username) throws NotFoundException;

    ResponseEntity<AccountModel> getAccountByEmail(String email) throws NotFoundException;

    ResponseEntity<Collection<AccountModel>> getAccountsByRole(String role, int skip, int limit);

    ResponseEntity<Collection<AccountModel>> getAccountsByPagination(int skip, int limit);
}
