package com.surfsense.api.infra.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ParseObject {
  private static ObjectMapper mapper = new ObjectMapper();

  public static String toJson(Object object) throws JsonProcessingException {
    if (object == null) {
      return null;
    }

    return mapper.writeValueAsString(object);
  }
}
