package ru.projects.calories.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.projects.calories.model.Role;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO
{
	private Long id;
	private String login;
	private String email;
	private String password;
	private boolean archive;
	private short sex;
	private Role role;
	private String lastname;
	private String name;
	private String middleName;
	private String phone;
}
