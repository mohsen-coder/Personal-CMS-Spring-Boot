package mohsen.coder.personalcmsblogspring.adapter.web.request;

import lombok.Data;
import mohsen.coder.personalcmsblogspring.domain.Account;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegisterRequestModel {
    @NotNull(message = "name can't be null!")
    @Size(message = "name must be at least 3 characters long!")
    private String name;
    @NotNull(message = "last name can't be null!")
    @Size(message = "last name must be at least 3 characters long!")
    private String lastName;
    @NotNull(message = "username can't be null!")
    @Size(message = "username must be at least 3 characters long!")
    private String username;
    @NotNull(message = "email can't be null!")
    @Email(message = "email is not valid!")
    private String email;
    @NotNull(message = "password can't be null!")
    @Size(message = "password must be at least 8 characters long!")
    private String password;
    @NotNull(message = "confirm password can't be null!")
    @Size(message = "confirm password must be at least 8 characters long!")
    private String confirmPassword;

    public Account mapToAccountDomainModel(){
        Account account = new Account();
        account.setName(name);
        account.setLastName(lastName);
        account.setUsername(username);
        account.setEmail(email);
        account.setPassword(password);
        return account;
    }

}
