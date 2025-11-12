package com.yegorov.Lab2.service;

import com.yegorov.Lab2.model.Positions;
import org.springframework.stereotype.Service;

import java.time.Year;

@Service
public class QuarterBonusServiceImpl implements QuarterBonusService {

    @Override
    public double calculateQuarterBonus(double salary, double bonus, int workDays, Positions position) {
        if (!position.isManager()) {
            throw new IllegalArgumentException("Квартальная премия доступна только менеджерам и управленцам!");
        }

        int year = Year.now().getValue();
        int daysInYear = Year.isLeap(year) ? 366 : 365;
        int quarterDays = daysInYear / 4;

        return salary * bonus * quarterDays * position.getPositionCoefficient() / workDays;
    }
}
