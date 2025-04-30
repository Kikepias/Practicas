public class UserDB {
    private static User[] users = new User[] {
        new User("antonio", "abc123.", "Antonio", "de Andres", User.ADMIN),
        new User("susana", "abc123.", "Susana", "Alvarez", User.ADMIN),
        new User("pedro", "abc123.", "Pedro", "Gomez", User.USER),
        new User("raquel", "abc123.", "Raquel", "Hazas", User.USER)
    };

    public static User findByName(String userName) {
        for (User user : users) {
            if (user.getUsername().equals(userName)) {
                return user;
            }
        }
        return null;
    }
}