package vn.aptech.project4.forgotpassword;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import net.bytebuddy.utility.RandomString;
import vn.aptech.project4.entity.Customer;

@Controller
public class ForgotPasswordController {

	@Autowired
	private ResetPasswordServices resetpasswordService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@GetMapping("/forgot_password")
	public String showForgotPasswordForm(Model model) {
		model.addAttribute("pageTitle", "Forgot Password");
		return"guest/forgot-password-form";
	}
	@PostMapping("/forgot_password")
	public String processForgotPassword(HttpServletRequest request, Model model) {
		String email = request.getParameter("email");
		String token = RandomString.make(30);
		
		try {
			resetpasswordService.updateResetPasswordToken(token, email);
			
			String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
			
			//send mail
			sendEmail(email, resetPasswordLink);
			model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
		} catch (CustomerNotFoundException ex) {
			model.addAttribute("error", ex.getMessage());
		} catch (UnsupportedEncodingException | MessagingException e) {
			model.addAttribute("error", "Error while sending email.");
		} 
		
		
		return"guest/forgot-password-form";
	}
	private void sendEmail(String email, String resetPasswordLink) throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom("contact@ncoffee.com", "Ncoffee Support ");
		helper.setTo(email);
		
		String subject = "Here's the link to reset your password";
		String content = "<p>Hello,</p>"
	            + "<p>You have requested to reset your password.</p>"
	            + "<p>Click the link below to change your password:</p>"
	            + "<p><a href=\"" + resetPasswordLink + "\">Change my password</a></p>"
	            + "<br>"
	            + "<p>Ignore this email if you do remember your password, "
	            + "or you have not made the request.</p>";
		helper.setSubject(subject);
		helper.setText(content, true);
		
		mailSender.send(message);
	}
	@GetMapping("/reset_password")
	public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
		Customer customer = resetpasswordService.getByResetPasswordToken(token);
		if (customer == null) {
			model.addAttribute("title", "Reset your Password");
			model.addAttribute("message", "Invalid Token");
			return "message";
		}
		model.addAttribute("token", token);
		model.addAttribute("pageTitle", "Reset Your Password");
		return "guest/reset-password-form";
	}
	@PostMapping("/reset_password")
	public String processResetPassword(HttpServletRequest request, Model model) {
		String token = request.getParameter("token");
		String password = "{noop}"+request.getParameter("password");
		
		
		Customer customer = resetpasswordService.getByResetPasswordToken(token);
		if (customer == null) {
			model.addAttribute("title", "Reset your Password");
			model.addAttribute("message", "Invalid Token");
		}else {
			resetpasswordService.updatePassword(customer, password);
			model.addAttribute("message", "You have successfully changed your Password!");
		}
		return "guest/index";
	}
}
