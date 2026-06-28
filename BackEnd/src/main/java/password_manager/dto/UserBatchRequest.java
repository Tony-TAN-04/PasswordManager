package password_manager.dto;

import java.util.List;

import jakarta.validation.Valid;

public class UserBatchRequest {
    @Valid
    private List<UserRequest> users;

    public List<UserRequest> getUsers() {
        return users;
    }

    public void setUsers(List<UserRequest> users) {
        this.users = users;
    }
}