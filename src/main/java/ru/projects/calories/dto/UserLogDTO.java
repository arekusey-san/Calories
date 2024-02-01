package ru.projects.calories.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.projects.calories.model.Action;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLogDTO
{
	private Long id;

	private String lonin;
	private String timestamp;
	private Action action;
	private String reason;
}
