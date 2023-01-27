package com.clojure.lang;

public interface ISeq {
    Object first();
    ISeq rest();
}
