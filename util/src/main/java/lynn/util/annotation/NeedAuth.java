package lynn.util.annotation;

import org.springframework.core.annotation.AliasFor;
import lynn.util.enums.NeedAuthEnum;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.METHOD
})
@Inherited
@Documented
public @interface NeedAuth {
    @AliasFor("needAuth")
    NeedAuthEnum value() default NeedAuthEnum.TOKEN;

    @AliasFor("value")
    NeedAuthEnum needAuth() default NeedAuthEnum.TOKEN;
}
