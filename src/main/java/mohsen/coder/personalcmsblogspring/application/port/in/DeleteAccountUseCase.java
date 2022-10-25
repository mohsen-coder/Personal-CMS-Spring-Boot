package mohsen.coder.personalcmsblogspring.application.port.in;

import org.springframework.http.ResponseEntity;

public interface DeleteAccountUseCase {
    ResponseEntity<Object> deleteAccount(String accountId);
}
