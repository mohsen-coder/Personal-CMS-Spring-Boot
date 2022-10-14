package mohsen.coder.personalcmsblogspring.adapter.web.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginRequestModel {
    @NotNull(message = "نام کاربری نمی تواند خالی باشد!")
    @Size(min = 3, message = "نام کاربری معتبر نمی باشد!")
    private String username;

    @NotNull(message = "password can't be null!")
    @Size(message = "password must be at least 8 characters long!")
    private String password;
}
