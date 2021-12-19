package management.config;

import management.email.EmailSender;
import management.email.MailboxProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfiguration {

    private transient EmailSender emailSender;

    @Bean
    public EmailSender createEmailSender() {
        return new MailboxProvider();
    }
}
