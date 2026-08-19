package password_manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class WebsiteRequest {

    @NotBlank(message = "name is required")
    private String name;
    
    @NotBlank(message = "url is required")
    private String url;

    @NotBlank(message = "password is required")
    @Size(min = 4, message = "password too short")
    private String password;

    @NotNull(message = "userId is required")
    @Positive(message = "userId must be greater than 0")
    private Integer userId;

    public WebsiteRequest() {
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getPassword() {
        return password;
    }
    
    public Integer getUserId() {
        return userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}