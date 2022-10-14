package mohsen.coder.personalcmsblogspring.adapter.persistence;

import lombok.extern.slf4j.Slf4j;
import mohsen.coder.personalcmsblogspring.adapter.persistence.model.AccountEntity;
import mohsen.coder.personalcmsblogspring.application.port.out.CreateAccountPort;
import mohsen.coder.personalcmsblogspring.application.port.out.GetAccountPort;
import mohsen.coder.personalcmsblogspring.application.port.out.UpdateAccountPort;
import mohsen.coder.personalcmsblogspring.domain.Account;
import mohsen.coder.personalcmsblogspring.domain.AccountRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AccountPersistence implements CreateAccountPort, GetAccountPort, UpdateAccountPort {

    private final AccountRepo repo;

    @Autowired
    public AccountPersistence(AccountRepo repo) {
        this.repo = repo;
    }

    @Override
    public Optional<Account> createAccount(Account account) {
        account.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        account.setRole(AccountRole.user);
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.fromDomainModel(account);
        try {
            repo.save(accountEntity);
            return Optional.of(account);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Account> getAccountById(String publicId) {
        var accountEntityOptional = repo.findAccountEntityByPublicId(publicId);
        return accountEntityOptional.map(AccountEntity::toDomainModel);
    }

    @Override
    public Optional<Account> getAccountByEmail(String email) {
        Optional<AccountEntity> accountByUsername = repo.findAccountEntityByEmail(email);
        return accountByUsername.map(AccountEntity::toDomainModel);
    }

    @Override
    public Optional<Account> getAccountByUsername(String username) {
        Optional<AccountEntity> accountByUsername = repo.findAccountEntityByUsername(username);
        return accountByUsername.map(AccountEntity::toDomainModel);
    }

    @Override
    public Collection<Account> getAllAccountsByPagination(Pageable pageable) {
        return repo.findAll(pageable).map(AccountEntity::toDomainModel).stream().collect(Collectors.toList());
    }

    @Override
    public Collection<Account> getAccountsByRole(String role, Pageable pageable) {
        List<AccountEntity> allByRole = repo.findAllByRole(role, pageable);
        return allByRole.stream().map(AccountEntity::toDomainModel).collect(Collectors.toList());
    }

    @Override
    public Optional<Account> updateAccount(Account account) {
        var accountEntityOptional = repo.findAccountEntityByPublicId(account.getId());
        if (accountEntityOptional.isPresent()){
            var accountEntity = accountEntityOptional.get();
            accountEntity.fromDomainModel(account);
            try{
                repo.save(accountEntity);
                return Optional.of(account);
            }catch (Exception e){
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}
