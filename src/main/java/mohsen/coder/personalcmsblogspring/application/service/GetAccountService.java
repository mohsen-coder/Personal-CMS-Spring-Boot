package mohsen.coder.personalcmsblogspring.application.service;

import mohsen.coder.personalcmsblogspring.adapter.web.model.AccountModel;
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
import java.util.stream.Collectors;

@Service
public class GetAccountService implements GetAccountUseCase {

    private final GetAccountPort repo;

    public GetAccountService(GetAccountPort repo) {
        this.repo = repo;
    }

    public ResponseEntity<AccountModel> getAccountById(String id) throws NotFoundException {
        Optional<Account> account = repo.getAccountById(id);
        if (account.isEmpty())
            throw new NotFoundException("کاربر یافت نشد!");
        var accountModel = new AccountModel();
        accountModel.fromDomainModel(account.get());
        return new ResponseEntity<>(accountModel, new HttpHeaders(), HttpStatus.OK);
    }

    public ResponseEntity<AccountModel> getAccountByUsername(String username) throws NotFoundException {
        Optional<Account> account = repo.getAccountByUsername(username);
        if (account.isEmpty())
            throw new NotFoundException("کاربر یافت نشد!");

        var accountModel = new AccountModel();
        accountModel.fromDomainModel(account.get());
        return new ResponseEntity<>(accountModel, new HttpHeaders(), HttpStatus.OK);
    }

    public ResponseEntity<AccountModel> getAccountByEmail(String email) throws NotFoundException {
        Optional<Account> account = repo.getAccountByEmail(email);
        if (account.isEmpty())
            throw new NotFoundException("کاربر یافت نشد!");

        var accountModel = new AccountModel();
        accountModel.fromDomainModel(account.get());
        return new ResponseEntity<>(accountModel, new HttpHeaders(), HttpStatus.OK);
    }

    public ResponseEntity<Collection<AccountModel>> getAccountsByRole(String role, int skip, int limit) {
        Pageable pageable = PageRequest.of(skip, limit);
        Collection<Account> accountsByRole = repo.getAccountsByRole(role, pageable);
        var mappedAccounts = accountsByRole.stream().map(accountArg -> {
            var accountModel = new AccountModel();
            accountModel.fromDomainModel(accountArg);
            return accountModel;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(mappedAccounts, new HttpHeaders(), HttpStatus.OK);
    }

    public ResponseEntity<Collection<AccountModel>> getAccountsByPagination(int skip, int limit) {
        Pageable pageable = PageRequest.of(skip, limit);
        Collection<Account> accounts = repo.getAllAccountsByPagination(pageable);
        var mappedAccounts = accounts.stream().map(accountArg -> {
            var accountModel = new AccountModel();
            accountModel.fromDomainModel(accountArg);
            return accountModel;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(mappedAccounts, new HttpHeaders(), HttpStatus.OK);
    }
}
