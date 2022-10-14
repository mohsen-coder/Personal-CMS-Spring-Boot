package mohsen.coder.personalcmsblogspring.application.port.out;


import mohsen.coder.personalcmsblogspring.domain.Account;

import java.util.Optional;

public interface UpdateAccountPort {
    Optional<Account> updateAccount(Account account);
}
