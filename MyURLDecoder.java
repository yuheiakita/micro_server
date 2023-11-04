import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MyURLDecoder {

    private static int hex2int(byte b1, byte b2) {
        // 2つの16進数文字を整数に変換します
        int digitValue1 = Character.digit(b1, 16);
        int digitValue2 = Character.digit(b2, 16);
        if (digitValue1 == -1 || digitValue2 == -1) {
            throw new IllegalArgumentException("Invalid hexadecimal characters: " + b1 + ", " + b2);
        }
        return (digitValue1 << 4) + digitValue2;
    }

    public static String decode(String src, String enc) throws UnsupportedEncodingException {
        Charset charset = Charset.forName(enc);
        byte[] srcBytes = src.getBytes(StandardCharsets.ISO_8859_1);
        // 変換されたバイトは、ソースと同じ長さかそれ以下になります
        byte[] destBytes = new byte[srcBytes.length];

        int destIdx = 0;
        for (int srcIdx = 0; srcIdx < srcBytes.length; srcIdx++) {
            if (srcBytes[srcIdx] == '%') {
                // 2つの16進数の桁のために十分なバイトが残っていることを確認します
                if (srcIdx + 2 >= srcBytes.length) {
                    throw new IllegalArgumentException("Incomplete percent encoding at: " + srcIdx);
                }
                destBytes[destIdx++] = (byte) hex2int(srcBytes[srcIdx + 1], srcBytes[srcIdx + 2]);
                srcIdx += 2;
            } else {
                destBytes[destIdx++] = srcBytes[srcIdx];
            }
        }

        // destBytesの使用された部分のみを新しい配列にコピーします
        byte[] finalBytes = new byte[destIdx];
        System.arraycopy(destBytes, 0, finalBytes, 0, destIdx);

        return new String(finalBytes, charset);
    }
}
