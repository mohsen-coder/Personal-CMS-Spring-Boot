package mohsen.coder.personalcmsblogspring.application.service;

import mohsen.coder.personalcmsblogspring.adapter.web.response.AccountResponseModel;
import mohsen.coder.personalcmsblogspring.application.port.in.UpdateAccountUseCase;
import mohsen.coder.personalcmsblogspring.application.port.out.GetAccountPort;
import mohsen.coder.personalcmsblogspring.application.port.out.UpdateAccountPort;
import mohsen.coder.personalcmsblogspring.domain.Account;
import mohsen.coder.personalcmsblogspring.errors.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateAccountService implements UpdateAccountUseCase {
    private final UpdateAccountPort repo;
    private final GetAccountPort getAccountPort;

    public UpdateAccountService(UpdateAccountPort repo, GetAccountPort getAccountPort) {
        this.repo = repo;
        this.getAccountPort = getAccountPort;
    }

    @Override
    public ResponseEntity<AccountResponseModel> updateAccount(Account accountArg) throws NotFoundException {
        Optional<Account> optionalAccount = getAccountPort.getAccountById(accountArg.getId());
        if (optionalAccount.isEmpty()) {
            throw new NotFoundException("کاربر موجود نمی باشد!");
        }

        Account account = optionalAccount.get();
        if (accountArg.getName() != null) account.setName(accountArg.getName());
        if (accountArg.getLastName() != null) account.setLastName(accountArg.getLastName());
        if (accountArg.getUsername() != null) account.setUsername(accountArg.getUsername());
        if (accountArg.getEmail() != null) account.setEmail(accountArg.getEmail());
        if (accountArg.getRole() != null) account.setRole(accountArg.getRole());
        if (accountArg.getPassword() != null) account.setPassword(accountArg.getPassword());

        Optional<Account> updatedAccount = repo.updateAccount(account);

        AccountResponseModel accountRequestModel = new AccountResponseModel();
        accountRequestModel.mapFromDomainModel(updatedAccount.get());

        return new ResponseEntity<>(accountRequestModel, new HttpHeaders(), HttpStatus.OK);
    }
}
