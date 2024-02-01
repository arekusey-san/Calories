package ru.projects.calories.service;

import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.Authenticator;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

@Service
public class BackupService
{
	@Value("${backup.directory}")
	private String backupDirectory;
	@Value("${zip.path}")
	private String zipProgramPath;

	/**
	 * Настройка электронной почты
	 */
	@Value("${mail.from}")
	public String fromMail;
	@Value("${mail.password}")
	public String passwordMail;
	@Value("${mail.to}")
	public String toMail;
	@Value("${mail.debug}")
	public boolean mailDebug;

	/**
	 * Настройка электронной почты ЯндексПочты
	 */
	@Value("${mail.smtp.host}")
	public String smtpHost;
	@Value("${mail.smtp.port}")
	public String smtpPort;
	@Value("${mail.smtp.auth}")
	public String smtpAuth;
	@Value("${mail.smtp.starttls.enable}")
	public String startTlsEnable;

	/**
	 * postgreSQL
	 */
	@Value("${pgDump.path}")
	public String pgDumpPath;
	@Value("${pgDump.database}")
	public String pgDumpDB;
	@Value("${pgDump.username}")
	public String pgUsername;
	@Value("${pgDump.host}")
	public String pgHost;
	@Value("${pgDump.port}")
	public String pgPort;

	public void createBackupAndSendEmail()
	{
		String pgPath = this.pgDumpPath;
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String date = dataFormat.format(new Date());

		File backupDir = new File(this.backupDirectory);
		if (!backupDir.exists() && backupDir.mkdir())
		{
			System.err.println("Не удалось создать папку: " + this.backupDirectory);
			return;
		}

		String backupFileName = "backup_" + date + ".sql";
		String[] command = {
				pgDumpPath,
				"-U", this.pgUsername,
				"-d", this.pgDumpDB,
				"-h", this.pgHost,
				"-p", this.pgPort,
				"-w",
				"-F", "t"
		};
		File outputFile = new File(backupDir, backupFileName);
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.redirectOutput(outputFile);
		try
		{
			Process process = processBuilder.start();
			try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream())))
			{
				String line;
				while ((line = errorReader.readLine()) != null)
				{
					System.out.println("Ошибка pg_dump: " + line);
				}
			}
			int exitCode = process.waitFor();
			if (exitCode == 0)
			{
				String zipCommand = this.zipProgramPath;
				File zipFile = new File(backupDir, outputFile.getName() + ".gz");
				System.out.println(zipFile.getName());
				String[] zipArgs = {
						"a",
						"-tgzip",
						"\"" + zipFile.getAbsolutePath() + "\"",
						"\"" + outputFile.getAbsolutePath() + "\"",
						"-sdel"
				};
				ProcessBuilder zipProcessBuilder = new ProcessBuilder(zipCommand);
				zipProcessBuilder.command().addAll(Arrays.asList(zipArgs));
				Process zipProcess = zipProcessBuilder.start();
				zipProcess.waitFor();
				System.out.println("Бэкап успешно создан и сжат.");

				sendBackupByEmail(zipFile, date);
			}
			else
			{
				System.err.println("Произошла ошибка при создании бэкапа.");
				System.out.println("Код завершение: " + exitCode);
			}
		}
		catch (IOException | InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	private void sendBackupByEmail(File backup, String date)
	{
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", smtpAuth);
		properties.put("mail.smtp.starttls.enable", startTlsEnable);
		properties.put("mail.smtp.from", fromMail);
		properties.put("mail.smtp.host", smtpHost);
		properties.put("mail.smtp.port", smtpPort);
		if (this.mailDebug)
			properties.put("mail.debug", "true");

		Session session = Session.getInstance(properties, new Authenticator()
		{
			@Override
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(fromMail, passwordMail);
			}
		});

		try
		{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromMail));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
			message.setSubject("Бэкап базы данных от " + date);
			message.setText("С уважением, " + fromMail);
			Multipart multipart = new MimeMultipart();
			BodyPart body = new MimeBodyPart();
			body.setText("Вложение: Бэкап базы данных от " + date);
			multipart.addBodyPart(body);
			BodyPart attachment = new MimeBodyPart();
			attachment.setDataHandler(new DataHandler(new FileDataSource(backup)));
			attachment.setFileName(backup.getName());
			multipart.addBodyPart(attachment);
			message.setContent(multipart);
			Transport.send(message);
			System.out.println("Бэкап успешно отправлен по электронной почте.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
