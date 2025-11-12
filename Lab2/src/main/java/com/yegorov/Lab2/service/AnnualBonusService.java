package com.yegorov.Lab2.service;

import com.yegorov.Lab2.model.Positions;
import org.springframework.stereotype.Service;

@Service
public interface AnnualBonusService {
    double calculate(Positions positions, double salary, double bonus, int workDays);
}
