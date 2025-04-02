package com.federico.service;

import com.federico.domain.VerificationType;
import com.federico.model.User;
import com.federico.model.VerificationCode;

public interface VerificationCodeService {
    
    VerificationCode sendVerificationOtp(User user, VerificationType verificationType);
    VerificationCode getVerificationCodeBiId(Long id) throws Exception;

    VerificationCode getVerificationCodeByUser(Long userID);

    void deleteVerificationCodeById(VerificationCode verificationCode);
}
