package lynn.util.date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 *  LocalDateTime 序列化为 timestamp
 */
public final class LocalDateTimeSer extends JsonSerializer<LocalDateTime> {
    public static final LocalDateTimeSer INSTANCE = new LocalDateTimeSer();

    public LocalDateTimeSer() {
    }

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeNumber(LocalDateTimeUtils.getMilliByTime(localDateTime));
    }
}