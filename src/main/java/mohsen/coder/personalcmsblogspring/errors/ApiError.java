package mohsen.coder.personalcmsblogspring.errors;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class ApiError {
    private int status;
    private final List<String> messages = new ArrayList<>();

    public ApiError() {
    }

    public ApiError(HttpStatus status) {
        this.status = status.value();
    }

    public void addMessage(String message){
        messages.add(message);
    }

    public void addAllMessages(Collection<String> allMessages){
        messages.addAll(allMessages);
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
