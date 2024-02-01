package ru.projects.calories.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.projects.calories.model.User;
import ru.projects.calories.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService
{
	private final UserRepository userRepo;

	@Autowired
	public UserService(UserRepository userRepo)
	{
		this.userRepo = userRepo;
	}

	public List<User> getAll()
	{
		return this.userRepo.findAll();
	}

	public User save(User user)
	{
		User result = this.userRepo.save(user);

		return result;
	}

	public User getUserById(Long id)
	{
		Optional<User> user = this.userRepo.findById(id);

		return user.orElse(null);
	}

	public Optional<User> findByUsername(String username)
	{
		return this.userRepo.findByName(username);
	}

	public Optional<User> findByLogin(String login)
	{
		return this.userRepo.findByLogin(login);
	}

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException
	{
		User user = this.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь '%s' не найден", login)));

		List<ru.projects.calories.model.Role> roles = new java.util.ArrayList<>();
		roles.add(user.getRole());

		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), roles.stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(
				Collectors.toList()));
	}
}
