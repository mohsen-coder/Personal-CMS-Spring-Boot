package mohsen.coder.personalcmsblogspring.domain;

import lombok.Data;

@Data
public class Account {
    private String id;
    private String name;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private AccountRole role;
}
