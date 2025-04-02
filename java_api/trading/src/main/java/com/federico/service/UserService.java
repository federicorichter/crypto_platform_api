package com.federico.service;

import com.federico.domain.VerificationType;
import com.federico.model.User;

public interface UserService {
    
    public User findUserByJwt(String jwt) throws Exception;
    public User findUserByEmail(String email) throws Exception;
    public User findUserById(Long userId) throws Exception;
    public User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user);
    User updatePassword(User user, String newPassword);

}
