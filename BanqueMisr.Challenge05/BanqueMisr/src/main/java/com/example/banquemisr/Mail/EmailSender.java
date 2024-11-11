package com.example.banquemisr.Mail;

import com.example.banquemisr.Model.Task;
import com.example.banquemisr.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

public class EmailSender {
    @Autowired
    private JavaMailSender javaMailSender;
    private TaskService taskService;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }
    @Scheduled(cron = "0 0 9 * * ?")  // Run daily at 9 AM
    public void sendDeadlineNotifications() {
        LocalDateTime now = LocalDateTime.now();
        List<Task> tasks = taskService.findTasksWithUpcomingDeadlines(now);

        for (Task task : tasks) {
            String subject = "Task Deadline Reminder";
            String body = "Dear " + task.getUser().getUsername() + ", \n\nYour task '" + task.getName() + "' is approaching its due date on "
                    + task.getDueDate() + ". Please ensure it is completed on time.\n\nBest regards,\nYour Task Management System";

            // Send the email
            sendEmail(task.getUser().getEmail(), subject, body);
        }
    }

    // Send notification when a task is updated
    public void sendTaskUpdateNotification(Task task, String updateMessage) {
        String subject = "Task Update Notification";
        String body = "Dear " + task.getUser().getUsername() + ", \n\nThere is an update regarding your task '" + task.getName() + "':\n\n"
                + updateMessage + "\n\nBest regards,\nYour Task Management System";

        // Send the email to the user associated with the task
        sendEmail(task.getUser().getEmail(), subject, body);
    }
}
