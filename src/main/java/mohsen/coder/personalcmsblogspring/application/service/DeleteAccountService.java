package mohsen.coder.personalcmsblogspring.application.service;

import mohsen.coder.personalcmsblogspring.application.port.in.DeleteAccountUseCase;
import mohsen.coder.personalcmsblogspring.application.port.out.DeleteAccountPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DeleteAccountService implements DeleteAccountUseCase {

    private final DeleteAccountPort repo;

    public DeleteAccountService(DeleteAccountPort repo) {
        this.repo = repo;
    }

    @Override
    public ResponseEntity<Object> deleteAccount(String accountId) {
        if (repo.deleteAccount(accountId)){
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
