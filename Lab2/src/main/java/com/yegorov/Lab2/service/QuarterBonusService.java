package com.yegorov.Lab2.service;

import com.yegorov.Lab2.model.Positions;
import org.springframework.stereotype.Service;

@Service
public interface QuarterBonusService {
    double calculateQuarterBonus(double salary, double bonus, int workDays, Positions position);
}
