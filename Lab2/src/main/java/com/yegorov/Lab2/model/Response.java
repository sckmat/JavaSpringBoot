package com.yegorov.Lab2.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {

    /** Уникальный идентификатор сообщения */
    private String uid;

    /** Уникальный идентификатор операции */
    private String operationUid;

    /** Время создания сообщения */
    private String systemTime;

    /** Код результата выполнения операции */
    private Codes code;

    /** Код ошибки */
    private ErrorCodes errorCode;

    /** Сообщение об ошибке */
    private ErrorMessages errorMessage;

    /** Годовой бонус */
    private Double annualBonus;

    /** Квартальный бонус */
    private Double quarterBonus;
}
