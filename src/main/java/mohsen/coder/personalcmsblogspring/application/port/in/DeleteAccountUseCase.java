package mohsen.coder.personalcmsblogspring.application.port.in;

import mohsen.coder.personalcmsblogspring.domain.Account;
import org.springframework.http.ResponseEntity;

public interface DeleteAccountUseCase {
    ResponseEntity<Object> deleteAccount(String accountId);
}
