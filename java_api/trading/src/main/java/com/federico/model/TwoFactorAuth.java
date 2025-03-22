package com.federico.model;

import com.federico.domain.VerificationType;

import lombok.Data;

@Data
public class TwoFactorAuth{
    private boolean isEnabled = false;
    private VerificationType sendTo;
}