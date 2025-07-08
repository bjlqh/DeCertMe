import java.security.*;
import java.util.Base64;

/**
 * 实践非对称加密 RSA（编程语言不限）：
 *
 * 先生成一个公私钥对
 * 用私钥对符合 POW 4 个 0 开头的哈希值的 “昵称 + nonce” 进行私钥签名
 * 用公钥验证
 */
public class Job2 {

    public static void main(String[] args) throws Exception {
        //生成公私钥
        KeyPair keyPair = generateRSAKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        System.out.printf("公钥：%s %n", Base64.getEncoder().encodeToString(publicKey.getEncoded()));

        //pow
        String input = "max";
        String hash = Job1.findPow(input, 4);

        //私钥签名
        String signature = signData(hash, privateKey);
        System.out.println("签名：" + signature);

        //公钥验证
        boolean verified = verifySignature(hash, signature, publicKey);
        System.out.println("验证结果：" + verified);
    }

    // ========== 生成 RSA 公私钥对 ==========
    public static KeyPair generateRSAKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048); // 使用 2048 位密钥
        return generator.generateKeyPair();
    }

    // ========== 使用私钥对数据进行签名 ==========
    // 先用 SHA-256 对原始数据做摘要，再用 RSA 私钥对摘要进行签名。
    public static String signData(String data, PrivateKey privateKey) throws Exception {
        Signature signer = Signature.getInstance("SHA256withRSA");      //生成签名器
        signer.initSign(privateKey);        //传入的私钥对签名器进行初始化
        signer.update(data.getBytes());     //这里不会直接签名，而是先“喂数据”，内部会对数据做哈希处理
        byte[] signatureBytes = signer.sign();      //执行签名操作
        return Base64.getEncoder().encodeToString(signatureBytes);
    }

    // ========== 使用公钥验证签名 ==========
    public static boolean verifySignature(String data, String signature, PublicKey publicKey) throws Exception {
        Signature verifier = Signature.getInstance("SHA256withRSA");
        verifier.initVerify(publicKey);
        verifier.update(data.getBytes());
        byte[] signatureBytes = Base64.getDecoder().decode(signature);
        return verifier.verify(signatureBytes);
    }
}
