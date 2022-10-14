package mohsen.coder.personalcmsblogspring.application.service;

import lombok.extern.slf4j.Slf4j;
import mohsen.coder.personalcmsblogspring.application.port.in.GetAccountUseCase;
import mohsen.coder.personalcmsblogspring.application.port.out.GetAccountPort;
import mohsen.coder.personalcmsblogspring.domain.Account;
import mohsen.coder.personalcmsblogspring.errors.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
public class GetAccountService implements GetAccountUseCase {

    private final GetAccountPort repo;

    public GetAccountService(GetAccountPort repo) {
        this.repo = repo;
    }

    public ResponseEntity<Account> getAccountById(String id) throws NotFoundException {
        Optional<Account> account = repo.getAccountById(id);
        if (account.isEmpty())
            throw new NotFoundException("کاربر یافت نشد!");

        return new ResponseEntity<>(account.get(), HttpStatus.OK);
    }

    public ResponseEntity<Account> getAccountByUsername(String username) throws NotFoundException {
        Optional<Account> account = repo.getAccountByUsername(username);
        if (account.isEmpty())
            throw new NotFoundException("کاربر یافت نشد!");

        return new ResponseEntity<>(account.get(), HttpStatus.OK);
    }

    public ResponseEntity<Account> getAccountByEmail(String email) throws NotFoundException {
        Optional<Account> account = repo.getAccountByEmail(email);
        if (account.isEmpty())
            throw new NotFoundException("کاربر یافت نشد!");

        return new ResponseEntity<>(account.get(), HttpStatus.OK);
    }

    public ResponseEntity<Collection<Account>> getAccountsByRole(String role, int skip, int limit){
        Pageable pageable = PageRequest.of(skip, limit);
        Collection<Account> accountsByRole = repo.getAccountsByRole(role, pageable);
        return new ResponseEntity<>(accountsByRole, new HttpHeaders(), HttpStatus.OK);
    }

    public ResponseEntity<Collection<Account>> getAccountsByPagination(int skip, int limit){
        Pageable pageable = PageRequest.of(skip, limit);
        Collection<Account> accounts = repo.getAllAccountsByPagination(pageable);
        return new ResponseEntity<>(accounts, new HttpHeaders(), HttpStatus.OK);
    }
}
