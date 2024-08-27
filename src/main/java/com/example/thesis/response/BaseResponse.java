package com.example.thesis.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseResponse<T> {
  private Integer code;
  private String status;
  private T data;

  public static <T> BaseResponse<T> ok(T data) {
    return BaseResponse.<T>builder().code(200).status("OK").data(data).build();
  }

  public static <T> BaseResponse<T> error(String err) {
    return BaseResponse.<T>builder().code(500).status(err).build();
  }
}

