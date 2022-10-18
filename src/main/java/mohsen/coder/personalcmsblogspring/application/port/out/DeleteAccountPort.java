package mohsen.coder.personalcmsblogspring.application.port.out;

import mohsen.coder.personalcmsblogspring.domain.Account;

public interface DeleteAccountPort {
    boolean deleteAccount(String accountPublicId);
}
