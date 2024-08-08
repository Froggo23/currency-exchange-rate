package com.choe.currencyapp.entity.history;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {
    private String dateKind;
    private String dateName;
    private String isHoliday;
    private String locdate;
    private int seq;
}
