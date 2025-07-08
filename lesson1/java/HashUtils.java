import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

    public static String sha256(String input) {
        try {
            // 获取 SHA-256 摘要器
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 执行哈希计算
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // 将字节数组转换成十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b); // 保持两位十六进制
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found.");
        }
    }
}
