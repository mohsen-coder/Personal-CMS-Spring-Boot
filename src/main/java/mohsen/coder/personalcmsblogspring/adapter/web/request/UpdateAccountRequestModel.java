package mohsen.coder.personalcmsblogspring.adapter.web.request;

import lombok.Data;
import mohsen.coder.personalcmsblogspring.domain.Account;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@FieldsValueMatch.List({
        @FieldsValueMatch(
                message = "confirm password is wrong!",
                field = "password",
                fieldMatch = "confirmPassword"
        )
})
public class UpdateAccountRequestModel{
    @NotNull(message = "آیدی نمی تواند خالی باشد!")
    @NotEmpty(message = "آیدی نمی تواند خالی باشد!")
    private String id;
    @Size(message = "name must be at least 3 characters long!")
    private String name;
    @Size(message = "last name must be at least 3 characters long!")
    private String lastName;
    @Size(message = "username must be at least 3 characters long!")
    private String username;

    private String email;
    @Size(message = "password must be at least 8 characters long!")
    private String password;
    @Size(message = "confirm password must be at least 8 characters long!")
    private String confirmPassword;

    public Account mapToAccountDomainModel(){
        Account account = new Account();
        account.setId(id);
        account.setName(name);
        account.setLastName(lastName);
        account.setUsername(username);
        account.setEmail(email);
        account.setPassword(password);
        return account;
    }
}
