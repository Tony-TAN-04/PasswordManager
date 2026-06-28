package password_manager.controller;

import password_manager.model.User;
import password_manager.repository.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dev")
@Profile("dev")
public class DevDataController {

    private final UserRepository repository;
    private final Faker faker = new Faker();

    public DevDataController(UserRepository repository) {
        this.repository = repository;
    }

    // Générer des users fake
    @PostMapping("/seed-users")
    public String seedUsers(@RequestParam(defaultValue = "10") int count) {

        for (int i = 0; i < count; i++) {
            repository.save(new User(
                    faker.name().username(),
                    faker.internet().password()
            ));
        }

        return count + " fake users created";
    }

    // Supprimer tous les users
    @DeleteMapping("/clear-users")
    public String clearUsers() {
        repository.deleteAll();
        return "All users deleted";
    }

    // Reset + reseed
    @PostMapping("/reset-users")
    public String resetUsers(@RequestParam(defaultValue = "10") int count) {

        repository.deleteAll();

        for (int i = 0; i < count; i++) {
            repository.save(new User(
                    faker.name().username(),
                    faker.internet().password()
            ));
        }

        return "Reset + " + count + " fake users created";
    }
}
