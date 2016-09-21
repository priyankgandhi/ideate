/**
 * 
 */
package service;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import pojo.AuthUser;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


@Service
public class EmailService {
    
    Logger LOG = LoggerFactory.getLogger(EmailService.class);

    private JavaMailSender mailSender;

    
    public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public String resolveTemplate(String templatePath, Map<String, Object> data){
    	Writer ret = new StringWriter();
    	Configuration cfg = new Configuration();
    	try {
			Template template = cfg.getTemplate(templatePath);			
			try {
				template.process(data, ret);
			} catch (TemplateException e) {				
				e.printStackTrace();
			}
		} catch (IOException e) {			
			e.printStackTrace();
		}
    	return ret.toString();
    }
    
    private void sendEmail(String content,String toEmail,String subject, List<String> bcc){
    	MimeMessage mimeMessage = mailSender.createMimeMessage();
    	try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			helper.setText(toEmail, true);
			mimeMessage.setContent(content, "text/html");
			helper.setSubject(subject);
			helper.setTo(toEmail);
			if(null!=bcc){
				for(String email : bcc){
					try{
					mimeMessage.addRecipient(RecipientType.BCC, new InternetAddress(email));
					}
					catch(Exception ex){
						ex.printStackTrace();
					}
				}
			}
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
    }
    
    @Async
    public void sendUserSignupEmail(AuthUser authUser) {
    	String templatePath = "src/main/webapp/WEB-INF/email-templates/welcomeEmailTemplate.html";
    	Map<String, Object> data = new HashMap<String, Object>();
    	data.put("fullname", authUser.getFullName());
    	String htmlContent = resolveTemplate(templatePath,data);
    	sendEmail(htmlContent,authUser.getEmail(),"Thanks. We have got you in.", null);
    }    
}
