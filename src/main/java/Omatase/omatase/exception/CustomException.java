package Omatase.omatase.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final int errorCode;

    public CustomException(int errorCode, String message) {
        super(message); // 親クラスの作成者を呼び出してメッセージを設定
        this.errorCode = errorCode;
    }
}

