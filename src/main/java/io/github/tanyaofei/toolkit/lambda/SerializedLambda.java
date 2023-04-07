package io.github.tanyaofei.toolkit.lambda;

import java.io.Serializable;

/**
 * 完全拷贝于 {@link java.lang.invoke.SerializedLambda}, 用于反序列化 lambda 表达式来使用
 */
@SuppressWarnings("unused")
public class SerializedLambda implements Serializable {
    private static final long serialVersionUID = 8025925345765570181L;

    private Class<?> capturingClass;

    private String functionalInterfaceClass;

    private String functionalInterfaceMethodName;

    private String functionalInterfaceMethodSignature;

    private String implClass;

    private String implMethodName;

    private String implMethodSignature;

    private int implMethodKind;

    private String instantiatedMethodType;

    private Object[] capturedArgs;

    public String getImplMethodName() {
        return implMethodName;
    }

}
