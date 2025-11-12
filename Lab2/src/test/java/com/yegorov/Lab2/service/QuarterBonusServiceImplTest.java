package com.yegorov.Lab2.service;

import com.yegorov.Lab2.model.Positions;
import org.junit.jupiter.api.Test;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

class QuarterBonusServiceImplTest {

    private final QuarterBonusService service = new QuarterBonusServiceImpl();

    @Test
    void calculateQuarterBonus_returnsCorrectValue_forManager() {
        // given
        double salary = 120_000.0;
        double bonus = 0.20;
        int workDays = 220;
        Positions position = Positions.CTO;

        int year = Year.now().getValue();
        int daysInYear = Year.isLeap(year) ? 366 : 365;
        int quarterDays = daysInYear / 4;

        double expected = salary * bonus * quarterDays * position.getPositionCoefficient() / workDays;

        // when
        double actual = service.calculateQuarterBonus(salary, bonus, workDays, position);

        // then
        assertEquals(expected, actual, 1e-9, "Квартальная премия рассчитана неверно");
    }

    @Test
    void calculateQuarterBonus_throwsForNonManager() {
        // given
        double salary = 80_000.0;
        double bonus = 0.15;
        int workDays = 200;
        Positions position = Positions.DEV;

        // expect
        assertThrows(IllegalArgumentException.class,
                () -> service.calculateQuarterBonus(salary, bonus, workDays, position),
                "Для не-менеджера должен выбрасываться IllegalArgumentException");
    }
}