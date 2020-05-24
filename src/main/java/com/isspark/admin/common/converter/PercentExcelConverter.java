package com.isspark.admin.common.converter;

import com.alibaba.excel.converters.AutoConverter;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.math.BigDecimal;

/**
 * @version V1.0
 * @class: PercentExcelConverter
 * @description:
 * @author: xuzheng
 * @create: 2020-04-23 15:58
 **/
public class PercentExcelConverter extends AutoConverter {

    @Override
    public CellData convertToExcelData(Object value, ExcelContentProperty contentProperty,
                                       GlobalConfiguration globalConfiguration) {
        BigDecimal decimal = (BigDecimal) value;
        if(decimal == null){
            decimal = BigDecimal.valueOf(0);
        }
        String result = decimal.multiply(BigDecimal.valueOf(100)).setScale(2,BigDecimal.ROUND_HALF_UP).toString()+"%";
        return new CellData(result);
    }
}
