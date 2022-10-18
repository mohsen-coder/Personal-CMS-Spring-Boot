package mohsen.coder.personalcmsblogspring.errors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InvalidFieldException extends Exception {
    private final ArrayList<String> globalFieldsErrors;
    private final List<Map<String, String>> fields;

    public InvalidFieldException(List<Map<String, String>> fields, ArrayList<String> globalFieldsErrors) {
        this.fields = fields;
        this.globalFieldsErrors = globalFieldsErrors;
    }

    public List<Map<String, String>> getFields() {
        return fields;
    }

    public ArrayList<String> getGlobalFieldsErrors() {
        return globalFieldsErrors;
    }
}
