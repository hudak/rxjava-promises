package com.darylteo.promises;

import rx.util.functions.Func1;

public abstract class RepromiseFunction<I, O> implements Func1<I, Promise<O>> {
}