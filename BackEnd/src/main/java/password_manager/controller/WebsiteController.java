package password_manager.controller;

import password_manager.dto.WebsiteRequest;
import password_manager.model.Website;
import password_manager.service.WebsiteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/websites")
public class WebsiteController {

    private final WebsiteService websiteService;

    public WebsiteController(WebsiteService websiteService) {
        this.websiteService = websiteService;
    }

    // GET /websites
    @GetMapping
    public List<Website> getAllWebsites() {
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