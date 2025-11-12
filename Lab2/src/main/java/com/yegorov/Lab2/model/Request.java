package com.yegorov.Lab2.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    /** Уникальный идентификатор сообщения */
    @NotBlank()
    @Size(max = 32)
    private String uid;

    /** Уникальный идентификатор операции */
    @NotBlank()
    @Size(max = 32)
    private String operationUid;

    /** Имя системы отправителя */
    private Systems systemName;

    /** Время создания сообщения */
    @NotBlank()
    private String systemTime;

    /** Наименование ресурса */
    private String source;

    /** Должность сотрудника */
    private Positions position;

    /** Размер заработной платы */
    private Double  salary;

    /** Размер бонуса */
    private Double  bonus;

    /** Количество отработанных дней */
    private Integer workDays;

    /** Уникальный идентификатор коммуникации */
    @NotNull()
    @Min(value = 1)
    @Max(value = 100000)
    private int communicationId;

    /** Уникальный идентификатор шаблона */
    private int templateId;

    /** Код продукта */
    private int productCode;

    /** СМС код */
    private int smsCode;

    @Override
    public String toString() {
        return "Request{" +
                "uid='" + uid + '\'' +
                ", operationUid='" + operationUid + '\'' +
                ", systemName=" + systemName +
                ", systemTime='" + systemTime + '\'' +
                ", source='" + source + '\'' +
                ", position=" + position +
                ", salary=" + salary +
                ", bonus=" + bonus +
                ", workDays=" + workDays +
                ", communicationId=" + communicationId +
                ", templateId=" + templateId +
                ", productCode=" + productCode +
                ", smsCode=" + smsCode +
                '}';
    }

}
