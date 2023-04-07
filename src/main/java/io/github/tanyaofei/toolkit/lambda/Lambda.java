package io.github.tanyaofei.toolkit.lambda;

import java.io.Serializable;

@FunctionalInterface
public interface Lambda<T, R> extends Serializable {

    @SuppressWarnings("unused")
    R unreachable(T t);

}
