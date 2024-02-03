package ru.projects.calories.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.projects.calories.dto.UserDTO;
import ru.projects.calories.model.Role;
import ru.projects.calories.model.User;
import ru.projects.calories.repository.UserRepository;

import java.util.ArrayList;
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

	public boolean save(UserDTO dto)
	{
		User user = User.builder()
				.login(dto.getLogin())
				.archive(false)
				.email(dto.getEmail())
				.phone(dto.getPhone())
				.sex(dto.getSex())
				.role(Role.USER)
				.lastname(dto.getLastname())
				.middleName(dto.getMiddleName())
				.name(dto.getName())
				.password(dto.getPassword())
				.height(dto.getHeight())
				.weight(dto.getWeight())
				.build();

		this.userRepo.save(user);
		return true;
	}

	public List<UserDTO> getAll()
	{
		return userRepo
				.findAll()
				.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}

	private UserDTO toDTO(User user)
	{
		return UserDTO.builder()
					  .id(user.getId())
					  .login(user.getLogin())
					  .archive(user.isArchive())
					  .email(user.getEmail())
					  .phone(user.getPhone())
					  .sex(user.getSex())
					  .role(user.getRole())
					  .lastname(user.getLastname())
					  .middleName(user.getMiddleName())
					  .name(user.getName())
					  .weight(user.getWeight())
					  .height(user.getHeight())
					  .build();
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

	public User findById(Long id)
	{
		return this.userRepo.findById(id).orElse(null);
	}

	public void promoteUserByUsername(String username)
	{
		Optional<User> user = userRepo.findUserByName(username);

		user.ifPresent(u ->
		{
			if (u.getRole().equals(Role.USER))
			{
				u.setRole(Role.MANAGER);
			}
			else if (u.getRole().equals(Role.MANAGER))
			{
				u.setRole(Role.ADMIN);
			}

			userRepo.save(u);
		});
	}

	public void promoteUserByLogin(String login)
	{
		Optional<User> user = userRepo.findByLogin(login);

		user.ifPresent(u ->
		{
			if (u.getRole().equals(Role.USER))
			{
				u.setRole(Role.MANAGER);
			}
			else if (u.getRole().equals(Role.MANAGER))
			{
				u.setRole(Role.ADMIN);
			}

			userRepo.save(u);
		});
	}

	public void promoteUserById(Long id)
	{
		Optional<User> user = userRepo.findById(id);

		user.ifPresent(u ->
		{
			if (u.getRole().equals(Role.USER))
			{
				u.setRole(Role.MANAGER);
			}
			else if (u.getRole().equals(Role.MANAGER))
			{
				u.setRole(Role.ADMIN);
			}

			userRepo.save(u);
		});
	}

	public void demoteUserByUsername(String username)
	{
		Optional<User> user = userRepo.findUserByName(username);

		user.ifPresent(u ->
		{
			if (u.getRole().equals(Role.ADMIN))
			{
				u.setRole(Role.MANAGER);
			}
			else if (u.getRole().equals(Role.MANAGER))
			{
				u.setRole(Role.USER);
			}

			userRepo.save(u);
		});
	}

	public void demoteUserByLogin(String login)
	{
		Optional<User> user = userRepo.findByLogin(login);

		user.ifPresent(u ->
		{
			if (u.getRole().equals(Role.ADMIN))
			{
				u.setRole(Role.MANAGER);
			}
			else if (u.getRole().equals(Role.MANAGER))
			{
				u.setRole(Role.USER);
			}

			userRepo.save(u);
		});
	}

	public void demoteUserById(Long id)
	{
		Optional<User> user = userRepo.findById(id);

		user.ifPresent(u ->
		{
			if (u.getRole().equals(Role.ADMIN))
			{
				u.setRole(Role.MANAGER);
			}
			else if (u.getRole().equals(Role.MANAGER))
			{
				u.setRole(Role.USER);
			}

			userRepo.save(u);
		});
	}

	public void deleteUserByUsername(String username)
	{
		Optional<User> user = userRepo.findUserByName(username);

		user.ifPresent(u ->
		{
			userRepo.deleteById(u.getId());
		});
	}

	public void deleteUserByLogin(String login)
	{
		Optional<User> user = userRepo.findByLogin(login);

		user.ifPresent(u ->
		{
			userRepo.deleteById(u.getId());
		});
	}

	public void deleteUserById(Long id)
	{
		Optional<User> user = userRepo.findById(id);

		user.ifPresent(u ->
		{
			userRepo.deleteById(u.getId());
		});
	}

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException
	{
		User user = this.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь '%s' не найден", login)));

		List<Role> roles = new ArrayList<>();
		roles.add(user.getRole());

		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), roles.stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(
				Collectors.toList()));
	}

	public boolean saveEdit(UserDTO userDTO, Long id)
	{
		Optional<User> u = userRepo.findById(id);

		u.ifPresent(user ->
		{
			user.setLogin(userDTO.getLogin());
			user.setEmail(userDTO.getEmail());

			userRepo.save(user);
		});
		return true;
	}
}
