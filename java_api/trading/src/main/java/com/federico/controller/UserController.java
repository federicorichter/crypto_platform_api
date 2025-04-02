package com.federico.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.federico.domain.VerificationType;
import com.federico.model.ForgotPasswordToken;
import com.federico.model.User;
import com.federico.model.VerificationCode;
import com.federico.request.ForgotPasswordTokenRequest;
import com.federico.request.ResetPasswordRequest;
import com.federico.response.ApiResponse;
import com.federico.response.AuthResponse;
import com.federico.service.EmailService;
import com.federico.service.ForgotPasswordService;
import com.federico.service.UserService;
import com.federico.service.VerificationCodeService;
import com.federico.utils.OtpUtils;

@RestController
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;
    
    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorized") String jwt) throws Exception{
            User user = userService.findUserByJwt(jwt);

            return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(@RequestHeader("Authorized") String jwt,
                                                        @PathVariable VerificationType verificationType) throws Exception{  
        User user = userService.findUserByJwt(jwt); 
        VerificationCode verificationCode = verificationCodeService.getVerificationCodeBiId(user.getId());
        
        if(verificationCode == null){
            verificationCode = verificationCodeService.sendVerificationOtp(user, verificationType);
        }
        if(verificationType.equals(VerificationType.EMAIL)){
            emailService.sendVerificationOtpEmail(user.getEmail(), verificationCode.getOtp());
        }
        
        return new ResponseEntity<>("Verification otp sent successfully", HttpStatus.OK);
    }

    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enabeTwoFactorAuthentication(@RequestHeader("Authorized") String jwt,
                                                             @PathVariable String otp) throws Exception{
        User user = userService.findUserByJwt(jwt);

        VerificationCode verificationCode= verificationCodeService.getVerificationCodeByUser(user.getId());

        String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL)?    //address to send verification code
                        verificationCode.getEmail():verificationCode.getMobile();

        boolean isVerified = verificationCode.getOtp().equals(otp);

        if(isVerified){
            User updatedUser = userService.enableTwoFactorAuthentication(verificationCode.getVerificationType(), sendTo, user);
            return new ResponseEntity<User>(updatedUser, HttpStatus.OK);

        }
        throw new Exception("wrong otp");
    }

    @PostMapping("/auth/users/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgotPasswordOtp(
                                                        @RequestBody ForgotPasswordTokenRequest req) throws Exception{  
        
                                                            
        User user = userService.findUserByEmail(req.getSendTo());
        String otp = OtpUtils.generateOtp();
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
                                    
        ForgotPasswordToken token = forgotPasswordService.findByUser(user.getId());

        if(token == null){
            token = forgotPasswordService.createToken(user, id, otp, req.getVerificationType() ,req.getSendTo());
        }

        if(req.getVerificationType().equals(VerificationType.EMAIL)){
            emailService.sendVerificationOtpEmail(user.getEmail(), token.getOtp());
        }

        AuthResponse response = new AuthResponse();

        response.setSession(token.getId());
        response.setMessage("Password reset otp sent");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/auth/users/reset-password/verify-otp")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam String id,
    @RequestBody ResetPasswordRequest req,
                                            @RequestHeader("Authorized") String jwt
                                                             ) throws Exception{
        
                                                            
        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findById(id);                                                        
        
        boolean isVerified = forgotPasswordToken.getOtp().equals(req.getOtp());

        if(isVerified){
            userService.updatePassword(forgotPasswordToken.getUser(), req.getPassword());
            ApiResponse res = new ApiResponse();
            res.setMessage("password update successfully");
            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
            
        }
        throw new Exception("wrong otp");
    }


}
