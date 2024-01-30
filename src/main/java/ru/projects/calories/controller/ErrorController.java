package ru.projects.calories.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@RequestMapping("/error")
public class ErrorController
{
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String notFoundHandler(Exception e, Model model)
	{
		model.addAttribute("error", e.getMessage());

		return "errors/error-404";
	}

	@ExceptionHandler(InternalServerErrorException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String internalServerError(Exception e, Model model)
	{
		model.addAttribute("error", e.getMessage());

		return "errors/error-500";
	}
}

class NotFoundException extends Exception
{
}

class InternalServerErrorException extends Exception
{
}
