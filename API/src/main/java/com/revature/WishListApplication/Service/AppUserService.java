@Service
public class AppUserService {

    private final AppUserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser register(String username, String rawPassword) {
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword)); // important
        return repo.save(user);
    }
}
