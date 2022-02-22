package academy.pocu.comp3500.lab5;

import javax.crypto.Cipher;
import java.nio.ByteBuffer;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;

public class Bank {
    private HashMap<byte[], Long> account;

    public Bank(byte[][] pubKeys, final long[] amounts) {
        this.account = new HashMap<>();

        for (int i = 0; i < pubKeys.length; i++) {
            account.put(pubKeys[i], Long.valueOf(amounts[i]));
        }
    }

    public long getBalance(final byte[] pubKey) {
        if (pubKey == null) {
            return 0;
        }

        /* expeting Most fast Search Hash Map */
        Long amount = account.get(pubKey);

        return amount != null ? amount.longValue() : 0;

        /* linar Searching */
        /*
        for (int i = 0; i < bankPubkeys.length; i++) {
            boolean isAccount = true;

            for (int j = 0; j < bankPubkeys[i].length; j++) {
                if (bankPubkeys[i][j] != pubKey[j]) {
                    isAccount = false;
                    break;
                }
            }

            if (isAccount == true) {
                return bankAmounts[i];
            }
        }
        return 0;
         */
    }

    public boolean transfer(final byte[] from, byte[] to, final long amount, final byte[] signature) {
        if (from == null || to == null || signature == null) {
            System.out.println("Invaild argumnet");
            return false;
        }

        //signature를 rsa from 해독한다
        //시그니처는 다음 메시지의 SHA-256 해시를 송금자의 비밀(private) 키로 암호화를 해서 서명을 만듭니다.
        String decodeSignature = decrypt(signature, from);
        if (decodeSignature == null) {
            System.out.println("Invaild Encrypt");
            return false;
        }

        //은행 계좌정보에서 시그니처와 동일한 SHA-256해시를 얻는다
        String bankSignature = getHashMessage(from, to, amount);
        if (bankSignature == null) {
            System.out.println("Invaild Signature");
            return false;
        }

        //은행에서 가지고 있는 계좌 정보와 decode한 signature가 맞는지 확인한다
        //보내는 사람이 암호화는 제대로 했지만 그 내용이 다를 수도 있으니까
        if (decodeSignature.equals(bankSignature) == false) {
            System.out.println("Invaild signature");
            return false;
        }

        //에외처리
        if (account.containsKey(from) == false) {
            System.out.println("Bank dosen't have sender's Account");
            return false;
        }

        if (account.containsKey(to) == false) {
            System.out.println("bank doesn't have account for receiver");
        }

        if (amount < 1) {
            System.out.println("Invaild amount");
            return false;
        }

        if (getBalance(from) < amount ) {
            System.out.println("Invaild Balance");
            return false;
        }

        //송금
        //해시로 보낼 계좌를 찾고 계좌에서 금액을 뺸다
        //해시로 받을 계좌를 찾고 계좌에서 금액을 더 한다
        //true를 반환한다

        long senderNewAmount = getBalance(from) - amount;
        long receiverNewAmount = getBalance(to) + amount;

        account.replace(from, senderNewAmount);
        account.replace(to, receiverNewAmount);

        return true;
    }

    public String decrypt(byte[] signatureByte, final byte[] publicKeyByte) {

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publickey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyByte));

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(cipher.DECRYPT_MODE, publickey);

            byte[] plaintext = cipher.doFinal(signatureByte);

            return Base64.getEncoder().encodeToString(plaintext);

        } catch (Exception e) {
            return null;
        }
    }

    public String getHashMessage(byte[] from, byte[] to, long amount) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(from.length + to.length + 8);
        byteBuffer.put(from).put(to).putLong(amount);
        byte[] bankSignature = byteBuffer.array();

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(bankSignature);

            return Base64.getEncoder().encodeToString(md.digest());
        } catch (Exception e) {
            return null;
        }
    }


}