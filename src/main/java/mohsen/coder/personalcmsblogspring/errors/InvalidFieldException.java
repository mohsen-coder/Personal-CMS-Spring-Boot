package mohsen.coder.personalcmsblogspring.errors;

import java.util.List;
import java.util.Map;

public class InvalidFieldException extends Exception {
    private final List<Map<String, String>> fields;

    public InvalidFieldException(List<Map<String, String>> fields) {
        this.fields = fields;
    }

    public List<Map<String, String>> getFields() {
        return fields;
    }
}
