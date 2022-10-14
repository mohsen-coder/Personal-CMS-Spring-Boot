package mohsen.coder.personalcmsblogspring.adapter.persistence.model;

import lombok.Data;
import mohsen.coder.personalcmsblogspring.domain.Account;
import mohsen.coder.personalcmsblogspring.domain.AccountRole;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class AccountEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull
    private String publicId;

    @NotNull
    private String name;

    @NotNull
    private String lastName;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    private String role;

    @NotNull
    private String password;

    public void fromDomainModel(Account account) {
        if (account.getId() != null) this.publicId = account.getId();
        this.name = account.getName();
        this.lastName = account.getLastName();
        this.username = account.getUsername();
        this.email = account.getEmail();
        if (account.getPassword() != null) this.password = account.getPassword();

        switch (account.getRole()){
            case user:
                this.role = "user";
                break;
            case admin:
                this.role = "admin";
                break;
            case writer:
                this.role = "writer";
                break;
            default:
                this.role = "none";
        }
    }

    public Account toDomainModel(){
        var account = new Account();
        account.setId(publicId);
        account.setName(name);
        account.setLastName(lastName);
        account.setEmail(email);
        account.setUsername(username);

        switch (role){
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
}
