package lynn.util.date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *  yyyy-MM-dd HH:mm:ss 转为 LocalDateTime
 */
public final class LocalDateTimeKeyDes extends KeyDeserializer {
    public static final LocalDateTimeKeyDes INSTANCE = new LocalDateTimeKeyDes();

    public LocalDateTimeKeyDes() {
    }

    @Override
    public Object deserializeKey(String s, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtil.YMD_HMS);
        return StringUtils.isBlank(s) ? null : LocalDateTime.parse(s, formatter);
    }
}