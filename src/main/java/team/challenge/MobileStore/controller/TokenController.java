package team.challenge.MobileStore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.challenge.MobileStore.dto.PasswordResetRequest;
import team.challenge.MobileStore.model.UserModel;
import team.challenge.MobileStore.model.VerificationToken;
import team.challenge.MobileStore.service.MailSenderService;
import team.challenge.MobileStore.service.PasswordVerificationTokenService;
import team.challenge.MobileStore.service.UserService;
import team.challenge.MobileStore.service.EmailVerificationTokenService;

@RestController
@RequestMapping("/api/v1/token")
@RequiredArgsConstructor
public class TokenController {
    private final UserService userService;
    private final EmailVerificationTokenService emailVerificationTokenService;
    private final PasswordVerificationTokenService passwordVerificationTokenService;
    private final MailSenderService mailSenderService;

    @PostMapping("/mail/create")
    @PreAuthorize("authentication.principal.username == #email")
    public ResponseEntity<?> createEmailToken(@RequestBody String email){
        UserModel user = userService.getOneByEmail(email);
        VerificationToken token = emailVerificationTokenService.createToken(user);
        String verifyToken = token.getToken() + "+" + user.getId();
        String message = "To verify your email go to https://electronic-heaven.netlify.app/verify-email?token=" + verifyToken;
        String subject = "Email verification";
        mailSenderService.sendSimpleMessage(email, subject, message);
        return ResponseEntity.ok("To verify your email go to https://electronic-heaven.netlify.app/verify-email?token=" + verifyToken);
    }
    @PostMapping("/mail/verify")
    public ResponseEntity<?> verifyEmail(@RequestBody String token){
        String body = "Token invalid!";
        String[] tokenPlusId = token.split("\\+");
        boolean isVerified = emailVerificationTokenService.verifyToken(tokenPlusId[0], tokenPlusId[1]);
        if (isVerified) body = "Token valid!";
        return ResponseEntity.ok(body);
    }
    @PostMapping("/password/create")
    public ResponseEntity<?> createPasswordToken(@RequestBody String email){
        UserModel user = userService.getOneByEmail(email);
        VerificationToken token = passwordVerificationTokenService.createToken(user);
        String verifyToken = token.getToken() + "+" + user.getId();
        String message = "To reset your password go to https://electronic-heaven.netlify.app/password-reset?token=" + verifyToken;
        String subject = "Password reset";
        mailSenderService.sendSimpleMessage(email, subject, message);
        return ResponseEntity.ok("To verify your email go to https://electronic-heaven.netlify.app/password-reset?token=" + verifyToken);
    }
    @PostMapping("/password/verify")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest){
        String[] tokenPlusId = passwordResetRequest.token().split("\\+");
        return ResponseEntity.ok(passwordVerificationTokenService.verifyToken(tokenPlusId[0], tokenPlusId[1], passwordResetRequest.password()));
    }
}
