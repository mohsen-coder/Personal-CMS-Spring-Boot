package mohsen.coder.personalcmsblogspring.application.port.in;

import mohsen.coder.personalcmsblogspring.domain.Account;
import mohsen.coder.personalcmsblogspring.errors.NotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface GetAccountUseCase {
    ResponseEntity<Account> getAccountById(String accountId) throws NotFoundException;

    ResponseEntity<Account> getAccountByUsername(String username) throws NotFoundException;

    ResponseEntity<Account> getAccountByEmail(String email) throws NotFoundException;

    ResponseEntity<Collection<Account>> getAccountsByRole(String role, int skip, int limit);
}
