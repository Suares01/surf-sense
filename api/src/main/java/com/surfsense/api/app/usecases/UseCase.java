package com.surfsense.api.app.usecases;

import com.surfsense.api.app.errors.ApiException;

public interface UseCase<T, R> {
  public R execute(T params) throws ApiException;
}
