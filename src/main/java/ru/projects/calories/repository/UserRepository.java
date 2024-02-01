package ru.projects.calories.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.projects.calories.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>
{
	Optional<User> findByName(String name);

	Optional<User> findByLogin(String login);
}
