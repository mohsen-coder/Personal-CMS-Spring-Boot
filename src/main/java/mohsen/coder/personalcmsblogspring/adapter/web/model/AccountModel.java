package mohsen.coder.personalcmsblogspring.adapter.web.model;

import lombok.Data;
import mohsen.coder.personalcmsblogspring.domain.Account;
import mohsen.coder.personalcmsblogspring.domain.AccountRole;

@Data
public class AccountModel {
    private String id;
    private String name;
    private String lastName;
    private String username;
    private String email;
    private String role;

    public Account toDomainModel() {
        Account account = new Account();
        account.setId(this.id);
        account.setName(this.name);
        account.setLastName(this.lastName);
        account.setUsername(this.username);
        account.setEmail(this.email);

        switch (role) {
            case "user":
                account.setRole(AccountRole.user);
                break;
            case "writer":
                account.setRole(AccountRole.writer);
                break;
            case "admin":
                account.setRole(AccountRole.admin);
                break;
            default:
                account.setRole(AccountRole.none);
        }

        return account;
    }

    public void fromDomainModel(Account account) {
        this.id = account.getId();
        this.name = account.getName();
        this.lastName = account.getLastName();
        this.username = account.getUsername();
        this.email = account.getEmail();
        this.role = account.getRole().toString();
    }
}
