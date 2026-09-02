package password_manager.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import password_manager.dto.WebsiteRequest;
import password_manager.model.User;
import password_manager.model.Website;
import password_manager.service.WebsiteService;

@RestController
@RequestMapping("/websites")
public class WebsiteController {

    private final WebsiteService websiteService;

    public WebsiteController(WebsiteService websiteService) {
        this.websiteService = websiteService;
    }

    // GET /websites
    @GetMapping
    public List<Website> getAllWebsites( @AuthenticationPrincipal User user) {
        return websiteService.getAllWebsites();
    }

    // GET /websites/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Website> getWebsiteById(@PathVariable Long id) {
        return websiteService.getWebsiteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /websites/user/{userId}
    @GetMapping("/user/{userId}")
    public List<Website> getWebsitesByUserId(@PathVariable Long userId) {
        return websiteService.getWebsitesByUserId(userId);
    }

    // POST /websites
    @PostMapping
    public Website createWebsite(@Valid @RequestBody WebsiteRequest request) {
        return websiteService.createWebsite(request);
    }

    // PUT /websites/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Website> updateWebsite(
            @Valid @PathVariable Long id,
            @RequestBody Website website) {

        try {
            Website updatedWebsite =
                    websiteService.updateWebsite(id, website);

            return ResponseEntity.ok(updatedWebsite);

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /websites/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWebsite(@PathVariable Long id) {
        websiteService.deleteWebsite(id);

        return ResponseEntity.noContent().build();
    }
}