package digital.extreme.quarkus01.quarkussocial.domain.repository;

import digital.extreme.quarkus01.quarkussocial.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
}
