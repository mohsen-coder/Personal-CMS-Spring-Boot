package mohsen.coder.personalcmsblogspring.application.port.out;
import mohsen.coder.personalcmsblogspring.domain.Account;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Optional;

public interface GetAccountPort {
    Optional<Account> getAccountById(String publicId);

    Optional<Account> getAccountByEmail(String email);

    Optional<Account> getAccountByUsername(String username);

    Collection<Account> getAllAccountsByPagination(Pageable pageable);

    Collection<Account> getAccountsByRole(String role, Pageable pageable);
}
