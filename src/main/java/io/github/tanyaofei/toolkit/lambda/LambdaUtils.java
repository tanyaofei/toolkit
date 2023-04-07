package io.github.tanyaofei.toolkit.lambda;

import lombok.var;
import java.io.*;
import java.util.Locale;

/**
 * 一个可以从 lambda 表达式里获取字段名称的工具类
 */
public class LambdaUtils {

    /**
     * 通过 Lambda 表达式获取方法名
     * <br><br>
     * <h3>静态方法</h3>
     * <pre>{@code
     *   String methodName = LambdaUtils.getMethodName(String::valueOf);    // "valueOf"
     * }</pre>
     *
     * <br>
     * <h3>实例方法: 实例方法需要确定类的类型, 因此需要一个桥接方法</h3>
     * <pre>{@code
     *  public String getMethodNameForUserClass(Lambda<User, ?> lambda) {
     *      return LambdaUtils.getMethodName(lambda);
     *  });
     *  String methodName = getMethodNameForUserClass(User::getUsername);   // "getUsername"
     * }</pre>
     * 写桥接方法的这种用法多数应用于父类定义, 子类提供的场景。如果不想写桥接方法, 可以使用 {@link #getMethodName(Class, Lambda)}
     *
     * @param lambda lambda 表达式
     * @return 方法名
     * @see #getMethodName(Class, Lambda)
     */
    public static String getMethodName(Lambda<?, ?> lambda) {
        return getMethodName((Serializable) lambda);
    }

    /**
     * 通过 Lambda 表达式获取方法名
     * <br>
     * <pre>{@code
     *  String methodName = LambdaUtils.getMethodName(User.class, User::getUsername);   // "getUsername"
     * }</pre>
     *
     * @param clazz  获取哪个类的方法名
     * @param lambda Lambda 表达式
     * @return 方法名
     */
    public static <T> String getMethodName(Class<T> clazz, Lambda<T, ?> lambda) {
        return getMethodName(lambda);
    }

    /**
     * 通过 Lambda 表达式获取字段名
     *
     * <pre>{@code
     *  public String getFieldNameForUserClass(Lambda<User, ?> lambda) {
     *      return LambdaUtils.getFieldName(lambda);
     *  }
     *  String fieldName = getFieldNameForUserClass(User::getUsername); // "username"
     * }</pre>
     * <p>
     * 由于这个方法需要桥接方法来定义取哪个类, 因此通常应用于父类定义, 子类提供的长青。如果不想写桥接方法, 可以使用 {@link #getFieldName(Class, Lambda)}
     *
     * @param lambda Lambda 表达式
     * @see #getFieldName(Class, Lambda)
     * @return 字段名
     */
    public static String getFieldName(Lambda<?, ?> lambda) {
        return methodToField(getMethodName(lambda));
    }

    /**
     * 通过 Lambda 表达式获取字段名
     * <pre>{@code
     *  String fieldName = LambdaUtils.getFieldName(User.class, User::getUsername);     // "username"
     * }</pre>
     *
     * @param clazz  类
     * @param lambda Lambda 表达式
     * @return 字段名
     */
    public static <T> String getFieldName(Class<T> clazz, Lambda<T, ?> lambda) {
        return getFieldName(lambda);
    }

    private static String getMethodName(Serializable lambda) {
        return resolve(lambda).getImplMethodName();
    }

    private static String methodToField(String name) {
        if (name.startsWith("is")) {
            name = name.substring(2);
        } else {
            if (!name.startsWith("get") && !name.startsWith("set")) {
                throw new IllegalArgumentException("Error parsing property name '" + name + "'.  Didn't start with 'is', 'get' or 'set'.");
            }

            name = name.substring(3);
        }

        if (name.length() == 1 || name.length() > 1 && !Character.isUpperCase(name.charAt(1))) {
            name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
        }

        return name;
    }

    private static SerializedLambda resolve(Serializable lambda) {
        if (!lambda.getClass().isSynthetic()) {
            throw new IllegalArgumentException("该方法仅能传入 lambda 表达式产生的合成类");
        }
        try (
                var objIn = new ObjectInputStream(new ByteArrayInputStream(SerializationUtils.serialize(lambda))) {
                    @Override
                    protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
                        Class<?> clazz = super.resolveClass(objectStreamClass);
                        return clazz == java.lang.invoke.SerializedLambda.class ? SerializedLambda.class : clazz;
                    }
                }
        ) {
            return (SerializedLambda) objIn.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new IllegalStateException("This is impossible to happen", e);
        }
    }
}
