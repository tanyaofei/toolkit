package io.github.tanyaofei.toolkit.lambda;

import lombok.Data;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class LambdaUtilsTest extends Assertions {


    @Test
    public void testLambdaUtils() {
        assertEquals(LambdaUtils.getFieldName(User.class, User::getUsername), "username");
        assertEquals(LambdaUtils.getMethodName(User.class, User::getUsername), "getUsername");
        assertEquals(getUserFieldName(User::getUsername), "username");
        assertEquals(getUserMethodName(User::getUsername), "getUsername");

        assertEquals(LambdaUtils.getMethodName(Object::toString), "toString");
    }

    private String getUserFieldName(Lambda<User, ?> lambda) {
        return LambdaUtils.getFieldName(lambda);
    }

    private String getUserMethodName(Lambda<User, ?> lambda) {
        return LambdaUtils.getMethodName(lambda);
    }

    @Data
    @Accessors(chain = true)
    public static class User {
        private String username;
    }

}
