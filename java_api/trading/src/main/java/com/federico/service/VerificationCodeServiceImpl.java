package com.federico.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.federico.domain.VerificationType;
import com.federico.model.User;
import com.federico.model.VerificationCode;
import com.federico.repository.VerificationCodeRepository;
import com.federico.utils.OtpUtils;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Override
    public VerificationCode sendVerificationOtp(User user,VerificationType verificationType) {
        VerificationCode verificationCode1 = new VerificationCode();
        verificationCode1.setOtp(OtpUtils.generateOtp());
        verificationCode1.setVerificationType(verificationType);
        verificationCode1.setUser(user);

        return verificationCodeRepository.save(verificationCode1);
    }

    @Override
    public VerificationCode getVerificationCodeBiId(Long id) throws Exception {
        Optional<VerificationCode> verificationCode = verificationCodeRepository.findById(id);

        if(verificationCode.isPresent()){
            return verificationCode.get();
        }
        throw new Exception("verificaton code not found");
    }

    @Override
    public VerificationCode getVerificationCodeByUser(Long userID) {
        return verificationCodeRepository.findByUserId(userID); 
    }

    @Override
    public void deleteVerificationCodeById(VerificationCode verificationCode) {
        verificationCodeRepository.delete(verificationCode);
    }
    
}
