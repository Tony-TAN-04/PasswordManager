package password_manager.service;
 
import password_manager.dto.WebsiteRequest;
import password_manager.model.User;
import password_manager.model.Website;
import password_manager.repository.UserRepository;
import password_manager.repository.WebsiteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WebsiteService {

    private final WebsiteRepository websiteRepository;
    private final UserRepository userRepository;

    public WebsiteService(
            WebsiteRepository websiteRepository,
            UserRepository userRepository
    ) {
        this.websiteRepository = websiteRepository;
        this.userRepository = userRepository;
    }

    public List<Website> getAllWebsites() {
        return websiteRepository.findAll();
    }

    public Optional<Website> getWebsiteById(Long id) {
        return websiteRepository.findById(id);
    }

    public List<Website> getWebsitesByUserId(Long userId) {
        return websiteRepository.findByUserId(userId);
    }

    public Website createWebsite(WebsiteRequest request) {

        User user = userRepository.findById(request.getUserId().longValue())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Website website = new Website(
                request.getName(),
                request.getUrl(),
                request.getPassword(),
                user
        );

        return websiteRepository.save(website);
    }

    public Website updateWebsite(Long id, Website website) {
        return websiteRepository.findById(id)
                .map(existingWebsite -> {
                    existingWebsite.setName(website.getName());
                    existingWebsite.setUrl(website.getUrl());
                    existingWebsite.setPassword(website.getPassword());
                    return websiteRepository.save(existingWebsite);
                })
                .orElseThrow(() -> new RuntimeException("Website not found"));
    }

    public void deleteWebsite(Long id) {
        websiteRepository.deleteById(id);
    }
}