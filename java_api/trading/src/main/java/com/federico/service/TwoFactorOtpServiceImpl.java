package com.federico.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.federico.model.TwoFactorOTP;
import com.federico.model.User;
import com.federico.repository.TwoFactorOtpRepository;

@Service
public class TwoFactorOtpServiceImpl implements TwoFactorOtpService{

    @Autowired
    private TwoFactorOtpRepository twoFactorOtpRepository;

    @Override
    public TwoFactorOTP createTwoFactorOTP(User user, String otp, String jwt) {
       UUID uuid = UUID.randomUUID();

       String id = uuid.toString();

       TwoFactorOTP twoFactorOTP = new TwoFactorOTP();
       twoFactorOTP.setOtp(otp);
       twoFactorOTP.setJwt(jwt);
       twoFactorOTP.setUser(user);
       twoFactorOTP.setId(id);

       return twoFactorOtpRepository.save(twoFactorOTP);
    }

    @Override
    public TwoFactorOTP findByUser(Long userId) {
        return twoFactorOtpRepository.findByUserId(userId);
    }

    @Override
    public TwoFactorOTP findById(String id) {
        Optional<TwoFactorOTP> opt = twoFactorOtpRepository.findById(id);

        return opt.orElse(null); //if not data present, returns null
    }

    @Override
    public boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOTP, String otp) { 
        return twoFactorOTP.getOtp().equals(otp);
    }

    @Override
    public void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP) {
        twoFactorOtpRepository.delete(twoFactorOTP);
    }
    
}
