package com.yegorov.Lab2.service;

import com.yegorov.Lab2.model.Positions;
import org.springframework.stereotype.Service;

import java.time.Year;

@Service
public class AnnualBonusServiceImpl implements AnnualBonusService {

    @Override
    public double calculate(Positions positions, double salary, double bonus, int workDays) {
        int year = Year.now().getValue();
        int daysInYear = Year.isLeap(year) ? 366 : 365;

        return salary * bonus * daysInYear * positions.getPositionCoefficient() / workDays;    }
}
