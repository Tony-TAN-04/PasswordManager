package password_manager.dto;

public record LoginResponse(
        String token,
        Integer userId,
        String username
) {

}
