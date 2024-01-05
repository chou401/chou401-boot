package io.chou401.framework.jackson.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * {@code @author}  chou401
 * {@code @date} 2023/6/20
 **/
public class JacksonDateDeserializer extends JsonDeserializer<Date> {

    public static final JacksonDateDeserializer INSTANCE = new JacksonDateDeserializer();

    /**
     * 日期格式数组
     */
    private static final String[] DATE_PATTERNS = {
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd",
    };

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String dateString = jsonParser.getText();
        if (dateString == null) {
            return null;
        }
        dateString = dateString.trim();
        if (StringUtils.isBlank(dateString)) {
            return null;
        }
        Date date = null;
        boolean flag = false;
        for (String pattern : DATE_PATTERNS) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                date = simpleDateFormat.parse(dateString);
                flag = true;
                break;
            } catch (ParseException e) {
            }
        }
        if (flag) {
            return date;
        } else {
            throw new IllegalArgumentException("不能解析的日期:" + dateString);
        }
    }

}
