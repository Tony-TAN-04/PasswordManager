package password_manager.controller;

import com.github.javafaker.Company;
import com.github.javafaker.Faker;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import password_manager.model.User;
import password_manager.model.Website;
import password_manager.repository.UserRepository;
import password_manager.repository.WebsiteRepository;

@RestController
@RequestMapping("/dev")
@Profile("dev")
public class DevDataController {

    private final UserRepository userRepository;
    private final WebsiteRepository websiteRepository;

    private final Faker faker = new Faker();

    public DevDataController(
            UserRepository userRepository,
            WebsiteRepository websiteRepository) {

        this.userRepository = userRepository;
        this.websiteRepository = websiteRepository;
    }

    // Générer des users fake
    @PostMapping("/seed-users")
    public String seedUsers(
            @RequestParam(defaultValue = "10") int count) {

        for (int i = 0; i < count; i++) {

            User user = new User(
                    faker.name().username(),
                    faker.internet().password()
            );

            userRepository.save(user);
        }

        return count + " fake users created";
    }

    // Générer des websites fake
    @PostMapping("/seed-websites")
    public String seedWebsites(
            @RequestParam(defaultValue = "5") int count) {

        var users = userRepository.findAll();

        if (users.isEmpty()) {
            return "No users found. Create users first.";
        }

        for (int i = 0; i < count; i++) {

            User user = users.get(faker.random().nextInt(users.size()));
            Company company = faker.company();
            Website website = new Website(
                    company.name(),
                    company.url(),
                    faker.internet().password(),
                    user
            );

            websiteRepository.save(website);
        }

        return count + " fake websites created";
    }

    // Supprimer tous les users
    @DeleteMapping("/clear-users")
    public String clearUsers() {

        websiteRepository.deleteAll();
        userRepository.deleteAll();

        return "All users and websites deleted";
    }

    // Reset + reseed Users + Websites
    @PostMapping("/reset")
    public String reset(
            @RequestParam(defaultValue = "10") int userCount,
            @RequestParam(defaultValue = "5") int websiteCount) {

        // On supprime d'abord les websites
        // car ils possèdent la FK vers User
        websiteRepository.deleteAll();
        userRepository.deleteAll();

        for (int i = 0; i < userCount; i++) {

            User user = new User(
                    faker.name().username(),
                    faker.internet().password()
            );

            userRepository.save(user);

            // Créer plusieurs websites pour chaque user
            for (int j = 0; j < websiteCount; j++) {
                Company company = faker.company();
                Website website = new Website(
                        company.name(),
                        company.url(),
                        faker.internet().password(),
                        user
                );

                websiteRepository.save(website);
            }
        }

        return "Reset + "
                + userCount
                + " users + "
                + (userCount * websiteCount)
                + " websites created";
    }
}