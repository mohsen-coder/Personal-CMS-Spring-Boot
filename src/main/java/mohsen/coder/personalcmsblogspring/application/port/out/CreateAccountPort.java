package mohsen.coder.personalcmsblogspring.application.port.out;

import mohsen.coder.personalcmsblogspring.domain.Account;

import java.util.Optional;

public interface CreateAccountPort {
    Optional<Account> createAccount(Account account);
}
