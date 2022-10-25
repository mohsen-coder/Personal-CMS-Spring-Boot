package mohsen.coder.personalcmsblogspring.application.service;

import mohsen.coder.personalcmsblogspring.adapter.web.model.AccountModel;
import mohsen.coder.personalcmsblogspring.application.port.in.CreateAccountUseCase;
import mohsen.coder.personalcmsblogspring.application.port.out.CreateAccountPort;
import mohsen.coder.personalcmsblogspring.domain.Account;
import mohsen.coder.personalcmsblogspring.errors.ConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateAccountService implements CreateAccountUseCase {

    private final CreateAccountPort repo;

    @Autowired
    public CreateAccountService(CreateAccountPort repo) {
        this.repo = repo;
    }

    @Override
    public ResponseEntity<AccountModel> createAccount(Account account) throws ConflictException {
        var createdAccount = repo.createAccount(account);
        if (createdAccount.isEmpty())
            throw new ConflictException("کاربر موجود می باشد!");

        var accountModel = new AccountModel();
        accountModel.fromDomainModel(createdAccount.get());

        return new ResponseEntity<>(accountModel, new HttpHeaders(), HttpStatus.CREATED);
    }

}
