# Rxjava-Promises #

## Documentation

### Creating a Promise

Either use the constructor or the defer() static method

```java
Promise<String> p1 = Promise.defer();
Promise<String> p2 = new Promise<>();
````

### then()

To add a new action to a promise that is invoked when it is fulfilled, you may use the then(onFulfilled) function.

```java
p1.then(new PromiseAction<String>() {
  public void call(String value) {
    // do something with String value
  }
});

p1.then(new PromiseFunction<String, String>() {
  public String call(String value) {
    // do something with String value and return a String
    return value.toUpperCase();
  }
});

p1.then(new RepromiseFunction<String, String>() {
  public Promise<String> call(String value) {
    // do something with String value and return another Promise for a String
    Promise<String> result = Promise.defer():
    ...
    return result;
  }
});
````

To detect when a promise is rejected (or an error occurs), using then(onFulfilled, onRejected).

```java
p1.then(new PromiseAction<String>() {
  public void call(String value) {
    // do something with String value
  }
}, new PromiseAction<Exception>() {
  public void call(Exception reason) {
    // recover from the error
  }
});
````

Alternatively, you may use fail(onRejected).

```java
p1.fail(new PromiseAction<Exception>() {
  public void call(Exception reason) {
    // recover from the error
  }
});
````

### finally

Finally is a non-spec feature for promises. It is invoked immediately after the promise is fulfilled
or rejected. Just call fin(onFinally).

```java
p1.fin(new FinallyAction() {
  public void call() {
    // cleanup 
  }
});

p1.fin(new FinallyFunction<String>() {
  public Promise<String> call() {
    // returning a promise will delay the chain of promises until it is fulfilled
    return new Promise<String>();
  }
});
````

## Java vs Other Languages

As you can see from the code above, while Promises offers a solution to "callback hell", its usage in Java
is still rather verbose, and due to the type-safe nature of Java, when using both onFulfilled and onRejected, 
you must always return the same return-type as its handler will be expecting a single class. If, for some 
reason, you absolutely must allow for different return types, you should then use a container class, or 
a common superclass.

There are also several handler implementations available for convenience to Java users.

If you are using a different language implementation (for example, Groovy), you will be able to sacrifice 
the type-safety of your code to avoid that limitation. For example, in Groovy, your code should look more like
this:

```groovy
aPromiseReturningFunction()
.then { result ->
  return result.toUpperCase()
}
.then { result ->
  return result[6..11]
}
.fail { error ->
  return 'World'
}
.then { result ->
  println result
}
````

Keep your ears open for new language implementations in the future!