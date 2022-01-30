package academy.pocu.comp3500.lab4;

import academy.pocu.comp3500.lab4.pocuhacker.RainbowTable;
import academy.pocu.comp3500.lab4.pocuhacker.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.zip.CRC32;

public final class Cracker {
    User[] userTable;
    String email;
    String password;

    public Cracker(User[] userTable, String email, String password) {
        this.userTable = userTable;
        this.email = email;
        this.password = password;
    }

    public String[] run(final RainbowTable[] rainbowTables) {
        /*
        String[] plainPassword = new String[userTable.length];

        boolean isNotNull = false;
        for (int i = 0; i < rainbowTables.length; i++) {
            for (int j = 0; j < userTable.length; j++) {
                if (rainbowTables[i].contains(userTable[j].getPasswordHash())) {
                    plainPassword[j] = rainbowTables[i].get(userTable[j].getPasswordHash());
                    isNotNull = true;
                }
            }

            if (isNotNull == true) {
                break;
            }
        }
        */

        /*
        시간 복잡도 최선으로 만들기
        1. email과 플래인 패스워드를 통해서 패스워드 해시를 만들어서
        어떤 rainbow테이블을 쓸지 고려
        2. 유저 테이블 도는데 N, 패스워드 해시를 만들거나 푸는데 N,
         */

        String serverDbHash = null;
        for (int i = 0; i < userTable.length; i++) {
            if (userTable[i].getEmail() == email) {
                serverDbHash = userTable[i].getPasswordHash();
                break;
            }
        }

        String[] hackerPwHash = new String[rainbowTables.length];

        CRC32 crc32 = new CRC32();
        byte[] inputByte = password.getBytes(StandardCharsets.UTF_8);
        crc32.update(inputByte);

        hackerPwHash[0] = Long.toString(crc32.getValue());
        hackerPwHash[1] = getHashByType("MD2");
        hackerPwHash[2] = getHashByType("MD5");
        hackerPwHash[3] = getHashByType("SHA-1");
        hackerPwHash[4] = getHashByType("SHA-256");

        int serverHashAlgorithmIndex = -1;
        for (int i = 0; i < hackerPwHash.length; i++) {
            if (serverDbHash != null && serverDbHash.equals(hackerPwHash[i])) {
                serverHashAlgorithmIndex = i;
                break;
            }
        }

        String[] plainPassword = new String[userTable.length];

        int i = 0;
        while (serverHashAlgorithmIndex != -1 && i < userTable.length) {
            if (rainbowTables[serverHashAlgorithmIndex].contains(userTable[i].getPasswordHash())) {
                    plainPassword[i] = rainbowTables[serverHashAlgorithmIndex].get(userTable[i].getPasswordHash());
            }
            i++;
        }

        return plainPassword;
    }

    private String getHashByType(String hashFuctionType) {
        String hash;

        try {
            MessageDigest hashType = MessageDigest.getInstance(hashFuctionType);
            hashType.update(password.getBytes(StandardCharsets.UTF_8));
            byte outputByte[] = hashType.digest();
            hash = Base64.getEncoder().encodeToString(outputByte);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            hash = null;
        }

        return hash;
    }
}